package br.com.rafelms.rest_with_spring.services;

import br.com.rafelms.rest_with_spring.controllers.PersonController;
import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
//import br.com.rafelms.rest_with_spring.data.dto.v2.PersonDTOV2;
import br.com.rafelms.rest_with_spring.exception.RequiredObjectIsNullException;
import br.com.rafelms.rest_with_spring.exception.ResourceNotFoundException;
import static br.com.rafelms.rest_with_spring.mapper.ObjectMapper.parseObject;

//import br.com.rafelms.rest_with_spring.mapper.custom.PersonMapper;
import br.com.rafelms.rest_with_spring.model.Person;
import br.com.rafelms.rest_with_spring.repository.PersonRepository;
import br.com.rafelms.rest_with_spring.validations.PersonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import static br.com.rafelms.rest_with_spring.mapper.ObjectMapper.parseListObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service // disponibiliza a classe e deixa injetavel
public class PersonServices {
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName()); // adicionando logger

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonValidator validator;

    /**Usado para versionamento de API(estudo)*/
//    @Autowired
//    PersonMapper converter;

    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public List<PersonDTO> findAll(){
        logger.info("Finding one Person!");
        var persons =  parseListObjects(repository.findAll(), PersonDTO.class);
        persons.forEach(PersonServices::addHateoasLinks);
        return persons;
    }


    public PersonDTO create(PersonDTO person){
        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");

        validator.validatePerson(person);

        var entity = parseObject(person, Person.class);

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }



    public void delete(Long id) {
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }


    public PersonDTO update(PersonDTO person){
        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");

        validator.validatePerson(person);

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    private static void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
    }

    /**Usado para versionamento de API(estudo)*/
//    public PersonDTOV2 createV2(PersonDTOV2 person){
//        logger.info("Creating one Person V2!");
//        var entity = converter.convertDTOToEntity(person);
//        return converter.convertEntityToDTO(repository.save(entity));
//    }
}
