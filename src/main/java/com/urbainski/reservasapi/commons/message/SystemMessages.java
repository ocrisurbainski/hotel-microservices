package com.urbainski.reservasapi.commons.message;

public enum SystemMessages {
    VALIDATION_FAILED("msg.validation.failed.key"),
    DATA_DUPLICATION("msg.data.duplication.key"),
    CUSTOMER_NOT_FOUND("msg.customer.not.found.key"),
    RESERVATION_NOT_FOUND("msg.reservation.not.found.key"),
    RESERVATION_STATUS_CANCELED_INVALID("msg.reservation.status.canceled.invalid"),
    RESERVATION_STATUS_IS_CANCELED("msg.reservation.status.canceled");

    SystemMessages(String key) {
        this.key = key;
    }

    private final String key;

    public String getKey() {
        return key;
    }

}
