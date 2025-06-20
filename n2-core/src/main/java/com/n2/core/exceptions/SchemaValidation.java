package com.n2.core.exceptions;

public class SchemaValidation extends RuntimeException {
    public SchemaValidation(String message) {
        super(message);
    }
}
