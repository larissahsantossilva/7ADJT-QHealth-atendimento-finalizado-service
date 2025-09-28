package br.com.fiap.qhealth.service;

import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import br.com.fiap.qhealth.model.Atendimento;
import br.com.fiap.qhealth.repository.AtendimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AtendimentoServiceTest {

    @Mock
    private AtendimentoRepository repository;

    @InjectMocks
    private AtendimentoService service;

    private UUID id;
    private Atendimento atendimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        id = UUID.randomUUID();
        atendimento = Atendimento.builder()
                .id(id)
                .cpf("12345678900")
                .status("AGUARDANDO")
                .filaId(UUID.randomUUID())
                .build();
    }

    // ----------- CRIAR -----------

    @Test
    void testCriarAtendimento_OrigemApi() {
        when(repository.saveAndFlush(any(Atendimento.class))).thenReturn(atendimento);

        Atendimento result = service.criarAtendimento(atendimento, false);

        assertNotNull(result);
        assertEquals("12345678900", result.getCpf());
        verify(repository, times(1)).saveAndFlush(any(Atendimento.class));
    }

    @Test
    void testCriarAtendimento_OrigemRabbit() {
        when(repository.saveAndFlush(any(Atendimento.class))).thenReturn(atendimento);

        Atendimento result = service.criarAtendimento(atendimento, true);

        assertNotNull(result);
        verify(repository, times(1)).saveAndFlush(any(Atendimento.class));
    }

    @Test
    void testCriarAtendimento_DataAccessException() {
        when(repository.saveAndFlush(any(Atendimento.class))).thenThrow(mock(DataAccessException.class));

        assertThrows(UnprocessableEntityException.class, () -> service.criarAtendimento(atendimento, false));
    }

    // ----------- BUSCAR -----------

    @Test
    void testBuscarAtendimentoPorId_Sucesso() {
        when(repository.findById(id)).thenReturn(Optional.of(atendimento));

        Atendimento result = service.buscarAtendimentoPorId(id);

        assertNotNull(result);
        assertEquals("12345678900", result.getCpf());
    }

    @Test
    void testBuscarAtendimentoPorId_NaoEncontrado() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarAtendimentoPorId(id));
    }

    @Test
    void testListarAtendimentos() {
        when(repository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(atendimento)));

        Page<Atendimento> page = service.listarAtendimentos(0, 10);

        assertNotNull(page);
        assertEquals(1, page.getTotalElements());
    }

    // ----------- EXCLUIR -----------

    @Test
    void testExcluirAtendimentoPorId_Sucesso() {
        when(repository.findById(id)).thenReturn(Optional.of(atendimento));

        service.excluirAtendimentoPorId(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testExcluirAtendimentoPorId_NaoEncontrado() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.excluirAtendimentoPorId(id));
    }

    // ----------- ATUALIZAR -----------

    @Test
    void testAtualizarAtendimentoExistente_Sucesso() {
        Atendimento atualizado = Atendimento.builder()
                .cpf("99999999999")
                .status("FINALIZADO")
                .filaId(UUID.randomUUID())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(atendimento));

        service.atualizarAtendimentoExistente(atualizado, id);

        verify(repository, times(1)).saveAndFlush(any(Atendimento.class));
        assertEquals("99999999999", atendimento.getCpf());
        assertEquals("FINALIZADO", atendimento.getStatus());
    }

    @Test
    void testAtualizarAtendimentoExistente_NaoEncontrado() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizarAtendimentoExistente(atendimento, id));
    }

    // ----------- CALCULAR POSIÇÃO FILA -----------

    @Test
    void testCalcularPosicaoFila() {
        UUID filaId = UUID.randomUUID();
        Atendimento a1 = Atendimento.builder()
                .id(UUID.randomUUID())
                .filaId(filaId)
                .status("AGUARDANDO")
                .build();

        Atendimento a2 = Atendimento.builder()
                .id(id)
                .filaId(filaId)
                .status("EM_ATENDIMENTO")
                .build();

        when(repository.findByFilaIdAndStatusNotOrderByDataCriacaoAsc(filaId, "FINALIZADO"))
                .thenReturn(List.of(a1, a2));

        int posicao = service.calcularPosicaoFila(id, filaId);

        assertEquals(2, posicao);
    }

    @Test
    void testCalcularPosicaoFila_NaoEncontrado() {
        UUID filaId = UUID.randomUUID();
        Atendimento a1 = Atendimento.builder()
                .id(UUID.randomUUID())
                .filaId(filaId)
                .status("AGUARDANDO")
                .build();

        when(repository.findByFilaIdAndStatusNotOrderByDataCriacaoAsc(filaId, "FINALIZADO"))
                .thenReturn(List.of(a1));

        int posicao = service.calcularPosicaoFila(id, filaId);

        assertEquals(-1, posicao);
    }

    @Test
    void testCalcularPosicaoFila_AtendimentoFinalizadoNaoConta() {
        UUID filaId = UUID.randomUUID();
        Atendimento finalizado = Atendimento.builder()
                .id(id)
                .filaId(filaId)
                .status("FINALIZADO")
                .build();

        // Simula que o repo ignora finalizados
        when(repository.findByFilaIdAndStatusNotOrderByDataCriacaoAsc(filaId, "FINALIZADO"))
                .thenReturn(List.of());

        int posicao = service.calcularPosicaoFila(id, filaId);

        assertEquals(-1, posicao); // finalizado não entra na fila
    }
}
