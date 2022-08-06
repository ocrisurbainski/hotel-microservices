package com.urbainski.customers.infra.controller;

import com.urbainski.customers.infra.controller.dto.*;
import com.urbainski.customers.domain.Customer;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CustomerControllerMapper {

    Customer toCustomer(CreateCustomerRequestDTO dto);

    CreateCustomerResponseDTO toCreateCustomerResponseDTO(Customer customer);

    Customer toCustomer(UpdateCustomerRequestDTO dto);

    UpdateCustomerResponseDTO toUpdateCustomerResponseDTO(Customer customer);

    GetCustomerByIdResponseDTO toGetCustomerByIdResponseDTO(Customer customer);

    GetCustomerByDocumentResponseDTO toGetCustomerByDocumentResponseDTO(Customer customer);

    GetCustomerByNameResponseDTO toGetCustomerByNameResponseDTO(Customer customer);

    GetCustomerByTelephoneResponseDTO toGetCustomerByTelephoneResponseDTO(Customer customer);

    GetAllCustomerResponseDTO toGetAllCustomerResponseDTO(Customer customer);

}
