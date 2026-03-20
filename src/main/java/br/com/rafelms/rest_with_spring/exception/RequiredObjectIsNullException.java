package br.com.rafelms.rest_with_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {
    public RequiredObjectIsNullException() {

        super("Não é permitido cadastrar um usuário nulo!");
    }

    public RequiredObjectIsNullException(String message) {

        super(message);
    }
}
