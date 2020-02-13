package com.documentflow.services;

import com.documentflow.entities.Person;
import com.documentflow.repositories.PersonRepository;
import com.documentflow.utils.ContragentUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person save(@NonNull Person personForSave) {

        Person savedPerson =  personRepository.save(personForSave);

        if(ContragentUtils.isNotEmpty(savedPerson)){
            return savedPerson;
        }
        return null;
    }
}
