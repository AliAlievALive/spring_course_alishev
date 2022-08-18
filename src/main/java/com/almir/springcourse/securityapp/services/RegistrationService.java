package com.almir.springcourse.securityapp.services;

import com.almir.springcourse.securityapp.models.Person;
import com.almir.springcourse.securityapp.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final PeopleRepository peopleRepository;

    @Transactional
    public void register(Person person) {
        peopleRepository.save(person);
    }
}
