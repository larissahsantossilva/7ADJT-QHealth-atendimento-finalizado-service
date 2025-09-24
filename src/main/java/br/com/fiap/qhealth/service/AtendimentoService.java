package br.com.fiap.qhealth.service;

import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import br.com.fiap.qhealth.model.Atendimento;
import br.com.fiap.qhealth.repository.AtendimentoRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.fiap.qhealth.utils.QHealthConstants.*;
import static br.com.fiap.qhealth.utils.QHealthUtils.uuidValidator;
import static java.time.LocalDateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.domain.PageRequest.of;

@Service
@AllArgsConstructor
public class AtendimentoService {

    private static final Logger logger = getLogger(AtendimentoService.class);

    private final AtendimentoRepository atendimentoRepository;

    public Page<Atendimento> listarAtendimentos(int page, int size) {
        return atendimentoRepository.findAll(of(page, size));
    }

    public Atendimento buscarAtendimentoPorId(UUID id) {
        uuidValidator(id);
        return atendimentoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ID_NAO_ENCONTRADO));
    }

    public Atendimento criarAtendimento(Atendimento atendimento) {
        try {
            atendimento.setDataCriacao(now());
            atendimento.setDataUltimaAlteracao(now());
            return atendimentoRepository.save(atendimento);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_CRIAR_ATENDIMENTO, e);
            throw new UnprocessableEntityException(ERRO_AO_CRIAR_ATENDIMENTO);
        }
    }

    public void atualizarAtendimentoExistente(Atendimento atendimento, UUID id) {
        Atendimento atendimentoExistente = atendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ATENDIMENTO_NAO_ENCONTRADO));
        atualizarCampos(atendimento, atendimentoExistente);
        try {
            atendimentoRepository.save(atendimentoExistente);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_ALTERAR_ATENDIMENTO, e);
            throw new UnprocessableEntityException(ERRO_AO_ALTERAR_ATENDIMENTO);
        }
    }

    public void excluirAtendimentoPorId(UUID id) {
        uuidValidator(id);
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ATENDIMENTO_NAO_ENCONTRADO));
        UUID atendimentoId = atendimento.getId();
        try {
            atendimentoRepository.deleteById(atendimentoId);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_DELETAR_ATENDIMENTO, e);
            throw new UnprocessableEntityException(ERRO_AO_DELETAR_ATENDIMENTO);
        }
    }

    private static void atualizarCampos(Atendimento novo, Atendimento existente) {
        if (novo != null) {
            if (novo.getCpf() != null) existente.setCpf(novo.getCpf());
            if (novo.getStatus() != null) existente.setStatus(novo.getStatus());
            if (novo.getFilaId() != null) existente.setFilaId(novo.getFilaId());
            if (novo.getPosicaoFila() != null) existente.setPosicaoFila(novo.getPosicaoFila());
            existente.setDataUltimaAlteracao(now());
        }
    }
}
