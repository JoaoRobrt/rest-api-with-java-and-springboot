package com.github.JoaoRobrt.mappers;

import com.github.JoaoRobrt.data.dtos.PersonDto;
import com.github.JoaoRobrt.models.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto toDto(Person person);
    Person toEntity(PersonDto personDto);
    List<PersonDto> toDtosList(List<Person> persons);
}
