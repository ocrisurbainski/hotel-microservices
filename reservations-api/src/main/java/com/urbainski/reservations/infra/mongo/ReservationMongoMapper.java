package com.urbainski.reservations.infra.mongo;

import com.urbainski.reservations.domain.Reservation;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ReservationMongoMapper {

    ReservationDocument toReservationDocument(Reservation reservation);

    Reservation toReservation(ReservationDocument reservationDocument);

}
