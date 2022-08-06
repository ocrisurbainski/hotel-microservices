package com.urbainski.customers.infra.mongo;

import com.urbainski.commons.exception.NotFoundException;
import com.urbainski.commons.message.MessageSourceWrapperComponent;
import com.urbainski.customers.CustomerRepository;
import com.urbainski.customers.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.urbainski.customers.message.CustomersSystemMessages.CUSTOMER_NOT_FOUND;

@Slf4j
@Component
public class CustomerMongoRepository implements CustomerRepository {

    private final CustomerSpringRepository customerSpringRepository;

    private final CustomerMongoMapper mapper;

    private final MessageSourceWrapperComponent messageSourceWrapperComponent;

    @Autowired
    public CustomerMongoRepository(
            CustomerSpringRepository customerSpringRepository,
            CustomerMongoMapper mapper,
            MessageSourceWrapperComponent messageSourceWrapperComponent) {
        this.customerSpringRepository = customerSpringRepository;
        this.mapper = mapper;
        this.messageSourceWrapperComponent = messageSourceWrapperComponent;
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        return Mono.just(customer)
                .map(mapper::toCustomerDocument)
                .flatMap(customerSpringRepository::save)
                .map(mapper::toCustomer)
                .doOnSuccess(value -> log.debug("Success when registering a new customer, id: {}", value.getId()))
                .doOnError(throwable -> onErrorInsert(throwable, customer));
    }

    @Override
    public Mono<Customer> update(Customer customer) {
        return customerSpringRepository.findById(customer.getId())
                .switchIfEmpty(Mono.error(new NotFoundException(messageSourceWrapperComponent.getMessage(CUSTOMER_NOT_FOUND.getKey()))))
                .map(customerDocument -> customer)
                .map(mapper::toCustomerDocument)
                .flatMap(customerSpringRepository::save)
                .map(mapper::toCustomer)
                .doOnSuccess(value -> log.debug("Success in updating the customer, id: {}", value.getId()))
                .doOnError(throwable -> onErrorUpdate(throwable, customer));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return customerSpringRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(messageSourceWrapperComponent.getMessage(CUSTOMER_NOT_FOUND.getKey()))))
                .then(customerSpringRepository.deleteById(id))
                .doOnSuccess(value -> log.debug("Success when deleting customer, id: {}", id))
                .doOnError(throwable -> log.error("Error deleting customer, id: {}", id, throwable));
    }

    @Override
    public Mono<Customer> findById(String id) {
        return this.customerSpringRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(messageSourceWrapperComponent.getMessage(CUSTOMER_NOT_FOUND.getKey()))))
                .map(mapper::toCustomer)
                .doOnError(throwable -> log.error("Error in method ::findById::", throwable.getCause()))
                .doOnSuccess(value -> log.debug("Success in method ::findById:: {}", value));
    }

    @Override
    public Mono<Customer> findByDocument(String document) {
        return this.customerSpringRepository.findByDocumentEquals(document)
                .switchIfEmpty(Mono.error(new NotFoundException(messageSourceWrapperComponent.getMessage(CUSTOMER_NOT_FOUND.getKey()))))
                .map(mapper::toCustomer)
                .doOnError(throwable -> log.error("Error in method ::findByDocument::", throwable.getCause()))
                .doOnSuccess(value -> log.debug("Success in method ::findByDocument:: {}", value));
    }

    @Override
    public Flux<Customer> findByTelephone(String telephone) {
        return this.customerSpringRepository.findByTelephoneEquals(telephone).map(mapper::toCustomer);
    }

    @Override
    public Flux<Customer> findByName(String name) {
        return this.customerSpringRepository.findByNameContainingIgnoreCase(name).map(mapper::toCustomer);
    }

    @Override
    public Flux<Customer> findAll() {
        return this.customerSpringRepository.findAll().map(mapper::toCustomer);
    }

    private void onErrorInsert(Throwable throwable, Customer customer) {
        var logMessage = "Error registering new customer: {}, cause: {}";
        var json = ToStringBuilder.reflectionToString(customer, ToStringStyle.JSON_STYLE);
        log.error(logMessage, json, throwable.getMessage());
    }

    private void onErrorUpdate(Throwable throwable, Customer customer) {
        var logMessage = "Error updating customer: {}, cause: {}";
        var json = ToStringBuilder.reflectionToString(customer, ToStringStyle.JSON_STYLE);
        log.error(logMessage, json, throwable.getMessage());
    }

}
