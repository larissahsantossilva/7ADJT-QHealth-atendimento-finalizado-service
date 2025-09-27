package br.com.fiap.qhealth.utils;

import br.com.fiap.qhealth.dto.request.AtendimentoAtualizarBodyRequest;
import br.com.fiap.qhealth.dto.request.AtendimentoBodyRequest;
import br.com.fiap.qhealth.dto.response.AtendimentoBodyResponse;
import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.model.Atendimento;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class QHealthUtilsTest {

    @Test
    void testUuidValidator_ValidUuid() {
        UUID validUuid = UUID.randomUUID();
        assertDoesNotThrow(() -> QHealthUtils.uuidValidator(validUuid));
    }

    @Test
    void testUuidValidator_InvalidUuid() {
        UUID invalidUuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        assertThrows(ResourceNotFoundException.class, () -> QHealthUtils.uuidValidator(invalidUuid));
    }

    @Test
    void testConvertToAtendimento_FromBodyRequest() {
        AtendimentoBodyRequest dto = new AtendimentoBodyRequest("12345678900", "AGUARDANDO", UUID.randomUUID());

        Atendimento entity = QHealthUtils.convertToAtendimento(dto);

        assertNotNull(entity);
        assertEquals("12345678900", entity.getCpf());
        assertEquals("AGUARDANDO", entity.getStatus());
        assertNotNull(entity.getFilaId());
    }

    @Test
    void testConvertToAtendimento_FromAtualizarBodyRequest() {
        AtendimentoAtualizarBodyRequest dto = new AtendimentoAtualizarBodyRequest(
                "98765432100", "FINALIZADO", UUID.randomUUID(), 1
        );

        Atendimento entity = QHealthUtils.convertToAtendimento(dto);

        assertNotNull(entity);
        assertEquals("98765432100", entity.getCpf());
        assertEquals("FINALIZADO", entity.getStatus());
        assertNotNull(entity.getFilaId());
    }

    @Test
    void testConvertToAtendimento_EntityToResponse() {
        UUID id = UUID.randomUUID();
        UUID filaId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Atendimento entity = Atendimento.builder()
                .id(id)
                .cpf("11122233344")
                .status("AGUARDANDO")
                .filaId(filaId)
                .dataCriacao(now.minusHours(2))
                .dataUltimaAlteracao(now.minusHours(1))
                .build();

        AtendimentoBodyResponse response = QHealthUtils.convertToAtendimento(entity, 5);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("11122233344", response.getCpf());
        assertEquals("AGUARDANDO", response.getStatus());
        assertEquals(filaId, response.getFilaId());
        assertEquals(5, response.getPosicaoFila());
        assertEquals(entity.getDataCriacao(), response.getDataCriacao());
        assertEquals(entity.getDataUltimaAlteracao(), response.getDataUltimaAlteracao());
    }
}
