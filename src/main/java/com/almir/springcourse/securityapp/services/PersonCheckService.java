package com.almir.springcourse.securityapp.services;

import com.almir.springcourse.securityapp.models.Person;
import com.almir.springcourse.securityapp.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonCheckService {
    private final PeopleRepository peopleRepository;

    public void userIsExist(String username) {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (person.isPresent()) {
            throw new RuntimeException();
        }
    }
}
