package com.urbainski.reservations.infra.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReservationCustomerByIdResponseDTO {
    private String id;
    private String name;
    private String document;
    private String telephone;
}
