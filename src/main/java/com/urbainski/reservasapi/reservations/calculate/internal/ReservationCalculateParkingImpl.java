package com.urbainski.reservasapi.reservations.calculate.internal;

import com.urbainski.reservasapi.reservations.properties.ReservationPricesProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationCalculateParkingImpl extends ReservationCalculate {

    private final ReservationPricesProperties.ReservationPricesDayProperties reservationPricesDayProperties;

    public ReservationCalculateParkingImpl(
            ReservationCalculate next,
            ReservationPricesProperties.ReservationPricesDayProperties reservationPricesDayProperties) {
        super(next);
        this.reservationPricesDayProperties = reservationPricesDayProperties;
    }

    public ReservationCalculateParkingImpl(
            ReservationPricesProperties.ReservationPricesDayProperties reservationPricesDayProperties) {
        this(null, reservationPricesDayProperties);
    }

    @Override
    protected BigDecimal getValueByDateInternal(LocalDateTime dateCalculate) {
        return reservationPricesDayProperties.getParking().getNormal();
    }

    @Override
    protected boolean accepts(LocalDateTime dateCalculate) {
        return reservationPricesDayProperties.getCode().equals(dateCalculate.getDayOfWeek());
    }

}
