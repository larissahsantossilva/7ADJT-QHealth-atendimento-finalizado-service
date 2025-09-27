package br.com.fiap.qhealth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundDTOTest {

    @Test
    void testRecordCreationAndGetters() {
        ResourceNotFoundDTO dto = new ResourceNotFoundDTO("Not found", 404);

        assertEquals("Not found", dto.errorMessage());
        assertEquals(404, dto.statusCode());
    }

    @Test
    void testEqualsAndHashCode() {
        ResourceNotFoundDTO dto1 = new ResourceNotFoundDTO("Not found", 404);
        ResourceNotFoundDTO dto2 = new ResourceNotFoundDTO("Not found", 404);
        ResourceNotFoundDTO dto3 = new ResourceNotFoundDTO("Different", 500);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        ResourceNotFoundDTO dto = new ResourceNotFoundDTO("Not found", 404);
        String result = dto.toString();

        assertTrue(result.contains("Not found"));
        assertTrue(result.contains("404"));
    }
}
