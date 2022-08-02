package com.urbainski.reservasapi.customers.infra.controller;

import com.urbainski.reservasapi.customers.domain.Customer;
import com.urbainski.reservasapi.customers.infra.controller.dto.*;

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
