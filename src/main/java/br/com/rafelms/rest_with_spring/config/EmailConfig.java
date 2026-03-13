package br.com.rafelms.rest_with_spring.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class EmailConfig {

    String host;
    private int port;
    private String username;
    private String password;
    private String from;
    private boolean ssl;

    public EmailConfig() {}
}
