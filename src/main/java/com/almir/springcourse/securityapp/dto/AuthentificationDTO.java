package com.almir.springcourse.securityapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthentificationDTO {
    private String username;

    private String password;
}
