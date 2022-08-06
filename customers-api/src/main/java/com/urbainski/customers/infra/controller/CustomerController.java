package com.urbainski.customers.infra.controller;

import com.urbainski.customers.infra.controller.dto.*;
import com.urbainski.commons.exception.handler.dto.ResponseErrorDTO;
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

@Tag(name = "Customers operations")
public interface CustomerController {

    @Operation(operationId = "save", description = "Save new customer")
    @ApiResponse(responseCode = "201", description = "When a new customer is succesfull saved")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<CreateCustomerResponseDTO>> save(Mono<CreateCustomerRequestDTO> dto, UriComponentsBuilder componentsBuilder);

    @Operation(operationId = "update", description = "Update the customer")
    @ApiResponse(responseCode = "200", description = "When the customer is succesfull updated")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no customer with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<UpdateCustomerResponseDTO>> update(Mono<UpdateCustomerRequestDTO> dto, @Parameter(description = "Identifier of customer") String id);

    @Operation(operationId = "deleteById", description = "Delete a customer by their identifier")
    @ApiResponse(responseCode = "200", description = "When the customer is succesfull deleted")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no customer with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<Void>> deleteById(@Parameter(description = "Identifier of customer") String id);

    @Operation(operationId = "findById", description = "Find a customer by their identifier")
    @ApiResponse(responseCode = "200", description = "When the customer is found")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no customer with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<GetCustomerByIdResponseDTO>> findById(@Parameter(description = "Identifier of customer") String id);

    @Operation(operationId = "findByDocument", description = "Find a customer by their number of document")
    @ApiResponse(responseCode = "200", description = "When the customer is found")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    @ApiResponse(responseCode = "404", description = "When there is no customer with the given identifier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Mono<ResponseEntity<GetCustomerByDocumentResponseDTO>> findByDocument(@Parameter(description = "Number document of customer") String document);

    @Operation(operationId = "findByTelephone", description = "Find the customers by their number of telephone")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Flux<GetCustomerByTelephoneResponseDTO> findByTelephone(@Parameter(description = "Number telephone of customer") String telephone);

    @Operation(operationId = "findByName", description = "Find the customers by their name")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    @ApiResponse(responseCode = "400", description = "When the input data is wrong", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))
    })
    Flux<GetCustomerByNameResponseDTO> findByName(@Parameter(description = "Name of customer") String name);

    @Operation(operationId = "findAll", description = "Find all customers")
    @ApiResponse(responseCode = "200", description = "When the search is successful")
    Flux<GetAllCustomerResponseDTO> findAll();

}
