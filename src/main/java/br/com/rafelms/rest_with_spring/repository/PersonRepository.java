package br.com.rafelms.rest_with_spring.repository;

import br.com.rafelms.rest_with_spring.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
