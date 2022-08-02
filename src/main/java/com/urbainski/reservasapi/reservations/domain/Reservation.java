package com.urbainski.reservasapi.reservations.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Reservation {
    private String id;
    private LocalDate dateReservationInitial;
    private LocalDate dateReservationFinish;
    private LocalDateTime dateCheckin;
    private LocalDateTime dateCheckout;
    private boolean parkingSpace;
    private ReservationCustomer guest;
    private ReservationStatus status;
    private BigDecimal amount;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
