package com.almir.springcourse.securityapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@AllArgsConstructor
@Getter
@Setter
public class PersonDTO {
    @NotEmpty(message = "Name must be don't empty")
    @Size(min = 2, max = 100, message = "name must have from 2 to 100 symbols")
    private String username;

    @Min(value = 1900, message = "Year of birth must be after 1900")
    private int yearOfBirth;

    private String password;
}
