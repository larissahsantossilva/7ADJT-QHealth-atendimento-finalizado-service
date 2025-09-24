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
@Schema(description = "DTO para criar atendimento")
public class AtendimentoBodyRequest {

    @NotNull(message = "Paciente é obrigatório")
    private String cpf;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "Fila é obrigatória")
    private UUID filaId;

    @NotNull(message = "Posição na fila é obrigatória")
    @Positive(message = "A posição deve ser maior que zero")
    private Integer posicaoFila;
}
