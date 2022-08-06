package com.urbainski.reservations.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "reservation.checkout")
public class ReservationCheckoutProperties {

    private LocalTime hourLimit;

}
