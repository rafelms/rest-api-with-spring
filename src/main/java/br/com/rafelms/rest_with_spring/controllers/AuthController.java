package br.com.rafelms.rest_with_spring.controllers;

import br.com.rafelms.rest_with_spring.controllers.docs.AuthControllerDocs;
import br.com.rafelms.rest_with_spring.data.dto.security.AccountCredentialsDTO;
import br.com.rafelms.rest_with_spring.data.dto.security.TokenDTO;
import br.com.rafelms.rest_with_spring.services.AuthService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint!")
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    AuthService service;

    @PostMapping("/signin")
    @Override
    public ResponseEntity<?> signin(@Valid @RequestBody AccountCredentialsDTO credentials) {
        if (credentialsIsInvalid(credentials))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = service.signIn(credentials);

        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/refresh/{username}")
    @Override
    public ResponseEntity<?> refreshToken(@PathVariable String username,
                                          @RequestHeader("Authorization") String refreshToken) {
        if (parameterAsInvalid(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        ResponseEntity<TokenDTO> token = service.refreshToken(username, refreshToken);

        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    @PostMapping(value = "/createUser",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public AccountCredentialsDTO create(@RequestBody AccountCredentialsDTO credentials) {
        return service.createUser(credentials);
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
