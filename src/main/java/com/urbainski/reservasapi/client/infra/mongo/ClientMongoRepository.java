package com.urbainski.reservasapi.client.infra.mongo;

import com.urbainski.reservasapi.client.Client;
import com.urbainski.reservasapi.client.ClientRepository;
import com.urbainski.reservasapi.commons.exception.NotFoundException;
import com.urbainski.reservasapi.commons.message.MessageSourceWrapperComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.urbainski.reservasapi.commons.message.SystemMessages.CLIENT_NOT_FOUND;

@Slf4j
@Component
public class ClientMongoRepository implements ClientRepository {

    private final ClientSpringRepository clientSpringRepository;

    private final ClientMongoMapper mapper;

    private final MessageSourceWrapperComponent messageSourceWrapperComponent;

    @Autowired
    public ClientMongoRepository(
            ClientSpringRepository clientSpringRepository,
            ClientMongoMapper mapper,
            MessageSourceWrapperComponent messageSourceWrapperComponent) {
        this.clientSpringRepository = clientSpringRepository;
        this.mapper = mapper;
        this.messageSourceWrapperComponent = messageSourceWrapperComponent;
    }

    @Override
    public Mono<Client> save(Client client) {
        return Mono.just(client)
                .map(mapper::toClientDocument)
                .flatMap(clientSpringRepository::save)
                .map(mapper::toClient)
                .doOnSuccess(value -> log.debug("Success when registering a new client, id: {}", value.getId()))
                .doOnError(throwable -> onErrorInsert(throwable, client));
    }

    @Override
    public Mono<Client> update(Client client) {
        return Mono.just(client)
                .map(mapper::toClientDocument)
                .flatMap(clientSpringRepository::save)
                .map(mapper::toClient)
                .doOnSuccess(value -> log.debug("Success in updating the client, id: {}", value.getId()))
                .doOnError(throwable -> onErrorUpdate(throwable, client));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return clientSpringRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(messageSourceWrapperComponent.getMessage(CLIENT_NOT_FOUND))))
                .then(clientSpringRepository.deleteById(id))
                .doOnSuccess(value -> log.debug("Success when deleting client, id: {}", id))
                .doOnError(throwable -> log.error("Error deleting client, id: {}", id, throwable));
    }

    @Override
    public Mono<Client> findById(String id) {
        return this.clientSpringRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(messageSourceWrapperComponent.getMessage(CLIENT_NOT_FOUND))))
                .map(mapper::toClient)
                .doOnError(throwable -> log.error("Error in method ::findById::", throwable.getCause()))
                .doOnSuccess(value -> log.debug("Success in method ::findById:: {}", value));
    }

    @Override
    public Mono<Client> findByDocument(String document) {
        return this.clientSpringRepository.findByDocumentEquals(document)
                .switchIfEmpty(Mono.error(new NotFoundException(messageSourceWrapperComponent.getMessage(CLIENT_NOT_FOUND))))
                .map(mapper::toClient)
                .doOnError(throwable -> log.error("Error in method ::findByDocument::", throwable.getCause()))
                .doOnSuccess(value -> log.debug("Success in method ::findByDocument:: {}", value));
    }

    @Override
    public Flux<Client> findByTelephone(String telephone) {
        return this.clientSpringRepository.findByTelephoneEquals(telephone).map(mapper::toClient);
    }

    @Override
    public Flux<Client> findByName(String name) {
        return this.clientSpringRepository.findByNameContainingIgnoreCase(name).map(mapper::toClient);
    }

    @Override
    public Flux<Client> findAll() {
        return this.clientSpringRepository.findAll().map(mapper::toClient);
    }

    private void onErrorInsert(Throwable throwable, Client client) {
        var logMessage = "Error registering new client: {}, cause: {}";
        var json = ToStringBuilder.reflectionToString(client, ToStringStyle.JSON_STYLE);
        log.error(logMessage, json, throwable.getMessage());
    }

    private void onErrorUpdate(Throwable throwable, Client client) {
        var logMessage = "Error updating client: {}, cause: {}";
        var json = ToStringBuilder.reflectionToString(client, ToStringStyle.JSON_STYLE);
        log.error(logMessage, json, throwable.getMessage());
    }

}
