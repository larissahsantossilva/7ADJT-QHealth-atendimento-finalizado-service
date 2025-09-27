package br.com.fiap.qhealth.controller;

import br.com.fiap.qhealth.dto.request.AtendimentoBodyRequest;
import br.com.fiap.qhealth.dto.request.AtendimentoAtualizarBodyRequest;
import br.com.fiap.qhealth.dto.response.AtendimentoBodyResponse;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import br.com.fiap.qhealth.model.Atendimento;
import br.com.fiap.qhealth.service.AtendimentoService;
import br.com.fiap.qhealth.utils.QHealthUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.qhealth.utils.QHealthConstants.*;
import static br.com.fiap.qhealth.utils.QHealthUtils.convertToAtendimento;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.*;

@RestController
@AllArgsConstructor
@RequestMapping(V1_ATENDIMENTO_FINALIZADO)
public class AtendimentoController {

    private static final Logger logger = getLogger(AtendimentoController.class);

    private final AtendimentoService atendimentoService;

    @Operation(
        description = "Lista todos os atendimentos de forma paginada.",
        summary = "Lista atendimentos paginados.",
        responses = {
            @ApiResponse(
                description = OK,
                responseCode = HTTP_STATUS_CODE_200,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Atendimento.class))
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<AtendimentoBodyResponse>> listarAtendimentos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        logger.info("GET | {} | Iniciado listarAtendimentos", V1_ATENDIMENTO_FINALIZADO);
        Page<Atendimento> atendimentos = atendimentoService.listarAtendimentos(page, size);

        List<AtendimentoBodyResponse> lista = atendimentos.stream()
                .map(a -> QHealthUtils.convertToAtendimento(
                        a,
                        atendimentoService.calcularPosicaoFila(a.getId(), a.getFilaId())
                ))
                .toList();

        logger.info("GET | {} | Finalizado listarAtendimentos", V1_ATENDIMENTO_FINALIZADO);
        return ok(lista);
    }

    @Operation(
        description = "Busca atendimento por id.",
        summary = "Busca atendimento por id.",
        responses = {
            @ApiResponse(
                description = OK,
                responseCode = HTTP_STATUS_CODE_200,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Atendimento.class))
            ),
            @ApiResponse(
                description = NOT_FOUND,
                responseCode = HTTP_STATUS_CODE_404,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoBodyResponse> buscarAtendimentoPorId(@PathVariable("id") UUID id) {
        logger.info("GET | {} | Iniciado buscarAtendimentoPorId | id: {}", V1_ATENDIMENTO_FINALIZADO, id);
        var atendimento = atendimentoService.buscarAtendimentoPorId(id);
        if (atendimento != null) {
            int posicao = atendimentoService.calcularPosicaoFila(atendimento.getId(), atendimento.getFilaId());
            logger.info("GET | {} | Finalizado buscarAtendimentoPorId | id: {}", V1_ATENDIMENTO_FINALIZADO, id);
            return ok(QHealthUtils.convertToAtendimento(atendimento, posicao));
        }
        logger.info("GET | {} | Atendimento não encontrado | id: {}", V1_ATENDIMENTO_FINALIZADO, id);
        return status(404).build();
    }

    @Operation(
        description = "Cria atendimento.",
        summary = "Cria atendimento.",
        responses = {
            @ApiResponse(
                description = ATENDIMENTO_CRIADO_COM_SUCESSO,
                responseCode = HTTP_STATUS_CODE_201,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                description = ERRO_AO_CRIAR_ATENDIMENTO,
                responseCode = HTTP_STATUS_CODE_422,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            )
        }
    )
    @PostMapping
    public ResponseEntity<UUID> criarAtendimento(@Valid @RequestBody AtendimentoBodyRequest atendimentoBodyRequest) {
        logger.info("POST | {} | Iniciado criarAtendimento", V1_ATENDIMENTO_FINALIZADO);
        Atendimento atendimento = atendimentoService.criarAtendimento(convertToAtendimento(atendimentoBodyRequest), false);
        logger.info("POST | {} | Finalizado criarAtendimento", V1_ATENDIMENTO_FINALIZADO);
        return status(201).body(atendimento.getId());
    }

    @Operation(
        description = "Atualiza atendimento por id.",
        summary = "Atualiza atendimento por id.",
        responses = {
            @ApiResponse(
                description = OK,
                responseCode = HTTP_STATUS_CODE_200,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            ),
            @ApiResponse(
                description = ATENDIMENTO_NAO_ENCONTRADO,
                responseCode = HTTP_STATUS_CODE_404,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                description = ERRO_AO_ALTERAR_ATENDIMENTO,
                responseCode = HTTP_STATUS_CODE_422,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAtendimento(@PathVariable("id") UUID id,
                                                       @Valid @RequestBody AtendimentoAtualizarBodyRequest atendimentoAtualizarBodyRequest) {
        logger.info("PUT | {} | Iniciado atualizarAtendimento | id: {}", V1_ATENDIMENTO_FINALIZADO, id);
        atendimentoService.atualizarAtendimentoExistente(convertToAtendimento(atendimentoAtualizarBodyRequest), id);
        logger.info("PUT | {} | Finalizado atualizarAtendimento", V1_ATENDIMENTO_FINALIZADO);
        return ok("Atendimento atualizado com sucesso");
    }

    @Operation(
        description = "Exclui atendimento por id.",
        summary = "Exclui atendimento por id.",
        responses = {
            @ApiResponse(
                description = NO_CONTENT,
                responseCode = HTTP_STATUS_CODE_204,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            ),
            @ApiResponse(
                description = ATENDIMENTO_NAO_ENCONTRADO,
                responseCode = HTTP_STATUS_CODE_404,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirAtendimento(@PathVariable("id") UUID id) {
        logger.info("DELETE | {} | Iniciado excluirAtendimento | id: {}", V1_ATENDIMENTO_FINALIZADO, id);
        try {
            atendimentoService.excluirAtendimentoPorId(id);
            logger.info("DELETE | {} | Atendimento excluído com sucesso | id: {}", V1_ATENDIMENTO_FINALIZADO, id);
            return noContent().build();
        } catch (UnprocessableEntityException e) {
            logger.error("DELETE | {} | Erro ao excluir Atendimento | id: {} | Erro: {}", V1_ATENDIMENTO_FINALIZADO, id, e.getMessage());
            return status(404).body(ATENDIMENTO_NAO_ENCONTRADO);
        }
    }
}
