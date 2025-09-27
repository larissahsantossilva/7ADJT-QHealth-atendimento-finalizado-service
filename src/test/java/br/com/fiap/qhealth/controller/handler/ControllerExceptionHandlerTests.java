package br.com.fiap.qhealth.controller.handler;

import br.com.fiap.qhealth.dto.IllegalArgumentDTO;
import br.com.fiap.qhealth.dto.ResourceNotFoundDTO;
import br.com.fiap.qhealth.dto.UnprocessableEntityDTO;
import br.com.fiap.qhealth.dto.ValidationErrorDTO;
import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerExceptionHandlerTest {

    private ControllerExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new ControllerExceptionHandler();
    }

    @Test
    void testHandlerResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not found");
        ResponseEntity<ResourceNotFoundDTO> response = handler.handlerResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
        assertEquals("not found", response.getBody().errorMessage());
        assertEquals(404, response.getBody().statusCode());
    }

    @Test
    void testHandlerMethodArgumentNotValidException() {
        var bindingResult = mock(org.springframework.validation.BindingResult.class);
        var fieldError = new org.springframework.validation.FieldError("object", "cpf", "CPF inválido");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ValidationErrorDTO> response = handler.handlerMethodArgumentNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
        assertTrue(response.getBody().errors().get(0).contains("cpf"));
        assertEquals(400, response.getBody().status());
    }

    @Test
    void testHandleMethodArgumentMismatchException_IdAsUUID() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "123", UUID.class, "id", null, new IllegalArgumentException("invalid uuid"));
        ResponseEntity<ResourceNotFoundDTO> response = handler.handleMethodArgumentMismatchException(ex);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
        assertEquals("ID não encontrado", response.getBody().errorMessage());
    }

    @Test
    void testHandleMethodArgumentMismatchException_OtherField() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "abc", String.class, "otherField", null, new IllegalArgumentException("invalid"));
        ResponseEntity<ResourceNotFoundDTO> response = handler.handleMethodArgumentMismatchException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());
        assertEquals("Erro interno do servidor", response.getBody().errorMessage());
    }

    @Test
    void testHandlerUnprocessableEntityException() {
        UnprocessableEntityException ex = new UnprocessableEntityException("unprocessable");
        ResponseEntity<UnprocessableEntityDTO> response = handler.handlerUnprocessableEntityException(ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value());
        assertEquals("unprocessable", response.getBody().errorMessage());
        assertEquals(422, response.getBody().statusCode());
    }

    @Test
    void testHandlerIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("illegal arg");
        ResponseEntity<IllegalArgumentDTO> response = handler.handlerIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
        assertEquals("illegal arg", response.getBody().errorMessage());
        assertEquals(400, response.getBody().statusCode());
    }
}
