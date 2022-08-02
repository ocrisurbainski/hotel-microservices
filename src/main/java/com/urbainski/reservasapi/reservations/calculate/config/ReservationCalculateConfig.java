package com.urbainski.reservasapi.reservations.calculate.config;

import com.urbainski.reservasapi.reservations.calculate.internal.ReservationCalculate;
import com.urbainski.reservasapi.reservations.calculate.internal.ReservationCalculateDiaryImpl;
import com.urbainski.reservasapi.reservations.calculate.internal.ReservationCalculateLateDepartureFineImpl;
import com.urbainski.reservasapi.reservations.calculate.internal.ReservationCalculateParkingImpl;
import com.urbainski.reservasapi.reservations.properties.ReservationCheckoutProperties;
import com.urbainski.reservasapi.reservations.properties.ReservationPricesProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationCalculateConfig {

    @Bean
    @Qualifier("diary")
    public ReservationCalculate getReservationCalculateForDiary(ReservationPricesProperties reservationPricesProperties) {
        var calcSunday = new ReservationCalculateDiaryImpl(reservationPricesProperties.getSunday());
        var calcSaturday = new ReservationCalculateDiaryImpl(calcSunday, reservationPricesProperties.getSaturday());
        var calcFriday = new ReservationCalculateDiaryImpl(calcSaturday, reservationPricesProperties.getFriday());
        var calcThursday = new ReservationCalculateDiaryImpl(calcFriday, reservationPricesProperties.getThursday());
        var calcWednesday = new ReservationCalculateDiaryImpl(calcThursday, reservationPricesProperties.getWednesday());
        var calcTuesday = new ReservationCalculateDiaryImpl(calcWednesday, reservationPricesProperties.getTuesday());
        return new ReservationCalculateDiaryImpl(calcTuesday, reservationPricesProperties.getMonday());
    }

    @Bean
    @Qualifier("parking")
    public ReservationCalculate getReservationCalculateForParking(ReservationPricesProperties reservationPricesProperties) {
        var calcSunday = new ReservationCalculateParkingImpl(reservationPricesProperties.getSunday());
        var calcSaturday = new ReservationCalculateParkingImpl(calcSunday, reservationPricesProperties.getSaturday());
        var calcFriday = new ReservationCalculateParkingImpl(calcSaturday, reservationPricesProperties.getFriday());
        var calcThursday = new ReservationCalculateParkingImpl(calcFriday, reservationPricesProperties.getThursday());
        var calcWednesday = new ReservationCalculateParkingImpl(calcThursday, reservationPricesProperties.getWednesday());
        var calcTuesday = new ReservationCalculateParkingImpl(calcWednesday, reservationPricesProperties.getTuesday());
        return new ReservationCalculateParkingImpl(calcTuesday, reservationPricesProperties.getMonday());
    }

    @Bean
    @Qualifier("lateDepartureFine")
    public ReservationCalculate getReservationCalculateLateDepartureFine(
            ReservationPricesProperties reservationPricesProperties,
            ReservationCheckoutProperties reservationCheckoutProperties) {
        var diaries = getReservationCalculateForDiary(reservationPricesProperties);
        return new ReservationCalculateLateDepartureFineImpl(diaries, reservationCheckoutProperties);
    }

}
