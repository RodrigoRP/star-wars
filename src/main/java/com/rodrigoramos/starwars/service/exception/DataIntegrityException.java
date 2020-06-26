package com.rodrigoramos.starwars.service.exception;

public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String msg) {
        super(msg);
    }

}
