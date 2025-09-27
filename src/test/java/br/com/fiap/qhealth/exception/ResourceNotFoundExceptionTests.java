package br.com.fiap.qhealth.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testMessageIsStored() {
        String msg = "not found";
        ResourceNotFoundException ex = new ResourceNotFoundException(msg);
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void testIsRuntimeException() {
        assertTrue(new ResourceNotFoundException("x") instanceof RuntimeException);
    }
}
