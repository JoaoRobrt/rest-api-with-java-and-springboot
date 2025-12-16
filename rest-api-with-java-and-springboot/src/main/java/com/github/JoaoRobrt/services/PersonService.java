package com.github.JoaoRobrt.services;

import com.github.JoaoRobrt.exceptions.ResourceNotFoundException;
import com.github.JoaoRobrt.models.Person;
import com.github.JoaoRobrt.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        logger.info("Finding all persons");
        return personRepository.findAll();
    }

    public Person findById(long id) {
        logger.info("Finding person by id: " + id);

        return personRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records for this ID"));
    }

    public Person create(Person person) {
        logger.info("Creating person: ");
        return personRepository.save(person);
    }

    public Person update(Person person, Long id){
        logger.info("Updating person!");
        Person entity = findById(id);

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(long id) {
        logger.info("Deleting person by id: ");

        Person entity = findById(id);
        personRepository.delete(entity);
    }
}
