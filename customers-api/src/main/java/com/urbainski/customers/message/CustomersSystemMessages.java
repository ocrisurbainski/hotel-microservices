package com.urbainski.customers.message;

public enum CustomersSystemMessages {
    CUSTOMER_NOT_FOUND("msg.customer.not.found.key"),
    CUSTOMER_HAVE_RESERVATIONS("msg.customer.have.reservations");

    CustomersSystemMessages(String key) {
        this.key = key;
    }

    private final String key;

    public String getKey() {
        return key;
    }

}
