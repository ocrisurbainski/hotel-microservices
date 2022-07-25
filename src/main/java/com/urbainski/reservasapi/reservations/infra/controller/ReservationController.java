package com.urbainski.reservasapi.reservations.infra.controller;

import com.urbainski.reservasapi.commons.exception.handler.dto.ResponseErrorDTO;
import com.urbainski.reservasapi.reservations.infra.controller.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Reservations operations")
public interface ReservationController {

    @Operation(operationId = "save", description = "Save new reservation")
    @ApiResponse(responseCode = "201", description = "When a new reservation is succesfull saved")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "422", description = "When the reservation has invalid dates", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<CreateReservationResponseDTO>> save(Mono<CreateReservationRequestDTO> dto, UriComponentsBuilder uriComponentsBuilder);

    @Operation(operationId = "update", description = "Update the reservation")
    @ApiResponse(responseCode = "200", description = "When the reservation is succesfull updated")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no reservation with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "422", description = "When the reservation has invalid dates", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<UpdateReservationResponseDTO>> update(Mono<UpdateReservationRequestDTO> dto, @Parameter(description = "Identifier of reservation") String id);

    @Operation(operationId = "cancel", description = "Cancel reservation by their identifier")
    @ApiResponse(responseCode = "200", description = "When reservation is succesfull canceled")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no reservation with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "422", description = "When the reservation is already canceled", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<Void>> cancel(@Parameter(description = "Identifier of reservation") String id);

    @Operation(operationId = "cancel", description = "Checkin reservation by their identifier")
    @ApiResponse(responseCode = "200", description = "When checkin of reservation is succesfull")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no reservation with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "422", description = "When the reservation is in a status other than reserved", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<Void>> checkin(@Parameter(description = "Identifier of reservation") String id);

    @Operation(operationId = "checkout", description = "Checkout of reservation by their identifier")
    @ApiResponse(responseCode = "200", description = "When checkout of reservation is succesfull")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no reservation with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "422", description = "When the reservation is in a status other than checkin", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<Void>> checkout(@Parameter(description = "Identifier of reservation") String id);

    @Operation(operationId = "findById", description = "Find a reservation by their identifier")
    @ApiResponse(responseCode = "200", description = "When the reservation is found")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no reservation with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<GetReservationByIdResponseDTO>> findById(@Parameter(description = "Identifier of reservation") String id);

    @Operation(operationId = "findByGuestDocument", description = "Find all reservations by guest document")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    Flux<GetReservationByDocumentResponseDTO> findByGuestDocument(@Parameter(description = "Document guest of reservation") String document);

    @Operation(operationId = "findByGuestName", description = "Find all reservations by guest name")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    Flux<GetReservationByNameResponseDTO> findByGuestName(@Parameter(description = "Name guest of reservation") String name);

    @Operation(operationId = "findAll", description = "Find all reservations")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    Flux<GetAllReservationResponseDTO> findAll();

}
