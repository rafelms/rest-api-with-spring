package br.com.rafelms.rest_with_spring;

import br.com.rafelms.rest_with_spring.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service // disponibiliza a classe e deixa injetavel
public class PersonServices {
    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName()); // adicionando logger


    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname "+ i);
        person.setLastName("Lastname "+ i);
        person.setAddress("Some address in Brasil");
        person.setGender("Masculino");
        return person;
    }

    public List<Person> findAll(){
        logger.info("Finding one Person!");
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 8; i++){
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    public Person findById(String id){

        logger.info("Finding one Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Rafael");
        person.setLastName("Menezes");
        person.setAddress("Palmas TO");
        person.setGender("Masculino");
        return person;

    }

    public Person create(Person person){

        logger.info("Creating one Person!");

        return person;

    }

    public Person update(Person person){

        logger.info("Updating one Person!");

        return person;
    }

    public Person delete(String id){
        logger.info("Deleting one Person!");

        return null;
    }
}
