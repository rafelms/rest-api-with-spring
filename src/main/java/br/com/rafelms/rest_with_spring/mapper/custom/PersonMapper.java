/**Usado para fazer versionamento da API com o v2,
 * Usado para estudo!*/

//package br.com.rafelms.rest_with_spring.mapper.custom;
//
//import br.com.rafelms.rest_with_spring.data.dto.v2.PersonDTOV2;
//import br.com.rafelms.rest_with_spring.model.Person;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class PersonMapper {
//
//    public PersonDTOV2 convertEntityToDTO(Person person){
//        PersonDTOV2 dto = new PersonDTOV2();
//
//        dto.setId(person.getId());
//        dto.setFirstName(person.getFirstName());
//        dto.setLastName(person.getLastName());
//        dto.setBirthDay(new Date());
//        dto.setAddress(person.getAddress());
//        dto.setGender(person.getGender());
//        return dto;
//    }
//
//    public Person convertDTOToEntity(PersonDTOV2 person){
//        Person entity = new Person();
//
//        entity.setId(person.getId());
//        entity.setFirstName(person.getFirstName());
//        entity.setLastName(person.getLastName());
//        //entity.setBirthDay(new Date());
//        entity.setAddress(person.getAddress());
//        entity.setGender(person.getGender());
//        return entity;
//    }
//}
