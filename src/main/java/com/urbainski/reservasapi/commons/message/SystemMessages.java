package com.urbainski.reservasapi.commons.message;

public enum SystemMessages {
    VALIDATION_FAILED("msg.validation.failed.key"),
    DATA_DUPLICATION("msg.data.duplication.key"),
    CUSTOMER_NOT_FOUND("msg.customer.not.found.key");

    SystemMessages(String key) {
        this.key = key;
    }

    private final String key;

    public String getKey() {
        return key;
    }

}
