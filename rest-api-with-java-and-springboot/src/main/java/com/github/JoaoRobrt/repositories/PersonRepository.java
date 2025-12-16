package com.github.JoaoRobrt.repositories;

import com.github.JoaoRobrt.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
