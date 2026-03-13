package br.com.rafelms.rest_with_spring.unittests.services;

import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
import br.com.rafelms.rest_with_spring.exception.RequiredObjectIsNullException;
import br.com.rafelms.rest_with_spring.model.Person;
import br.com.rafelms.rest_with_spring.repository.PersonRepository;
import br.com.rafelms.rest_with_spring.services.PersonServices;
import br.com.rafelms.rest_with_spring.unittests.mapper.mocks.MockPerson;
import br.com.rafelms.rest_with_spring.validations.PersonValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @Mock
    PersonRepository repository;

    @Mock
    private PersonValidator validator;

    @InjectMocks
    private PersonServices service;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
    }

    private void assertPerson(PersonDTO person, int number) {
        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getLinks());
        assertTrue(person.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/" + number)
                        && "GET".equals(link.getType())
                )
        );
        assertEquals("Address Test" + number, person.getAddress());
        assertEquals("First Name Test" + number, person.getFirstName());
        assertEquals("Last Name Test" + number, person.getLastName());
        assertEquals(((number % 2) == 0) ? "Male" : "Female", person.getGender());
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1L);
        assertPerson(result, 1);
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        when(repository.save(any(Person.class))).thenReturn(person);
        var result = service.create(dto);
        assertPerson(result, 1);
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null));
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.update(null));
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(any(Person.class))).thenReturn(person);
        var result = service.update(dto);
        assertPerson(result, 1);
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Person> list = input.mockEntityList();
        Page<Person> page = new PageImpl<>(list, pageable, list.size());
        when(repository.findAll(pageable)).thenReturn(page);
        PagedModel<EntityModel<PersonDTO>> result = service.findAll(pageable);
        assertNotNull(result);
        List<EntityModel<PersonDTO>> people = result.getContent().stream().toList();
        assertEquals(14, people.size());
        assertPerson(people.get(1).getContent(), 1);
        assertPerson(people.get(4).getContent(), 4);
        assertPerson(people.get(7).getContent(), 7);
    }
}
