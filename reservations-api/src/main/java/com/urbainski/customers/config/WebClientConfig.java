package com.urbainski.customers.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.net.ConnectException;
import java.time.Duration;

@Slf4j
@Configuration
public class WebClientConfig {

    public static final String WEBCLIENT_NA_URL = "CRIANDO WEBCLIENT NA URL {}";

    @Bean
    @Lazy
    @Qualifier("customers")
    public WebClient getWebClientCustomers(
            @Value("${clients.customers}") final String url,
            final WebClient.Builder webClientBuilder) {

        log.debug(WEBCLIENT_NA_URL, url);

        return webClientBuilder.filter(logRequestFilter())
                .baseUrl(url)
                .build();
    }

    @Bean
    public ReactorClientHttpConnector reactorClientHttpConnector(
            @Value("${clients.config.timeout.seconds}") int timeoutConfigured) {

        log.debug("TIMEOUT configured with: {} seconds", timeoutConfigured);

        final var secondsTimeout = Duration.ofSeconds(timeoutConfigured);
        final var httpClient = HttpClient.create()
                .responseTimeout(secondsTimeout)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, secondsTimeout.toMillisPart())
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(secondsTimeout.toMillisPart()))
                        .addHandlerLast(new WriteTimeoutHandler(secondsTimeout.toMillisPart())));

        return new ReactorClientHttpConnector(httpClient.wiretap(true));
    }

    @Bean
    public Retry retry(
            @Value("${clients.config.retry.max-attempts}") Integer retryMaxAttempts,
            @Value("${clients.config.retry.fixed-delay-in-seconds}") Integer retryFixedDelayInSeconds) {

        log.debug("RETRY configured with: {} number of attempts and with fixed delay: {} seconds", retryMaxAttempts, retryFixedDelayInSeconds);

        return Retry
                .fixedDelay(retryMaxAttempts, Duration.ofSeconds(retryFixedDelayInSeconds))
                .filter(this::isRetry)
                .doAfterRetry(retrySignal -> log.info(retrySignal.toString()))
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    private static ExchangeFilterFunction logRequestFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    private boolean isRetry(Throwable throwable) {
        if (throwable instanceof ConnectException || throwable.getCause() instanceof ConnectException) {
            return true;
        }

        if (throwable instanceof WebClientResponseException) {
            return ((WebClientResponseException) throwable).getStatusCode().is5xxServerError();
        }

        return false;
    }

}
