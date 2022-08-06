package com.urbainski.reservations.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "reservation.prices")
public class ReservationPricesProperties {

    private ReservationPricesDayProperties monday;
    private ReservationPricesDayProperties tuesday;
    private ReservationPricesDayProperties wednesday;
    private ReservationPricesDayProperties thursday;
    private ReservationPricesDayProperties friday;
    private ReservationPricesDayProperties saturday;
    private ReservationPricesDayProperties sunday;

    @Getter
    @Setter
    public static class ReservationPricesDayProperties {
        private DayOfWeek code;
        private ReservationPricesDayValuesProperties diary;
        private ReservationPricesDayValuesProperties parking;
    }

    @Getter
    @Setter
    public static class ReservationPricesDayValuesProperties {
        private BigDecimal normal;
    }

}
