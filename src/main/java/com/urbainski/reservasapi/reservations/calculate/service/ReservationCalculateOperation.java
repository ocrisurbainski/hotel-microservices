package com.urbainski.reservasapi.reservations.calculate.service;

import com.urbainski.reservasapi.reservations.domain.Reservation;

public interface ReservationCalculateOperation {

    void calculate(Reservation reservation);

}
