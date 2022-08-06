package com.urbainski.reservations.calculate.service;

import com.urbainski.reservations.domain.Reservation;
import com.urbainski.reservations.domain.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ReservationCalculateFacadeTest {

    private final ReservationCalculateOperation operation;

    @Autowired
    ReservationCalculateFacadeTest(ReservationCalculateOperation operation) {
        this.operation = operation;
    }

    @Test
    void calculateShouldRefundTheValueOfTheAccommodationForReservationDuringTheWeekdaysWithoutParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 4, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 9, 15, 0));
        reservation.setParkingSpace(false);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(600D, reservation.getAmount().doubleValue());
    }

    @Test
    void calculateShouldRefundTheValueOfTheAccommodationForReservationDuringTheWeekdaysWithParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 4, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 9, 15, 0));
        reservation.setParkingSpace(true);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(675, reservation.getAmount().doubleValue());
    }

    @Test
    void calculateShouldRefundTheValueOfTheAccommodationForReservationDuringTheWeekendWithoutParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 9, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 11, 15, 0));
        reservation.setParkingSpace(false);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(300, reservation.getAmount().doubleValue());
    }

    @Test
    void calculateShouldRefundTheValueOfTheAccommodationForReservationDuringTheWeekendWithParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 9, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 11, 15, 0));
        reservation.setParkingSpace(true);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(340, reservation.getAmount().doubleValue());
    }

    @Test
    void shouldRefundTheValueOfTheAccommodationForAReservationThatStartsDuringTheWeekAndEndsOnTheWeekendWithoutParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 7, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 10, 15, 0));
        reservation.setParkingSpace(false);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(390, reservation.getAmount().doubleValue());
    }

    @Test
    void shouldRefundTheValueOfTheAccommodationForAReservationThatStartsDuringTheWeekAndEndsOnTheWeekendWithParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 7, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 10, 15, 0));
        reservation.setParkingSpace(true);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(440, reservation.getAmount().doubleValue());
    }

    @Test
    void shouldReturnTheValueOfTheAccommodationChargingAnExtraDayWhenTheDepartureIsAfterTheDeadlineDuringTheWeekWithoutParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 4, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 6, 18, 0));
        reservation.setParkingSpace(false);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(360, reservation.getAmount().doubleValue());
    }

    @Test
    void shouldReturnTheValueOfTheAccommodationChargingAnExtraDayWhenTheDepartureIsAfterTheDeadlineDuringTheWeekWithParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 4, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 6, 18, 0));
        reservation.setParkingSpace(true);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(390, reservation.getAmount().doubleValue());
    }

    @Test
    void shouldReturnTheValueOfTheAccommodationChargingAnExtraDayWhenTheDepartureIsAfterTheDeadlineDuringTheWeekendWithoutParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 8, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 10, 18, 0));
        reservation.setParkingSpace(false);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(420, reservation.getAmount().doubleValue());
    }

    @Test
    void shouldReturnTheValueOfTheAccommodationChargingAnExtraDayWhenTheDepartureIsAfterTheDeadlineDuringTheWeekendWithParking() {

        var reservation = new Reservation();
        reservation.setDateCheckin(LocalDateTime.of(2022, Month.JULY, 8, 13, 0));
        reservation.setDateCheckout(LocalDateTime.of(2022, Month.JULY, 10, 18, 0));
        reservation.setParkingSpace(true);
        reservation.setStatus(ReservationStatus.CHECKIN);

        operation.calculate(reservation);

        assertEquals(455, reservation.getAmount().doubleValue());
    }

}