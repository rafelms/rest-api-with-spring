package br.com.rafelms.rest_with_spring.validations;

import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonValidator {
    private void validateFirstName(String firstName) {

        if (firstName == null || firstName.isBlank() || firstName.length() > 20) {
            throw new IllegalArgumentException("First name cannot be null or greater than 20 characters!");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || lastName.isBlank() ||lastName.length() > 20) {

            throw new IllegalArgumentException("Last name cannot be null or greater than 20 characters!");
        }
    }

    private void validateAddress(String address) {
        if (address == null || address.isBlank() ||address.length() > 100) {

            throw new IllegalArgumentException("Address cannot be null or greater than 100 characters!");
        }
    }

    private void validateGender(String gender) {
        if (gender == null ||
                (!gender.trim().equalsIgnoreCase("male") && !gender.trim()
                        .equalsIgnoreCase("female"))) {

            throw new IllegalArgumentException("Gender must be 'Male' or 'Female'!");
        }
    }

    public void validatePerson(PersonDTO personDTO){
        this.validateFirstName(personDTO.getFirstName());
        this.validateLastName(personDTO.getLastName());
        this.validateAddress(personDTO.getAddress());
        this.validateGender(personDTO.getGender());
    }
}
