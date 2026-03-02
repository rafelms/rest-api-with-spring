package br.com.rafelms.rest_with_spring.services;

import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
//import br.com.rafelms.rest_with_spring.data.dto.v2.PersonDTOV2;
import br.com.rafelms.rest_with_spring.exception.ResourceNotFoundException;
import static br.com.rafelms.rest_with_spring.mapper.ObjectMapper.parseObject;

//import br.com.rafelms.rest_with_spring.mapper.custom.PersonMapper;
import br.com.rafelms.rest_with_spring.model.Person;
import br.com.rafelms.rest_with_spring.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.rafelms.rest_with_spring.mapper.ObjectMapper.parseListObjects;

@Service // disponibiliza a classe e deixa injetavel
public class PersonServices {
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName()); // adicionando logger

    @Autowired
    PersonRepository repository;

    /**Usado para versionamento de API(estudo)*/
//    @Autowired
//    PersonMapper converter;

    public List<PersonDTO> findAll(){
        logger.info("Finding one Person!");
        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }
/**Usado para versionamento de API(estudo)*/
//    public PersonDTOV2 createV2(PersonDTOV2 person){
//        logger.info("Creating one Person V2!");
//        var entity = converter.convertDTOToEntity(person);
//        return converter.convertEntityToDTO(repository.save(entity));
//    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one Person!");

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }
}
