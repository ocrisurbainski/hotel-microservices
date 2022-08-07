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
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

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

        log.debug("TIMEOUT confugado com: {}  segundos", timeoutConfigured);

        final var secondsTimeout = Duration.ofSeconds(timeoutConfigured);
        final var httpClient = HttpClient.create()
                .responseTimeout(secondsTimeout)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, secondsTimeout.toMillisPart())
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(secondsTimeout.toMillisPart()))
                        .addHandlerLast(new WriteTimeoutHandler(secondsTimeout.toMillisPart())));

        return new ReactorClientHttpConnector(httpClient.wiretap(true));
    }

    private static ExchangeFilterFunction logRequestFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

}
