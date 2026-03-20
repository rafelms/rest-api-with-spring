package br.com.rafelms.rest_with_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(){
        super("Não foi possível fazer o cadastro, usuário já existente!");
    }

    public UsernameAlreadyExistsException(String message) {super(message);}


}
