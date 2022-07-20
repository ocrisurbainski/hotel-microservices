package com.urbainski.reservasapi.reservations.infra.controller;

import com.urbainski.reservasapi.commons.exception.handler.dto.ResponseErrorDTO;
import com.urbainski.reservasapi.reservations.infra.controller.dto.CreateReservationRequestDTO;
import com.urbainski.reservasapi.reservations.infra.controller.dto.CreateReservationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Tag(name = "Reservations operations")
public interface ReservationController {

    @Operation(operationId = "save", description = "Save new reservation")
    @ApiResponse(responseCode = "201", description = "When a new reservation is succesfull saved")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<CreateReservationResponseDTO>> save(Mono<CreateReservationRequestDTO> dto, UriComponentsBuilder uriComponentsBuilder);

}
