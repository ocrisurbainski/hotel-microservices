package com.urbainski.reservasapi.customers.infra.controller;

import com.urbainski.reservasapi.customers.CustomerOperation;
import com.urbainski.reservasapi.customers.infra.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerControllerImpl implements CustomerController {

    private final CustomerOperation operation;

    private final CustomerControllerMapper mapper;

    @Autowired
    public CustomerControllerImpl(CustomerOperation operation, CustomerControllerMapper mapper) {
        this.operation = operation;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
    public Mono<ResponseEntity<CreateCustomerResponseDTO>> save(
            @RequestBody @Valid Mono<CreateCustomerRequestDTO> dto, UriComponentsBuilder componentsBuilder) {

        return dto.map(mapper::toCustomer)
                .flatMap(operation::save)
                .map(mapper::toCreateCustomerResponseDTO)
                .map(value -> {
                    var uri = componentsBuilder.path("/customers/{id}").buildAndExpand(value.getId()).toUri();
                    return ResponseEntity.created(uri).body(value);
                })
                .log();
    }

    @Override
    @PutMapping("/{id}")
    public Mono<ResponseEntity<UpdateCustomerResponseDTO>> update(@RequestBody @Valid Mono<UpdateCustomerRequestDTO> dto, @PathVariable String id) {
        return dto.map(mapper::toCustomer)
                .doOnNext(value -> value.setId(id))
                .flatMap(operation::update)
                .map(mapper::toUpdateCustomerResponseDTO)
                .map(ResponseEntity::ok)
                .log();
    }

    @Override
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return operation.deleteById(id).map(ResponseEntity::ok).log();
    }

    @Override
    @GetMapping("/{id}")
    public Mono<ResponseEntity<GetCustomerByIdResponseDTO>> findById(@PathVariable String id) {
        return operation.findById(id)
                .map(mapper::toGetCustomerByIdResponseDTO)
                .map(ResponseEntity::ok)
                .log();
    }

    @Override
    @GetMapping("/document/{document}")
    public Mono<ResponseEntity<GetCustomerByDocumentResponseDTO>> findByDocument(@PathVariable String document) {
        return operation.findByDocument(document)
                .map(mapper::toGetCustomerByDocumentResponseDTO)
                .map(ResponseEntity::ok)
                .log();
    }

    @Override
    @GetMapping(value = "/telephone/{telephone}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetCustomerByTelephoneResponseDTO> findByTelephone(@PathVariable String telephone) {
        return operation.findByTelephone(telephone)
                .map(mapper::toGetCustomerByTelephoneResponseDTO)
                .log();
    }

    @Override
    @GetMapping(value = "/name/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetCustomerByNameResponseDTO> findByName(@PathVariable String name) {
        return operation.findByName(name)
                .map(mapper::toGetCustomerByNameResponseDTO)
                .log();
    }

    @Override
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetAllCustomerResponseDTO> findAll() {
        return this.operation.findAll()
                .map(mapper::toGetAllCustomerResponseDTO)
                .log();
    }

}
