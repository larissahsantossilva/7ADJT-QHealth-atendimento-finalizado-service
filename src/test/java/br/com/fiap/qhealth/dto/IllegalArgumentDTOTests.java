package br.com.fiap.qhealth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IllegalArgumentDTOTest {

    @Test
    void testRecordCreationAndGetters() {
        IllegalArgumentDTO dto = new IllegalArgumentDTO(400, "Invalid argument");

        assertEquals(400, dto.statusCode());
        assertEquals("Invalid argument", dto.errorMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        IllegalArgumentDTO dto1 = new IllegalArgumentDTO(400, "Invalid argument");
        IllegalArgumentDTO dto2 = new IllegalArgumentDTO(400, "Invalid argument");
        IllegalArgumentDTO dto3 = new IllegalArgumentDTO(422, "Other error");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        IllegalArgumentDTO dto = new IllegalArgumentDTO(400, "Invalid argument");
        String result = dto.toString();

        assertTrue(result.contains("400"));
        assertTrue(result.contains("Invalid argument"));
    }
}
