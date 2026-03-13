package br.com.rafelms.rest_with_spring.data.dto.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@Relation(collectionRelation = "books")
public class BooksDTO extends RepresentationModel<BooksDTO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String type;
    private String author;


    public BooksDTO(Long id, String title, String type, String author) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.author = author;
    }


    public BooksDTO(){

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BooksDTO booksDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(id, booksDTO.id) && Objects.equals(title, booksDTO.title) && Objects.equals(type, booksDTO.type) && Objects.equals(author, booksDTO.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, title, type, author);
    }
}
