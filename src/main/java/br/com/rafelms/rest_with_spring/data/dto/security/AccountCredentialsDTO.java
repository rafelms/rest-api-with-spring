package br.com.rafelms.rest_with_spring.data.dto.security;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


// nesta classe vai ser responsável para enviar a requisição de login, e o processamento vai ocorrer na Classe TokenDTO
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class AccountCredentialsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public AccountCredentialsDTO() {
    }
}
