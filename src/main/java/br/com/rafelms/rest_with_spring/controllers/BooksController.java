package br.com.rafelms.rest_with_spring.controllers;

import br.com.rafelms.rest_with_spring.controllers.docs.BooksControllerDocs;
import br.com.rafelms.rest_with_spring.data.dto.v1.BooksDTO;
import br.com.rafelms.rest_with_spring.services.BooksServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/books/v1")
@Tag(name="Books", description = "Endpoints for Managing Books")
public class BooksController implements BooksControllerDocs {

    @Autowired
    BooksServices service;

    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public BooksDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<BooksDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction){
        Sort sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.by("title").descending() : Sort.by("title").ascending();
        Pageable pageable = PageRequest.of(page, size, sortDirection);
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PostMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public BooksDTO create(@RequestBody BooksDTO books){
        return service.create(books);
    }

    @PutMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE},
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public BooksDTO update(@RequestBody BooksDTO books){return service.update(books);}

    @DeleteMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Usuário apagado com sucesso!");
    }
}
