package br.com.fiap.qhealth.listener.json;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoUbsRequestJsonTest {

    @Test
    void testRecordValues() {
        UUID id = UUID.randomUUID();
        String cpf = "12345678900";
        UUID filaId = UUID.randomUUID();
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        AtendimentoUbsRequestJson json = new AtendimentoUbsRequestJson(id, cpf, filaId, dataCriacao, dataUltimaAlteracao);

        assertEquals(id, json.id());
        assertEquals(cpf, json.cpf());
        assertEquals(filaId, json.filaId());
        assertEquals(dataCriacao, json.dataCriacao());
        assertEquals(dataUltimaAlteracao, json.dataUltimaAlteracao());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        UUID filaId = UUID.randomUUID();
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        AtendimentoUbsRequestJson json1 = new AtendimentoUbsRequestJson(id, "12345678900", filaId, dataCriacao, dataUltimaAlteracao);
        AtendimentoUbsRequestJson json2 = new AtendimentoUbsRequestJson(id, "12345678900", filaId, dataCriacao, dataUltimaAlteracao);

        assertEquals(json1, json2);
        assertEquals(json1.hashCode(), json2.hashCode());
    }

    @Test
    void testToStringNotNull() {
        AtendimentoUbsRequestJson json = new AtendimentoUbsRequestJson(
                UUID.randomUUID(),
                "98765432100",
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        assertNotNull(json.toString());
        assertTrue(json.toString().contains("cpf=98765432100"));
    }
}
