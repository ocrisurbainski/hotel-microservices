package com.urbainski.reservasapi.reservations.infra.mongo;

import com.urbainski.reservasapi.reservations.domain.ReservationStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Document("reservations")
public class ReservationDocument {
    private String id;
    private LocalDate dateReservationInitial;
    private LocalDate dateReservationFinish;
    private LocalDateTime dateCheckin;
    private LocalDateTime dateCheckout;
    private boolean parkingSpace;
    private ReservationCustomerDocument guest;
    private ReservationStatus status;
    private BigDecimal amount;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
