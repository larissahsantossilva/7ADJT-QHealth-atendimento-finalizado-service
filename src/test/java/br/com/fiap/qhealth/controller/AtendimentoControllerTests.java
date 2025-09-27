package br.com.fiap.qhealth.controller;

import br.com.fiap.qhealth.dto.request.AtendimentoAtualizarBodyRequest;
import br.com.fiap.qhealth.dto.request.AtendimentoBodyRequest;
import br.com.fiap.qhealth.dto.response.AtendimentoBodyResponse;
import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import br.com.fiap.qhealth.model.Atendimento;
import br.com.fiap.qhealth.service.AtendimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AtendimentoControllerTest {

    @Mock
    private AtendimentoService atendimentoService;

    @InjectMocks
    private AtendimentoController controller;

    private UUID id;
    private Atendimento atendimento;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        id = UUID.randomUUID();
        atendimento = Atendimento.builder()
                .id(id)
                .cpf("12345678900")
                .status("AGUARDANDO")
                .filaId(UUID.randomUUID())
                .build();
    }

    @Test
    void testListarAtendimentos() {
        Page<Atendimento> page = new PageImpl<>(List.of(atendimento));
        when(atendimentoService.listarAtendimentos(anyInt(), anyInt())).thenReturn(page);
        when(atendimentoService.calcularPosicaoFila(any(), any())).thenReturn(1);

        ResponseEntity<List<AtendimentoBodyResponse>> response = controller.listarAtendimentos(0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testBuscarAtendimentoPorId_Sucesso() {
        when(atendimentoService.buscarAtendimentoPorId(id)).thenReturn(atendimento);
        when(atendimentoService.calcularPosicaoFila(any(), any())).thenReturn(1);

        ResponseEntity<AtendimentoBodyResponse> response = controller.buscarAtendimentoPorId(id);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("12345678900", response.getBody().getCpf());
    }

    @Test
    void testBuscarAtendimentoPorId_NaoEncontrado() {
        when(atendimentoService.buscarAtendimentoPorId(id)).thenThrow(new ResourceNotFoundException("Atendimento não encontrado."));

        assertThrows(ResourceNotFoundException.class, () -> controller.buscarAtendimentoPorId(id));
    }

    @Test
    void testCriarAtendimento() {
        when(atendimentoService.criarAtendimento(any(Atendimento.class), eq(false))).thenReturn(atendimento);

        AtendimentoBodyRequest req = new AtendimentoBodyRequest("12345678900", "AGUARDANDO", UUID.randomUUID());
        ResponseEntity<UUID> response = controller.criarAtendimento(req);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(atendimento.getId(), response.getBody());
    }

    @Test
    void testAtualizarAtendimento() {
        doNothing().when(atendimentoService).atualizarAtendimentoExistente(any(), eq(id));

        AtendimentoAtualizarBodyRequest req = new AtendimentoAtualizarBodyRequest("12345678900", "AGUARDANDO", UUID.randomUUID(), 1);
        ResponseEntity<String> response = controller.atualizarAtendimento(id, req);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Atendimento atualizado com sucesso", response.getBody());
    }

    @Test
    void testExcluirAtendimento_Sucesso() {
        doNothing().when(atendimentoService).excluirAtendimentoPorId(id);

        ResponseEntity<String> response = controller.excluirAtendimento(id);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testExcluirAtendimento_ErroUnprocessable() {
        doThrow(new UnprocessableEntityException("erro")).when(atendimentoService).excluirAtendimentoPorId(id);

        ResponseEntity<String> response = controller.excluirAtendimento(id);

        assertEquals(404, response.getStatusCode().value());
        // 👇 Corrigido: ponto final
        assertEquals("Atendimento não encontrado.", response.getBody());
    }
}
