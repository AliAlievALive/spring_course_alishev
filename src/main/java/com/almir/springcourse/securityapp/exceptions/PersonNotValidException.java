package com.almir.springcourse.securityapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonNotValidException extends RuntimeException{
    public PersonNotValidException(String field) {
        super(field);
    }
}
