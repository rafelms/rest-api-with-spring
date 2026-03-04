package br.com.rafelms.rest_with_spring.controllers;

import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
//import br.com.rafelms.rest_with_spring.data.dto.v2.PersonDTOV2;
import br.com.rafelms.rest_with_spring.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/person/v1")
@Tag(name="People", description = "Endpoints for Managing People")
public class PersonController {

    @Autowired // faz a instancia
    private PersonServices service;

    @GetMapping(value = "/{id}",
                produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Find a Person",
            tags = "Person",
            description = "Find a specific person by your ID",
            responses = {
                @ApiResponse(description = "Success",
                        responseCode = "200",
                        content = @Content(schema = @Schema(implementation = PersonDTO.class))
                ),
                @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public PersonDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping(produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})/*Não preciso passar o path {id}, pois o default vai ser All*/
    @Operation(summary = "Find All People",
            tags = "People",
            description = "Find All People",
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(implementation = PersonDTO.class)
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    public List<PersonDTO> findAll(){
        return service.findAll();
    }

    @PostMapping(
            produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE},
            consumes = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE})
    public PersonDTO create(@RequestBody PersonDTO person){
        return service.create(person);
    }

    @PutMapping(produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
                consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public PersonDTO update(@RequestBody PersonDTO person){return service.update(person);}

    @DeleteMapping(value = "/{id}",
                    produces = {
                        MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!"); // Retorno do 204
    }

//    @PostMapping(value = "/v2",
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public PersonDTOV2 create(@RequestBody PersonDTOV2 person){
//            return service.createV2(person);
//    }
}
