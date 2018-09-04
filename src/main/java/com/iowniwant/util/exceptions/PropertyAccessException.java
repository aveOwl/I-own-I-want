package com.iowniwant.util.exceptions;

public class PropertyAccessException extends RuntimeException {

    public PropertyAccessException(final String s, final Throwable throwable) {
        super(s, throwable);
    }
}
