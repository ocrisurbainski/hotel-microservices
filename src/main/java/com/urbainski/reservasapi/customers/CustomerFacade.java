package com.urbainski.reservasapi.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.AssertTrue;

@Service
public class CustomerFacade implements CustomerOperation {

    private final CustomerRepository repository;

    @Autowired
    public CustomerFacade(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    @AssertTrue
    public Mono<Customer> save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Mono<Customer> update(Customer customer) {
        return repository.update(customer);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Customer> findByDocument(String document) {
        return repository.findByDocument(document);
    }

    @Override
    public Flux<Customer> findByTelephone(String telephone) {
        return repository.findByTelephone(telephone);
    }

    @Override
    public Flux<Customer> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

}
