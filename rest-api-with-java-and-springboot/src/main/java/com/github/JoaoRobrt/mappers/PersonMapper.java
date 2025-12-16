package com.github.JoaoRobrt.mappers;

import com.github.JoaoRobrt.data.dtos.PersonDTO;
import com.github.JoaoRobrt.models.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDTO toDto(Person person);
    Person toEntity(PersonDTO personDTO);
    List<PersonDTO> toDtosList(List<Person> persons);
    List<Person> toEntitiesList(List<PersonDTO> personDTOS);
}
