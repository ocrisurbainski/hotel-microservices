package com.urbainski.customers.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomerWebClientComponent {

    private final WebClient webClient;

    public CustomerWebClientComponent(@Qualifier("customers") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Boolean> checkCustomerExists(String customerId) {
        return webClient.get()
                .uri("/customers/{id}", customerId)
                .exchangeToMono(clientResponse -> {
                    if (HttpStatus.OK.equals(clientResponse.statusCode())) {
                        return Mono.just(Boolean.TRUE);
                    }
                    return Mono.just(Boolean.FALSE);
                })
                .doOnSubscribe(s -> log.debug("Verify the customer with identifier: {} exists.", customerId))
                .doOnSuccess(response -> log.debug("Success verify that the customer with the identifier: {} exists.", customerId))
                .doOnError(e -> log.debug("Error verify that the customer with the identifier: {} exists.", customerId));
    }

}
