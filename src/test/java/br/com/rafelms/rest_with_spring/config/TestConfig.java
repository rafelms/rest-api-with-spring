package br.com.rafelms.rest_with_spring.config;

public interface TestConfig {
    int SERVER_PORT = 8888;

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";
    String ORIGIN_RAFAEL = "https://rafaelms.com.br";
    String ORIGIN_SLA = "https://slasla.com.br";
}