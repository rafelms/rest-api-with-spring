package br.com.rafelms.rest_with_spring.controllers;

import br.com.rafelms.rest_with_spring.data.dto.security.AccountCredentialsDTO;
import br.com.rafelms.rest_with_spring.data.dto.security.TokenDTO;
import br.com.rafelms.rest_with_spring.services.AuthService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint!")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody AccountCredentialsDTO credentials) {
        if (credentialsIsInvalid(credentials))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = service.signIn(credentials);

        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "Refresh token for authenticates user and returns a token")
    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refreshToken(@PathVariable String username,
                                          @RequestHeader("Authorization") String refreshToken) {
        if (parameterAsInvalid(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        ResponseEntity<TokenDTO> token = service.refreshToken(username, refreshToken);

        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    private boolean parameterAsInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
                StringUtils.isBlank(credentials.getUsername()) ||
                StringUtils.isBlank(credentials.getPassword());
    }
}
