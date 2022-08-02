package com.urbainski.reservasapi.reservations.infra.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateReservationRequestDTO {
    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateReservationInitial;
    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateReservationFinish;
    private boolean parkingSpace;
    @NotNull
    private UpdateReservationCustomerRequestDTO guest;
}
