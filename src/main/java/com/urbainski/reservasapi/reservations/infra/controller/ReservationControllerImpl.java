package com.urbainski.reservasapi.reservations.infra.controller;

import com.urbainski.reservasapi.reservations.ReservationOperation;
import com.urbainski.reservasapi.reservations.domain.ReservationStatus;
import com.urbainski.reservasapi.reservations.infra.controller.dto.CreateReservationRequestDTO;
import com.urbainski.reservasapi.reservations.infra.controller.dto.CreateReservationResponseDTO;
import com.urbainski.reservasapi.reservations.infra.controller.dto.GetReservationByIdResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
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
                .doOnNext(value -> value.setStatus(ReservationStatus.RESERVED))
                .flatMap(operation::save)
                .map(mapper::toCreateReservationResponseDTO)
                .map(value -> ResponseEntity.status(HttpStatus.CREATED).body(value))
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
}
