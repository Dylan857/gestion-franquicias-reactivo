package com.pruebatecnicaaccenture.features.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final int statusCode;
    private final String errorCode;
    private final String severity;

    public CustomException(String message, int statusCode, String errorCode, String severity) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.severity = severity;
    }
}
