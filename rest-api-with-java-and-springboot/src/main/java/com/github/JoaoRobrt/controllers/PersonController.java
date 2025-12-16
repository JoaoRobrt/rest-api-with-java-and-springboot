package com.github.JoaoRobrt.controllers;

import com.github.JoaoRobrt.data.dtos.PersonDTO;
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
    public ResponseEntity<List<PersonDTO>> findAll() {
        List<PersonDTO> foundedPersons = personService.findAll();
        return ResponseEntity.ok(foundedPersons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Long id) {
        PersonDTO foundedPerson = personService.findById(id);
        return ResponseEntity.ok(foundedPerson);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDto) {
        PersonDTO createdPersonDTO = personService.create(personDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPersonDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdPersonDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO personDto, @PathVariable("id") Long id) {
        PersonDTO updatedPersonDTO = personService.update(personDto, id);
        return ResponseEntity.ok(updatedPersonDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
