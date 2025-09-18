package br.com.fiap.qhealth.dto;

import java.util.List;

public record ValidationErrorDTO(List<String> errors, int status) { }
