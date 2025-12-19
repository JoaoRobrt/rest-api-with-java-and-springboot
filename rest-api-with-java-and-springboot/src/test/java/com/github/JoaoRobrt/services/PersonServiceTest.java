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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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
        Person person = input.mockEntity(1);

        Person persisted  = input.mockEntity(1);
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(personMapper.toEntity(dto)).thenReturn(person);
        when(personRepository.save(person)).thenReturn(persisted);
        when(personMapper.toDto(person)).thenReturn(dto);

        var result = personService.create(dto);

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
    void update() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        Person persisted = input.mockEntity(1);
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(persisted);
        when(personMapper.toDto(persisted)).thenReturn(dto);

        var result = personService.update(dto,1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(personRepository.findById(1L))
                .thenReturn(Optional.of(person));

        assertDoesNotThrow(() -> personService.delete(1L));
        verify(personRepository, times(1)).findById(anyLong());
        verify(personRepository, times(1)).delete(any(Person.class));
    }
    @Test
    void findAll() {
        List<Person> persons = input.mockEntityList();
        List<PersonDTO> dtos = input.mockDTOList();

        Page<Person> page = new PageImpl<>(persons);

        when(personRepository.findAll()).thenReturn(persons);
        when(personMapper.toDtosList(persons)).thenReturn(dtos);

        var result = personService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(14, result.size());

        var dto = result.get(0);

        assertNotNull(dto.getId());
        assertNotNull(dto.getLinks());

        assertTrue(dto.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")));

        assertTrue(dto.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")));
    }
}
