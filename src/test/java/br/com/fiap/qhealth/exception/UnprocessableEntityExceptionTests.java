package br.com.fiap.qhealth.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnprocessableEntityExceptionTest {

    @Test
    void testMessageIsStored() {
        String msg = "unprocessable";
        UnprocessableEntityException ex = new UnprocessableEntityException(msg);
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void testIsRuntimeException() {
        assertTrue(new UnprocessableEntityException("x") instanceof RuntimeException);
    }
}
