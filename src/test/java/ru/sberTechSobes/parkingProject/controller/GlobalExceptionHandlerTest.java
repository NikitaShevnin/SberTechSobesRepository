package ru.sberTechSobes.parkingProject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBusinessExceptionReturnsBadRequest() {
        ResponseEntity<String> response = handler.handleBusinessException(new IllegalStateException("m"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("m", response.getBody());
    }

    @Test
    void handleOtherExceptionsReturnsServerError() {
        ResponseEntity<String> response = handler.handleOtherExceptions(new RuntimeException());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Внутренняя ошибка сервиса", response.getBody());
    }
}
