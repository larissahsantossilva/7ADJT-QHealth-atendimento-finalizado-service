package br.com.fiap.qhealth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para retorno de atendimento")
public class AtendimentoBodyResponse {

    private UUID id;

    private UUID pacienteId;

    private String status;

    private UUID filaId;

    private Integer posicaoFila;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataUltimaAlteracao;
}
