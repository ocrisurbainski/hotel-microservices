package com.urbainski.reservasapi.reservations.infra.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReservationCustomerResponseDTO {
    private String id;
    private String name;
    private String document;
    private String telephone;
}
