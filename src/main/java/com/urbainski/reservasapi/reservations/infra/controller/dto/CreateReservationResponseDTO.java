package com.urbainski.reservasapi.reservations.infra.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.urbainski.reservasapi.reservations.domain.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateReservationResponseDTO {
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateReservationInitial;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateReservationFinish;
    private boolean parkingSpace;
    private CreateReservationCustomerResponseDTO guest;
    private ReservationStatus status;
}
