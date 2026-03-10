package br.com.rafelms.rest_with_spring.integrationtests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;


//@JsonPropertyOrder({"id", "first_name", "last_name", "gender", "address"}) //Definição da ordem dos atributos no JSON
@Data
public class PersonDTO implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTO personDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(id, personDTO.id) && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(address, personDTO.address) && Objects.equals(gender, personDTO.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName, lastName, address, gender);
    }
}
