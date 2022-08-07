package com.urbainski.reservations.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
@Component
public class ReservationsWebClientComponent {

    private final WebClient webClient;
    private final Retry retry;

    public ReservationsWebClientComponent(
            @Qualifier("reservations") WebClient webClient, Retry retry) {
        this.webClient = webClient;
        this.retry = retry;
    }

    public Mono<Boolean> checkCustomerHaveReservations(String customerId) {
        return webClient.get()
                .uri("/reservations/customer/exists/{id}", customerId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .retryWhen(retry)
                .doOnSubscribe(s -> log.debug("Verify the customer with identifier: {} have reservations.", customerId))
                .doOnSuccess(response -> log.debug("Success verify that the customer with the identifier: {} have reservations.", customerId))
                .doOnError(e -> log.debug("Error verify that the customer with the identifier: {} have reservations.", customerId));
    }

}
