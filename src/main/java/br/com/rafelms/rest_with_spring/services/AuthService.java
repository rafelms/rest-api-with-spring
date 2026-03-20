package br.com.rafelms.rest_with_spring.services;

import br.com.rafelms.rest_with_spring.data.dto.security.AccountCredentialsDTO;
import br.com.rafelms.rest_with_spring.data.dto.security.TokenDTO;
import br.com.rafelms.rest_with_spring.exception.RequiredObjectIsNullException;
import br.com.rafelms.rest_with_spring.exception.UsernameAlreadyExistsException;
import br.com.rafelms.rest_with_spring.model.User;
import br.com.rafelms.rest_with_spring.repository.UserRepository;
import br.com.rafelms.rest_with_spring.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static br.com.rafelms.rest_with_spring.mapper.ObjectMapper.parseObject;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword()
            )
        );

        var user = repository.findByUsername(credentials.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("Username " + credentials.getUsername() + " não encontrado!");
        }



        var token = tokenProvider.createAccessToken(
                credentials.getUsername(),
                user.getRoles()
        );

        return ResponseEntity.ok(token);
    }

    public ResponseEntity<TokenDTO> refreshToken(String username, String refreshToken){
        var user = repository.findByUsername(username);
        TokenDTO token;

        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        token = tokenProvider.refreshToken(refreshToken);
        return ResponseEntity.ok(token);
    }

    private String generateHashedPassword(String password) {
        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder( "",
                8, //comprimeto de bytes
                185000, //n de vezes que o algoritmo vai rodar
                Pbkdf2PasswordEncoder
                        .SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(passwordEncoder);
       return passwordEncoder.encode(password);
    }

    public AccountCredentialsDTO createUser(AccountCredentialsDTO user) {
        if (user == null) throw new RequiredObjectIsNullException();

        var exists = repository.findByUsername(user.getUsername());
        if (exists != null){
            throw new UsernameAlreadyExistsException();
        }

        logger.info("Creating one new User!");
        var entity = new User();
        entity.setFullName(user.getFullname());
        entity.setUserName(user.getUsername());
        entity.setPassword(generateHashedPassword(user.getPassword())); //vai gerar a senha encriptografada
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);

        var dto = repository.save(entity);
        return new AccountCredentialsDTO(dto.getUsername(), dto.getPassword(), dto.getFullName());
    }
}
