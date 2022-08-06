package com.urbainski.reservations.calculate.internal;

import com.urbainski.reservations.properties.ReservationPricesProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationCalculateDiaryImpl extends ReservationCalculate {

    private final ReservationPricesProperties.ReservationPricesDayProperties reservationPricesDayProperties;

    public ReservationCalculateDiaryImpl(
            ReservationCalculate next,
            ReservationPricesProperties.ReservationPricesDayProperties reservationPricesDayProperties) {
        super(next);
        this.reservationPricesDayProperties = reservationPricesDayProperties;
    }

    public ReservationCalculateDiaryImpl(
            ReservationPricesProperties.ReservationPricesDayProperties reservationPricesDayProperties) {
        this(null, reservationPricesDayProperties);
    }

    @Override
    protected BigDecimal getValueByDateInternal(LocalDateTime dateCalculate) {
        return reservationPricesDayProperties.getDiary().getNormal();
    }

    @Override
    protected boolean accepts(LocalDateTime dateCalculate) {
        return reservationPricesDayProperties.getCode().equals(dateCalculate.getDayOfWeek());
    }

}
