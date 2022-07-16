package com.urbainski.reservasapi.util;

public enum SystemMessages {
    VALIDATION_FAILED("msg.validation.failed.key"),
    DATA_DUPLICATION("msg.data.duplication.key"),
    CLIENT_NOT_FOUND("msg.client.not.found.key");

    SystemMessages(String key) {
        this.key = key;
    }

    private final String key;

    public String getKey() {
        return key;
    }

}
