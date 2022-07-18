package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.infra.controller.dto.*;
import com.urbainski.reservasapi.commons.exception.handler.dto.ResponseErrorDTO;
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

@Tag(name = "Clients operations")
public interface ClientController {

    @Operation(operationId = "save", description = "Save new client")
    @ApiResponse(responseCode = "201", description = "When a new client is succesfull saved")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<CreateClientResponseDTO>> save(Mono<CreateClientRequestDTO> dto, UriComponentsBuilder componentsBuilder);

    @Operation(operationId = "update", description = "Update the client")
    @ApiResponse(responseCode = "200", description = "When the client is succesfull updated")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<UpdateClientResponseDTO>> update(Mono<UpdateClientRequestDTO> dto, @Parameter(description = "Identifier of client") String id);

    @Operation(operationId = "deleteById", description = "Delete a client by their identifier")
    @ApiResponse(responseCode = "200", description = "When the client is succesfull deleted")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no client with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<Void>> deleteById(@Parameter(description = "Identifier of client") String id);

    @Operation(operationId = "findById", description = "Find a client by their identifier")
    @ApiResponse(responseCode = "200", description = "When the client is found")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no client with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<GetClientByIdResponseDTO>> findById(@Parameter(description = "Identifier of client") String id);

    @Operation(operationId = "findByDocument", description = "Find a client by their number of document")
    @ApiResponse(responseCode = "200", description = "When the client is found")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no client with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<GetClientByDocumentResponseDTO>> findByDocument(@Parameter(description = "Number document of client") String document);

    @Operation(operationId = "findByTelephone", description = "Find the clients by their number of telephone")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Flux<GetClientByTelephoneResponseDTO> findByTelephone(@Parameter(description = "Number telephone of client") String telephone);

    @Operation(operationId = "findByName", description = "Find the clients by their name")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Flux<GetClientByNameResponseDTO> findByName(@Parameter(description = "Name of client") String name);

    @Operation(operationId = "findAll", description = "Find all clients")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    Flux<GetAllClientResponseDTO> findAll();

}
