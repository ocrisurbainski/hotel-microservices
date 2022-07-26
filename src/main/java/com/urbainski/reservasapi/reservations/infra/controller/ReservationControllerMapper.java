package com.urbainski.reservasapi.reservations.infra.controller;

import com.urbainski.reservasapi.reservations.domain.Reservation;
import com.urbainski.reservasapi.reservations.infra.controller.dto.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class ReservationControllerMapper {

    abstract Reservation toReservation(CreateReservationRequestDTO dto);

    abstract CreateReservationResponseDTO toCreateReservationResponseDTO(Reservation reservation);

    abstract Reservation toReservation(UpdateReservationRequestDTO dto);

    abstract UpdateReservationResponseDTO toUpdateReservationResponseDTO(Reservation reservation);

    abstract GetReservationByIdResponseDTO toGetReservationByIdResponseDTO(Reservation reservation);

    abstract GetReservationByDocumentResponseDTO toGetReservationByDocumentResponseDTO(Reservation reservation);

    abstract GetReservationByNameResponseDTO toGetReservationByNameResponseDTO(Reservation reservation);

    abstract GetAllReservationResponseDTO toGetAllReservationResponseDTO(Reservation reservation);

    @AfterMapping
    protected void afterMappingToGetReservationByIdResponseDTO(@MappingTarget GetReservationByIdResponseDTO dto) {
        if (BigDecimal.ZERO.equals(dto.getAmount())) {
            dto.setAmount(null);
        }
    }

    @AfterMapping
    protected void afterMappingToGetReservationByDocumentResponseDTO(@MappingTarget GetReservationByDocumentResponseDTO dto) {
        if (BigDecimal.ZERO.equals(dto.getAmount())) {
            dto.setAmount(null);
        }
    }

    @AfterMapping
    protected void afterMappingToGetReservationByNameResponseDTO(@MappingTarget GetReservationByNameResponseDTO dto) {
        if (BigDecimal.ZERO.equals(dto.getAmount())) {
            dto.setAmount(null);
        }
    }

    @AfterMapping
    protected void afterMappingToGetAllReservationResponseDTO(@MappingTarget GetAllReservationResponseDTO dto) {
        if (BigDecimal.ZERO.equals(dto.getAmount())) {
            dto.setAmount(null);
        }
    }

}
