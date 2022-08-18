package com.almir.springcourse.securityapp.util;


import com.almir.springcourse.securityapp.models.Person;
import com.almir.springcourse.securityapp.services.PersonCheckService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class PersonValidator implements Validator {
    private final PersonCheckService checkService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        try {
            checkService.userIsExist(person.getUsername());
        } catch (RuntimeException e) {
            errors.rejectValue("username", "", "Person with this name is exist");
        }
    }
}
