package com.urbainski.reservasapi.reservations.calculate.internal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class ReservationCalculate {

    private final ReservationCalculate next;

    protected ReservationCalculate(ReservationCalculate next) {
        this.next = next;
    }

    public BigDecimal getValueByDate(LocalDateTime dateCalculate) {
        if (accepts(dateCalculate)) {
            return getValueByDateInternal(dateCalculate);
        }
        return checkNext(dateCalculate);
    }

    protected BigDecimal checkNext(LocalDateTime dateCalculate) {
        if (next == null) {
            return BigDecimal.ZERO;
        }
        return next.getValueByDate(dateCalculate);
    }

    protected abstract BigDecimal getValueByDateInternal(LocalDateTime dateCalculate);

    protected abstract boolean accepts(LocalDateTime dateCalculate);

}
