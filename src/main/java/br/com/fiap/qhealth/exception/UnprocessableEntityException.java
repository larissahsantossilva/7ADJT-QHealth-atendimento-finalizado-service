package br.com.fiap.qhealth.exception;

import java.io.Serial;

public class UnprocessableEntityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UnprocessableEntityException(String errorMessage){
        super(errorMessage);
    }
}
