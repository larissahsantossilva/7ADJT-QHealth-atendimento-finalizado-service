package br.com.fiap.qhealth.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorDTOTest {

    @Test
    void testRecordCreationAndGetters() {
        List<String> errors = List.of("cpf: inválido", "status: obrigatório");
        ValidationErrorDTO dto = new ValidationErrorDTO(errors, 400);

        assertEquals(errors, dto.errors());
        assertEquals(400, dto.status());
    }

    @Test
    void testEqualsAndHashCode() {
        List<String> errors = List.of("erro1", "erro2");

        ValidationErrorDTO dto1 = new ValidationErrorDTO(errors, 400);
        ValidationErrorDTO dto2 = new ValidationErrorDTO(errors, 400);
        ValidationErrorDTO dto3 = new ValidationErrorDTO(List.of("outro"), 422);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        ValidationErrorDTO dto = new ValidationErrorDTO(List.of("cpf: inválido"), 400);
        String result = dto.toString();

        assertTrue(result.contains("cpf: inválido"));
        assertTrue(result.contains("400"));
    }

    @Test
    void testWithEmptyErrorsList() {
        ValidationErrorDTO dto = new ValidationErrorDTO(List.of(), 422);

        assertNotNull(dto.errors());
        assertTrue(dto.errors().isEmpty());
        assertEquals(422, dto.status());
    }
}
