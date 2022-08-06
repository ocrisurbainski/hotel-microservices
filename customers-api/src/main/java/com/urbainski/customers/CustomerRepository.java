package com.urbainski.customers;

import com.urbainski.customers.domain.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

    Mono<Customer> save(Customer customer);

    Mono<Customer> update(Customer customer);

    Mono<Void> deleteById(String id);

    Mono<Customer> findById(String id);

    Mono<Customer> findByDocument(String document);

    Flux<Customer> findByTelephone(String telephone);

    Flux<Customer> findByName(String name);

    Flux<Customer> findAll();

}
