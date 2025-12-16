package com.github.JoaoRobrt.services;

import com.github.JoaoRobrt.data.dtos.PersonDTO;
import com.github.JoaoRobrt.mappers.PersonMapper;
import com.github.JoaoRobrt.models.Person;
import com.github.JoaoRobrt.repositories.PersonRepository;
import com.github.JoaoRobrt.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    MockPerson input;
    @InjectMocks
    private PersonService personService;

    @Mock
    PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
    }


    @Test
    void findById() {

        Person person = input.mockEntity(1);
        person.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        when(personMapper.toDto(person)).thenReturn(dto);

        var result = personService.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks()
                .stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks()
                .stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks()
                .stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("PUT")));
        assertTrue(result.getLinks()
                .stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
    @Test
    void findAll() {
    }
}
