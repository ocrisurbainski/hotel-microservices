package com.urbainski.reservations.calculate.service;

import com.urbainski.reservations.calculate.internal.ReservationCalculate;
import com.urbainski.reservations.domain.Reservation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class ReservationCalculateFacade implements ReservationCalculateOperation {

    private final ReservationCalculate reservationCalculateDiary;
    private final ReservationCalculate reservationCalculateParking;

    private final ReservationCalculate reservationCalculateLateDepartureFine;

    public ReservationCalculateFacade(
            @Qualifier("diary") ReservationCalculate reservationCalculateDiary,
            @Qualifier("parking") ReservationCalculate reservationCalculateParking,
            @Qualifier("lateDepartureFine") ReservationCalculate reservationCalculateLateDepartureFine) {
        this.reservationCalculateDiary = reservationCalculateDiary;
        this.reservationCalculateParking = reservationCalculateParking;
        this.reservationCalculateLateDepartureFine = reservationCalculateLateDepartureFine;
    }

    @Override
    public void calculate(Reservation reservation) {

        var amount = BigDecimal.ZERO;
        var qtDias = reservation.getDateCheckin().toLocalDate().until(reservation.getDateCheckout().toLocalDate(), ChronoUnit.DAYS);
        for (int i = 0; i < qtDias; i++) {

            var dataCalculo = reservation.getDateCheckin().plusDays(i);

            amount = amount.add(reservationCalculateDiary.getValueByDate(dataCalculo));

            if (reservation.isParkingSpace()) {
                amount = amount.add(reservationCalculateParking.getValueByDate(dataCalculo));
            }
        }

        amount = amount.add(reservationCalculateLateDepartureFine.getValueByDate(reservation.getDateCheckout()));

        reservation.setAmount(amount);
    }

}
