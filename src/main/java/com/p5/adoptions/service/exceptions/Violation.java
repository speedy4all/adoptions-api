package com.p5.adoptions.service.exceptions;

import java.io.Serializable;

public class Violation implements Serializable {

    private String field;

    private String message;

    private String receivedValue;

    public Violation() {
    }

    public Violation(String field, String message, String receivedValue) {
        this.field = field;
        this.message = message;
        this.receivedValue = receivedValue;
    }

    public Violation(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public Violation setField(String field) {
        this.field = field;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Violation setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getReceivedValue() {
        return receivedValue;
    }

    public Violation setReceivedValue(String receivedValue) {
        this.receivedValue = receivedValue;
        return this;
    }
}
