package br.com.fiap.qhealth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para atualizar atendimento")
public class AtendimentoAtualizarBodyRequest {

    private String cpf;

    private String status;

    private UUID filaId;

    @Positive(message = "A posição deve ser maior que zero")
    private Integer posicaoFila;
}
