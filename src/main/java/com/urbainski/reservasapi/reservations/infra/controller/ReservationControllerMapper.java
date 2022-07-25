package com.urbainski.reservasapi.reservations.infra.controller;

import com.urbainski.reservasapi.reservations.domain.Reservation;
import com.urbainski.reservasapi.reservations.infra.controller.dto.*;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ReservationControllerMapper {

    Reservation toReservation(CreateReservationRequestDTO dto);

    CreateReservationResponseDTO toCreateReservationResponseDTO(Reservation reservation);

    Reservation toReservation(UpdateReservationRequestDTO dto);

    UpdateReservationResponseDTO toUpdateReservationResponseDTO(Reservation reservation);

    GetReservationByIdResponseDTO toGetReservationByIdResponseDTO(Reservation reservation);

    GetReservationByDocumentResponseDTO toGetReservationByDocumentResponseDTO(Reservation reservation);

    GetReservationByNameResponseDTO toGetReservationByNameResponseDTO(Reservation reservation);

    GetAllReservationResponseDTO toGetAllReservationResponseDTO(Reservation reservation);

}
