package br.com.rafelms.rest_with_spring.services;

import br.com.rafelms.rest_with_spring.data.dto.v1.BooksDTO;
import br.com.rafelms.rest_with_spring.exception.RequiredObjectIsNullException;
import br.com.rafelms.rest_with_spring.model.Books;
import br.com.rafelms.rest_with_spring.repository.BooksRepository;
import br.com.rafelms.rest_with_spring.unitetests.mapper.mocks.MockBooks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        assertTrue(dto.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("api/books/v1")
                        && "GET".equals(link.getType())
                ));

        assertTrue(dto.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("api/books/v1")
                        && "POST".equals(link.getType())
                ));

        assertTrue(dto.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("api/books/v1")
                        && "PUT".equals(link.getType())
                ));

        assertTrue(dto.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("api/books/v1/" + id)
                        && "DELETE".equals(link.getType())
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
        assertEquals("Type Test1", result.getType());
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
        assertEquals("Type Test1", result.getType());
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
        assertEquals("Type Test1", result.getType());
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

        List<Books> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        List<BooksDTO> result = service.findAll();

        assertNotNull(result);
        assertEquals(14, result.size());

        var book1 = result.get(1);
        assertHateoasLinks(book1, 1L);

        assertEquals("Title Test1", book1.getTitle());
        assertEquals("Type Test1", book1.getType());
        assertEquals("Author Test1", book1.getAuthor());

        var book4 = result.get(4);
        assertHateoasLinks(book4, 4L);

        assertEquals("Title Test4", book4.getTitle());
        assertEquals("Type Test4", book4.getType());
        assertEquals("Author Test4", book4.getAuthor());

        var book7 = result.get(7);
        assertHateoasLinks(book7, 7L);

        assertEquals("Title Test7", book7.getTitle());
        assertEquals("Type Test7", book7.getType());
        assertEquals("Author Test7", book7.getAuthor());
    }
}