package com.urbainski.reservations.message;

public enum ReservationsSystemMessages {
    CUSTOMER_NOT_FOUND("msg.customer.not.found.key"),
    RESERVATION_NOT_FOUND("msg.reservation.not.found.key"),
    RESERVATION_STATUS_CANCELED_INVALID("msg.reservation.status.canceled.invalid"),
    RESERVATION_STATUS_RESERVED("msg.reservation.status.reserved"),
    RESERVATION_STATUS_CANCELED("msg.reservation.status.canceled"),
    RESERVATION_STATUS_CHECKIN("msg.reservation.status.checkin"),
    RESERVATION_STATUS_CHECKOUT("msg.reservation.status.checkout"),
    RESERVATION_CHECKIN_TIME_DOES_NOT_ALLOW("msg.reservation.checkin.time.does.not.allow"),
    RESERVATION_CHECKIN_DATE_DOES_NOT_ALLOW("msg.reservation.checkin.date.does.not.allow"),
    RESERVATION_DATES_INVALID("msg.reservation.dates.invalid");

    ReservationsSystemMessages(String key) {
        this.key = key;
    }

    private final String key;

    public String getKey() {
        return key;
    }

}
