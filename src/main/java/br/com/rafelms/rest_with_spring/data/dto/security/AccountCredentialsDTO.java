package br.com.rafelms.rest_with_spring.data.dto.security;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;


// nesta classe vai ser responsável para enviar a requisição de login, e o processamento vai ocorrer na Classe TokenDTO
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Valid
public class AccountCredentialsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;
}
