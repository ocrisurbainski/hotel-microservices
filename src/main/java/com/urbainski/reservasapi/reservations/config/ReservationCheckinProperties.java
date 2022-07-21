package com.urbainski.reservasapi.reservations.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "reservation.checkin")
public class ReservationCheckinProperties {

    private LocalTime hourInit;

}
