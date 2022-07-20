package com.urbainski.reservasapi.reservations.infra.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.urbainski.reservasapi.reservations.domain.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetReservationByIdResponseDTO {
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateReservationInitial;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateReservationFinish;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCheckin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCheckout;
    private boolean parkingSpace;
    private CreateReservationCustomerResponseDTO customerPayment;
    private CreateReservationCustomerResponseDTO guest;
    private ReservationStatus status;
}
