package com.github.JoaoRobrt.controllers;

import com.github.JoaoRobrt.data.dtos.PersonDto;
import com.github.JoaoRobrt.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        List<PersonDto> foundedPersons = personService.findAll();
        return ResponseEntity.ok(foundedPersons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable("id") Long id) {
        PersonDto foundedPerson = personService.findById(id);
        return ResponseEntity.ok(foundedPerson);
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        PersonDto createdPersonDto = personService.create(personDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPersonDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdPersonDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto personDto,@PathVariable("id") Long id) {
        PersonDto updatedPersonDto = personService.update(personDto, id);
        return ResponseEntity.ok(updatedPersonDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonById(@PathVariable("id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
