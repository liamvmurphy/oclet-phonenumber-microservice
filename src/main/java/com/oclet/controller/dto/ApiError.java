package com.oclet.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//Simple ApiError.
@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ApiError {

    private HttpStatus status;
    private String message;
}