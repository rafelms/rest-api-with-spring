package br.com.rafelms.rest_with_spring.controllers.docs;

import br.com.rafelms.rest_with_spring.data.dto.security.AccountCredentialsDTO;
import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AuthControllerDocs {
    @Operation(summary = "Autentica o usuário",
            tags = "Auth",
            description = "Autentica o usuário e devolve os tokens",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AccountCredentialsDTO.class))
                    ),
                    @ApiResponse(description = "Not Authorized - Usuário inexistente ou senha inválida", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    ResponseEntity<?> signin(AccountCredentialsDTO credentials);

    @Operation(summary = "Atualiza o token",
            tags = "Auth",
            description = "Atualiza o token da sessão do usuário",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AccountCredentialsDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    ResponseEntity<?> refreshToken(String username,
                                   String refreshToken);

    @Operation(summary = "Cria um novo usuário",
            tags = "Auth",
            description = "Cria um novo usuário, com nome completo e senha",
            responses = {
                    @ApiResponse(description = "Created",
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AccountCredentialsDTO.class))
                    ),
                    @ApiResponse(description = "Not Authorized - Usuário já existe", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    AccountCredentialsDTO create(AccountCredentialsDTO credentials);
}
