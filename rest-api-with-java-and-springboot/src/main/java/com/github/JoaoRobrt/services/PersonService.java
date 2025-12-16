package com.github.JoaoRobrt.services;

import com.github.JoaoRobrt.data.dtos.PersonDto;
import com.github.JoaoRobrt.exceptions.ResourceNotFoundException;
import com.github.JoaoRobrt.mappers.PersonMapper;
import com.github.JoaoRobrt.models.Person;
import com.github.JoaoRobrt.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonMapper personMapper;
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    private Person findEntityById(long id) {
        return personRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records for this ID"));
    }

    public List<PersonDto> findAll() {
        List<Person> persons = personRepository.findAll();
        return personMapper.toDtosList(persons);
    }

    public PersonDto findById(long id) {
      Person person = findEntityById(id);
      return personMapper.toDto(person);
    }

    public PersonDto create(PersonDto personDto) {
        Person savedEntity = personRepository.save(personMapper.toEntity(personDto));
        return personMapper.toDto(savedEntity);
    }

    public PersonDto update(PersonDto personDto, Long id){
        Person entity = findEntityById(id);

        entity.setFirstName(personDto.getFirstName());
        entity.setLastName(personDto.getLastName());
        entity.setAddress(personDto.getAddress());
        entity.setGender(personDto.getGender());

        Person updatedEntity = personRepository.save(entity);

        return personMapper.toDto(updatedEntity);
    }

    public void delete(long id) {
        Person entity = findEntityById(id);
        personRepository.delete(entity);
    }
}
