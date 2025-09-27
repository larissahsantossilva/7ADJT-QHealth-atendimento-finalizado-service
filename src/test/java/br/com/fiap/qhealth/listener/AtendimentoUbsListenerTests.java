package br.com.fiap.qhealth.listener;

import br.com.fiap.qhealth.listener.json.AtendimentoUbsRequestJson;
import br.com.fiap.qhealth.model.Atendimento;
import br.com.fiap.qhealth.service.AtendimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class AtendimentoUbsListenerTest {

    @Mock
    private AtendimentoService atendimentoService;

    @InjectMocks
    private AtendimentoUbsListener listener;

    private AtendimentoUbsRequestJson requestJson;

    @BeforeEach
    void setUp() {
        requestJson = new AtendimentoUbsRequestJson(
                UUID.randomUUID(),
                "12345678900",
                UUID.randomUUID(),
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now()
        );
    }

    @Test
    void testEscutarMensagem_ChamaServiceComAtendimentoCorreto() {
        listener.escutarMensagem(requestJson);

        // Captura o objeto passado ao service
        ArgumentCaptor<Atendimento> captor = ArgumentCaptor.forClass(Atendimento.class);
        verify(atendimentoService, times(1)).criarAtendimento(captor.capture(), eq(true));

        Atendimento atendimento = captor.getValue();

        assertEquals(requestJson.id(), atendimento.getId());
        assertEquals(requestJson.cpf(), atendimento.getCpf());
        assertEquals("AGUARDANDO", atendimento.getStatus());
        assertEquals(requestJson.filaId(), atendimento.getFilaId());
        assertEquals(requestJson.dataCriacao(), atendimento.getDataCriacao());
        assertEquals(requestJson.dataUltimaAlteracao(), atendimento.getDataUltimaAlteracao());
    }

    @Test
    void testEscutarMensagem_NaoLancaExcecao() {
        assertDoesNotThrow(() -> listener.escutarMensagem(requestJson));
    }
}
