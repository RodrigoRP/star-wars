package com.rodrigoramos.starwars.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class StandardError implements Serializable {

    private final Long timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;

}
