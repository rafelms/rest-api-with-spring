package br.com.rafelms.rest_with_spring.unittests.services;

import br.com.rafelms.rest_with_spring.data.dto.v1.BooksDTO;
import br.com.rafelms.rest_with_spring.exception.RequiredObjectIsNullException;
import br.com.rafelms.rest_with_spring.model.Books;
import br.com.rafelms.rest_with_spring.repository.BooksRepository;
import br.com.rafelms.rest_with_spring.services.BooksServices;
import br.com.rafelms.rest_with_spring.unittests.mapper.mocks.MockBooks;
import br.com.rafelms.rest_with_spring.validations.BooksValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BooksServicesTest {

    MockBooks input;

    @Mock
    BooksRepository repository;

    @Mock
    private BooksValidator validator;


    @InjectMocks
    BooksServices service;

    @BeforeEach
    void setUp() {
        input = new MockBooks();
    }

    private void assertHateoasLinks(BooksDTO dto, Long id) {

        assertTrue(dto.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("api/books/v1/" + id)
                        && "GET".equals(link.getType())
                ));
    }

    @Test
    void findById() {

        Books entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        assertHateoasLinks(result, 1L);

        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
    }

    @Test
    void create() {

        Books entity = input.mockEntity(1);
        entity.setId(1L);

        BooksDTO dto = input.mockDTO(1);

        when(repository.save(any(Books.class))).thenReturn(entity);

        var result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        assertHateoasLinks(result, 1L);

        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
    }

    @Test
    void testCreateWithNullBooks() {

        Exception exception = assertThrows(
                RequiredObjectIsNullException.class,
                () -> service.create(null)
        );

        assertTrue(exception.getMessage()
                .contains("It is not allowed to persist a null object!"));
    }

    @Test
    void testUpdateWithNullBooks() {

        Exception exception = assertThrows(
                RequiredObjectIsNullException.class,
                () -> service.update(null)
        );

        assertTrue(exception.getMessage()
                .contains("It is not allowed to persist a null object!"));
    }

    @Test
    void update() {

        Books entity = input.mockEntity(1);
        entity.setId(1L);

        BooksDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(Books.class))).thenReturn(entity);

        var result = service.update(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        assertHateoasLinks(result, 1L);

        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
    }

    @Test
    void delete() {

        Books entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Books.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "title"));

        List<Books> list = input.mockEntityList();
        Page<Books> page = new PageImpl<>(list, pageable, list.size());

        when(repository.findAll(pageable)).thenReturn(page);

        PagedModel<EntityModel<BooksDTO>> result = service.findAll(pageable);

        assertNotNull(result);
        List<EntityModel<BooksDTO>> entities = result.getContent().stream().toList();
        assertEquals(14, entities.size());

        BooksDTO book1 = entities.get(1).getContent();
        assertNotNull(book1);
        assertHateoasLinks(book1, 1L);

        assertEquals("Title Test1", book1.getTitle());
        assertEquals("Author Test1", book1.getAuthor());

        BooksDTO book4 = entities.get(4).getContent();
        assertNotNull(book4);
        assertHateoasLinks(book4, 4L);
        assertEquals("Title Test4", book4.getTitle());

        BooksDTO book7 = entities.get(7).getContent();
        assertNotNull(book7);
        assertHateoasLinks(book7, 7L);
        assertEquals("Title Test7", book7.getAuthor());
    }
}