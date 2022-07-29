package com.urbainski.reservasapi.reservations.infra.controller;

import com.urbainski.reservasapi.reservations.ReservationOperation;
import com.urbainski.reservasapi.reservations.infra.controller.dto.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationControllerImpl implements ReservationController {

    private final ReservationOperation operation;

    private final ReservationControllerMapper mapper;

    public ReservationControllerImpl(ReservationOperation operation, ReservationControllerMapper mapper) {
        this.operation = operation;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
    public Mono<ResponseEntity<CreateReservationResponseDTO>> save(
            @RequestBody @Valid Mono<CreateReservationRequestDTO> dto, UriComponentsBuilder uriComponentsBuilder) {
        return dto.map(mapper::toReservation)
                .flatMap(operation::save)
                .map(mapper::toCreateReservationResponseDTO)
                .map(value -> {
                    var uri = uriComponentsBuilder.path("/reservations/{id}").buildAndExpand(value.getId()).toUri();
                    return ResponseEntity.created(uri).body(value);
                })
                .log();
    }

    @Override
    @PutMapping("/{id}")
    public Mono<ResponseEntity<UpdateReservationResponseDTO>> update(
            @RequestBody @Valid Mono<UpdateReservationRequestDTO> dto, @PathVariable String id) {
        return dto.map(mapper::toReservation)
                .doOnNext(value -> value.setId(id))
                .flatMap(operation::update)
                .map(mapper::toUpdateReservationResponseDTO)
                .map(ResponseEntity::ok)
                .log();
    }

    @Override
    @PatchMapping("/cancel/{id}")
    public Mono<ResponseEntity<Void>> cancel(@PathVariable String id) {
        return operation.cancel(id)
                .map(value -> ResponseEntity.ok().<Void>build())
                .log();
    }

    @Override
    @PatchMapping("/checkin/{id}")
    public Mono<ResponseEntity<Void>> checkin(@PathVariable String id) {
        return operation.checkin(id)
                .map(value -> ResponseEntity.ok().<Void>build())
                .log();
    }

    @Override
    @PatchMapping("/checkout/{id}")
    public Mono<ResponseEntity<Void>> checkout(@PathVariable String id) {
        return operation.checkout(id)
                .map(value -> ResponseEntity.ok().<Void>build())
                .log();
    }

    @Override
    @GetMapping("/{id}")
    public Mono<ResponseEntity<GetReservationByIdResponseDTO>> findById(@PathVariable String id) {
        return operation.findById(id)
                .map(mapper::toGetReservationByIdResponseDTO)
                .map(ResponseEntity::ok)
                .log();
    }

    @Override
    @GetMapping(path = "/customer/document/{document}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetReservationByDocumentResponseDTO> findByGuestDocument(@PathVariable String document) {
        return operation.findByGuestDocument(document)
                .map(mapper::toGetReservationByDocumentResponseDTO)
                .log();
    }

    @Override
    @GetMapping(path = "/customer/name/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetReservationByNameResponseDTO> findByGuestName(@PathVariable String name) {
        return operation.findByGuestName(name)
                .map(mapper::toGetReservationByNameResponseDTO)
                .log();
    }

    @Override
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetAllReservationResponseDTO> findAll() {
        return operation.findAll()
                .map(mapper::toGetAllReservationResponseDTO)
                .log();
    }

}
