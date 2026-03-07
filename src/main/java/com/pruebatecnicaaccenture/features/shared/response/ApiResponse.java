package com.pruebatecnicaaccenture.features.shared.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int statusCode;
    private String errorCode;
    private String severity;
    private T data;
}
