package br.com.rafelms.rest_with_spring.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {
}
