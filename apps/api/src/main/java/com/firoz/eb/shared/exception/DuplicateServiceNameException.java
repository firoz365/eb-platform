package com.firoz.eb.shared.exception;

public class DuplicateServiceNameException extends RuntimeException {
    public DuplicateServiceNameException(String name) {
        super("Service name already exists: " + name);
    }
}