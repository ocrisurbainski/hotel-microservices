package com.urbainski.customers;

import com.urbainski.commons.message.MessageSourceWrapperComponent;
import com.urbainski.customers.domain.Customer;
import com.urbainski.customers.exception.CustomerHaveReservationsException;
import com.urbainski.reservations.webclient.ReservationsWebClientComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.AssertTrue;

import static com.urbainski.customers.message.CustomersSystemMessages.CUSTOMER_HAVE_RESERVATIONS;

@Service
public class CustomerFacade implements CustomerOperation {

    private final CustomerRepository repository;
    private final ReservationsWebClientComponent reservationsWebClientComponent;
    private final MessageSourceWrapperComponent messageSourceWrapperComponent;

    @Autowired
    public CustomerFacade(
            CustomerRepository repository,
            ReservationsWebClientComponent reservationsWebClientComponent,
            MessageSourceWrapperComponent messageSourceWrapperComponent) {
        this.repository = repository;
        this.reservationsWebClientComponent = reservationsWebClientComponent;
        this.messageSourceWrapperComponent = messageSourceWrapperComponent;
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
        return reservationsWebClientComponent.checkCustomerHaveReservations(id)
                .flatMap(value -> {
                    if (Boolean.TRUE.equals(value)) {
                        return Mono.error(new CustomerHaveReservationsException(messageSourceWrapperComponent.getMessage(CUSTOMER_HAVE_RESERVATIONS.getKey())));
                    } else {
                        return repository.deleteById(id);
                    }
                });
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
