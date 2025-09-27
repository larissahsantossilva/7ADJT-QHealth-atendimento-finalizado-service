package br.com.fiap.qhealth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnprocessableEntityDTOTest {

    @Test
    void testRecordCreationAndGetters() {
        UnprocessableEntityDTO dto = new UnprocessableEntityDTO(422, "Unprocessable entity");

        assertEquals(422, dto.statusCode());
        assertEquals("Unprocessable entity", dto.errorMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        UnprocessableEntityDTO dto1 = new UnprocessableEntityDTO(422, "Unprocessable entity");
        UnprocessableEntityDTO dto2 = new UnprocessableEntityDTO(422, "Unprocessable entity");
        UnprocessableEntityDTO dto3 = new UnprocessableEntityDTO(400, "Bad request");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        UnprocessableEntityDTO dto = new UnprocessableEntityDTO(422, "Unprocessable entity");
        String result = dto.toString();

        assertTrue(result.contains("422"));
        assertTrue(result.contains("Unprocessable entity"));
    }
}
