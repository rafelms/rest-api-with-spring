package br.com.rafelms.rest_with_spring.repository;

import br.com.rafelms.rest_with_spring.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Long> {
}
