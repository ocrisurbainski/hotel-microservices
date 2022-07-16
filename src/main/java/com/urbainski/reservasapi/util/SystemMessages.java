package com.urbainski.reservasapi.util;

public enum SystemMessages {

    CLIENT_NOT_FOUND("client.not.found.key");

    SystemMessages(String key) {
        this.key = key;
    }

    private final String key;

    public String getKey() {
        return key;
    }

}
