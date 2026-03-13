package br.com.rafelms.rest_with_spring.services;

import br.com.rafelms.rest_with_spring.controllers.BooksController;
import br.com.rafelms.rest_with_spring.controllers.PersonController;
import br.com.rafelms.rest_with_spring.controllers.docs.BooksControllerDocs;
import br.com.rafelms.rest_with_spring.data.dto.v1.BooksDTO;
import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
import br.com.rafelms.rest_with_spring.exception.RequiredObjectIsNullException;
import br.com.rafelms.rest_with_spring.exception.ResourceNotFoundException;
import static br.com.rafelms.rest_with_spring.mapper.ObjectMapper.parseObject;

import br.com.rafelms.rest_with_spring.model.Books;
import br.com.rafelms.rest_with_spring.repository.BooksRepository;
import br.com.rafelms.rest_with_spring.validations.BooksValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

import static br.com.rafelms.rest_with_spring.mapper.ObjectMapper.parseListObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BooksServices {
    private Logger logger = LoggerFactory.getLogger(BooksServices.class.getName());

    @Autowired
    BooksRepository repository;

    @Autowired
    BooksValidator validator;

    @Autowired
    PagedResourcesAssembler<BooksDTO> assembler;



    public BooksDTO findById(Long id){
        logger.info("Finding one Book!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var dto = parseObject(entity, BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }


    public PagedModel<EntityModel<BooksDTO>> findAll(Pageable pageable){
        logger.info("Finding one Book!");

        var Books = repository.findAll(pageable);

        var booksWithLinks = Books.map(books -> {
            var dto = parseObject(books, BooksDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(BooksController.class)
                                .findAll(pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(booksWithLinks, findAllLink);
    }


    public BooksDTO create(BooksDTO books){
        if(books == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book!");

        validator.validateBooks(books);

        var entity = parseObject(books, Books.class);

        var dto = parseObject(repository.save(entity), BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }



    public ResponseEntity<?> delete(Long id) {
        logger.info("Deleting one Book!");

        Books entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
        return null;
    }


    public BooksDTO update(BooksDTO books){
        if(books == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Book!");

        validator.validateBooks(books);

        Books entity = repository.findById(books.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setTitle(books.getTitle());
        entity.setType(books.getType());
        entity.setAuthor(books.getAuthor());

        var dto = parseObject(repository.save(entity), BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    private static void addHateoasLinks(BooksDTO dto) {

        dto.add(linkTo(methodOn(BooksController.class)
                .findById(dto.getId()))
                .withSelfRel()
                .withType("GET"));

        dto.add(linkTo(methodOn(BooksController.class)
                .delete(dto.getId()))
                .withRel("delete")
                .withType("DELETE"));

        dto.add(linkTo(methodOn(BooksController.class)
                .findAll(1, 5, "asc"))
                .withRel("findAll")
                .withType("GET"));

        dto.add(linkTo(methodOn(BooksController.class)
                .create(null))
                .withRel("create")
                .withType("POST"));

        dto.add(linkTo(methodOn(BooksController.class)
                .update(null))
                .withRel("update")
                .withType("PUT"));
    }
}
