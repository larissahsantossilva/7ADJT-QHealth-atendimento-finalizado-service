package br.com.fiap.qhealth.exception;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}

