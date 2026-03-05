package br.com.rafelms.rest_with_spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="books")
@Getter
@Setter
public class Books implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false, length = 80)
    private String title;


    @Column(name="type", nullable = false, length = 20)
    private String type;

    @Column(name="author", nullable = false, length = 80)
    private String author;

    public Books(){

    }

    public Books(Long id, String title, String type, String author) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Books books)) return false;
        return Objects.equals(id, books.id) && Objects.equals(title, books.title) && Objects.equals(type, books.type) && Objects.equals(author, books.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, author);
    }
}
