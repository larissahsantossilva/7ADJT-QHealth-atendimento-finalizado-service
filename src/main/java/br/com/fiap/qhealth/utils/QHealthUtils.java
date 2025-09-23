package br.com.fiap.qhealth.utils;

import br.com.fiap.qhealth.dto.request.AtendimentoAtualizarBodyRequest;
import br.com.fiap.qhealth.dto.request.AtendimentoBodyRequest;
import br.com.fiap.qhealth.dto.response.AtendimentoBodyResponse;
import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.model.Atendimento;

import java.util.UUID;

import static br.com.fiap.qhealth.utils.QHealthConstants.ID_INVALIDO;
import static java.util.regex.Pattern.matches;

public class QHealthUtils {

    private static final String REGEX_UUID = "^[0-9a-f]{8}-[0-9a-f]{4}-[4][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    public static void uuidValidator(UUID id) {
        if (!matches(REGEX_UUID, id.toString())) {
            throw new ResourceNotFoundException(ID_INVALIDO);
        }
    }

    // ----------- Atendimento -----------

    // Request → Entity
    public static Atendimento convertToAtendimento(AtendimentoBodyRequest dto) {
        return Atendimento.builder()
                .pacienteId(dto.getPacienteId())
                .status(dto.getStatus())
                .filaId(dto.getFilaId())
                .posicaoFila(dto.getPosicaoFila())
                .build();
    }

    // Update Request → Entity
    public static Atendimento convertToAtendimento(AtendimentoAtualizarBodyRequest dto) {
        return Atendimento.builder()
                .pacienteId(dto.getPacienteId())
                .status(dto.getStatus())
                .filaId(dto.getFilaId())
                .posicaoFila(dto.getPosicaoFila())
                .build();
    }

    // Entity → Response
    public static AtendimentoBodyResponse convertToAtendimento(Atendimento entity) {
        return new AtendimentoBodyResponse(
                entity.getId(),
                entity.getPacienteId(),
                entity.getStatus(),
                entity.getFilaId(),
                entity.getPosicaoFila(),
                entity.getDataCriacao(),
                entity.getDataUltimaAlteracao()
        );
    }
}
