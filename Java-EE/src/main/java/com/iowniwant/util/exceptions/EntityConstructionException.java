package com.iowniwant.util.exceptions;

public class EntityConstructionException extends RuntimeException {

    public EntityConstructionException(final String s, final Throwable throwable) {
        super(s, throwable);
    }
}
