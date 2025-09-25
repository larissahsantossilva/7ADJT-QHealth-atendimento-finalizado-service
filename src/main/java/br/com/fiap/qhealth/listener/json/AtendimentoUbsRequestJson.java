package br.com.fiap.qhealth.listener.json;

import java.time.LocalDateTime;
import java.util.UUID;

public record AtendimentoUbsRequestJson (
    UUID id,
    String cpf,
    UUID filaId,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}
