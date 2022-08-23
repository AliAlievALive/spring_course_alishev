package com.almir.springcourse.securityapp.controllers;

import com.almir.springcourse.securityapp.dto.AuthentificationDTO;
import com.almir.springcourse.securityapp.dto.PersonDTO;
import com.almir.springcourse.securityapp.exceptions.PersonNotValidException;
import com.almir.springcourse.securityapp.exceptions.RegistrationErrorResponse;
import com.almir.springcourse.securityapp.models.Person;
import com.almir.springcourse.securityapp.security.JWTUtil;
import com.almir.springcourse.securityapp.services.RegistrationService;
import com.almir.springcourse.securityapp.util.PersonValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

import static com.almir.springcourse.securityapp.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

//    @GetMapping("/login")
//    public String loginPage() {
//        return "auth/login";
//    }
//
//    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("person") Person person) {
//        return "auth/registration";
//    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        registrationService.register(person);

        return Map.of("jwt_secret_key", jwtUtil.generateToken(person.getUsername()));
//        return ResponseEntity.ok(
//                JWTUtil.builder()
//                        .secretKey("jwt_secret_key")
//                        .issuer(jwtUtil.generateToken(person.getUsername()))
//                        .build()
//                );
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthentificationDTO authentificationDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        authentificationDTO.getUsername(),
                        authentificationDTO.getPassword()
                );

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authentificationDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    @ExceptionHandler
    private ResponseEntity<RegistrationErrorResponse> handleException(PersonNotValidException e) {
        RegistrationErrorResponse response = RegistrationErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
