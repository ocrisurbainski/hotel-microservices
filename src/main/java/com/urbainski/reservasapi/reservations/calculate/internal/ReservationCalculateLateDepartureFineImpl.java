package com.urbainski.reservasapi.reservations.calculate.internal;

import com.urbainski.reservasapi.reservations.properties.ReservationCheckoutProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationCalculateLateDepartureFineImpl extends ReservationCalculate {

    private final ReservationCalculate diaries;
    private final ReservationCheckoutProperties reservationCheckoutProperties;

    public ReservationCalculateLateDepartureFineImpl(
            ReservationCalculate diaries,
            ReservationCheckoutProperties reservationCheckoutProperties) {
        super(null);
        this.diaries = diaries;
        this.reservationCheckoutProperties = reservationCheckoutProperties;
    }

    @Override
    protected BigDecimal getValueByDateInternal(LocalDateTime dateCalculate) {
        return diaries.getValueByDate(dateCalculate);
    }

    @Override
    protected boolean accepts(LocalDateTime dateCalculate) {
        var time = dateCalculate.toLocalTime();
        return time.isAfter(reservationCheckoutProperties.getHourLimit());
    }

}
