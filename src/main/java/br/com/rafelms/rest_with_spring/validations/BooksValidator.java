package br.com.rafelms.rest_with_spring.validations;

import br.com.rafelms.rest_with_spring.data.dto.v1.BooksDTO;
import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
import org.springframework.stereotype.Component;

@Component
public class BooksValidator {
    private void validateTitle(String title) {

        if (title == null || title.isBlank() || title.length() > 80) {
            throw new IllegalArgumentException("Title cannot be null or greater than 80 characters!");
        }
    }

    private void validateType(String type) {
        if (type == null || type.isBlank() ||type.length() > 20) {

            throw new IllegalArgumentException("Type cannot be null or greater than 20 characters!");
        }
    }

    private void validateAuthor(String author) {
        if (author == null || author.isBlank() ||author.length() > 80) {

            throw new IllegalArgumentException("Author cannot be null or greater than 80 characters!");
        }
    }

    public void validateBooks(BooksDTO booksDTO){
        this.validateTitle(booksDTO.getTitle());
        this.validateType(booksDTO.getType());
        this.validateAuthor(booksDTO.getAuthor());
    }
}
