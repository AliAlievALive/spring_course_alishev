package com.almir.springcourse.securityapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class RegistrationErrorResponse {
    private String message;
    private long timestamp;
}
