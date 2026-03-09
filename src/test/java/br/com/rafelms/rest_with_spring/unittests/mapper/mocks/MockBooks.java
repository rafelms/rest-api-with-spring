package br.com.rafelms.rest_with_spring.unittests.mapper.mocks;

import br.com.rafelms.rest_with_spring.data.dto.v1.BooksDTO;
import br.com.rafelms.rest_with_spring.model.Books;

import java.util.ArrayList;
import java.util.List;

public class MockBooks {





    public List<Books> mockEntityList() {
        List<Books> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public Books mockEntity(Integer number) {
        Books books = new Books();
        books.setId(number.longValue());
        books.setTitle("Title Test" + number);
        books.setType("Type Test" + number);
        books.setAuthor("Author Test" + number);
        return books;
    }

    public BooksDTO mockDTO(Integer number) {
        BooksDTO books = new BooksDTO();
        books.setId(number.longValue());
        books.setTitle("Title Test" + number);
        books.setType("Type Test" + number);
        books.setAuthor("Author Test" + number);
        return books;
    }
}