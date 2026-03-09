package br.com.rafelms.rest_with_spring.controllers;

import br.com.rafelms.rest_with_spring.controllers.docs.PersonControllerDocs;
import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
//import br.com.rafelms.rest_with_spring.data.dto.v2.PersonDTOV2;
import br.com.rafelms.rest_with_spring.services.PersonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http:/localhost:8080")
@RestController
@RequestMapping("api/person/v1")
@Tag(name="People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {

    @Autowired // faz a instancia
    private PersonServices service;

    //@CrossOrigin(origins = "http:/localhost:8080")
    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    @Override
    public PersonDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    @Override
    public List<PersonDTO> findAll(){
        return service.findAll();
    }

    //@CrossOrigin(origins = {"http:/localhost:8080", "http:/rafael.ms.com.br" })
    @PostMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    @Override
    public PersonDTO create(@RequestBody PersonDTO person){
        return service.create(person);
    }

    @PutMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE},
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    @Override
    public PersonDTO update(@RequestBody PersonDTO person){return service.update(person);}

    @DeleteMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Usuário apagado com sucesso!");
    }

//    @PostMapping(value = "/v2",
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public PersonDTOV2 create(@RequestBody PersonDTOV2 person){
//            return service.createV2(person);
//    }
}
