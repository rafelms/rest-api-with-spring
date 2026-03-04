package br.com.rafelms.rest_with_spring.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;


//@JsonPropertyOrder({"id", "first_name", "last_name", "gender", "address"}) //Definição da ordem dos atributos no JSON
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;

//    @JsonProperty("first_name")//Alterando o nome do atributo no JSON
    private String firstName;

//    @JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String lastName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private String address;

    //@JsonIgnore // Suprimir algum atributo
    private String gender;

    public PersonDTO(Long id, String firstName, String lastName, String address, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
    }

    public PersonDTO(){

    }
}
