package com.github.JoaoRobrt.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.JoaoRobrt.controllers.PersonController;
import com.github.JoaoRobrt.data.dtos.PersonDTO;
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

    public List<PersonDTO> findAll() {
        List<Person> persons = personRepository.findAll();
        var personsDtos = personMapper.toDtosList(persons);
        personsDtos.forEach(this::addHateoasLinks);
        return personsDtos;
    }

    public PersonDTO findById(long id) {
      var person = findEntityById(id);
      var dto =  personMapper.toDto(person);
        addHateoasLinks(dto);
        return dto;
    }


    public PersonDTO create(PersonDTO personDto) {
        var savedEntity = personRepository.save(personMapper.toEntity(personDto));
        var dto = personMapper.toDto(savedEntity);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO personDto, Long id){
        var entity = findEntityById(id);

        entity.setFirstName(personDto.getFirstName());
        entity.setLastName(personDto.getLastName());
        entity.setAddress(personDto.getAddress());
        entity.setGender(personDto.getGender());

        var updatedEntity = personRepository.save(entity);
        var dto = personMapper.toDto(updatedEntity);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(long id) {
        var entity = findEntityById(id);
        personRepository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto, dto.getId())).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
    }
}
