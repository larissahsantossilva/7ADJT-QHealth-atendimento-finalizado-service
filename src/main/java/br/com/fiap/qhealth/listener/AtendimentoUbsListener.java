package br.com.fiap.qhealth.listener;

import br.com.fiap.qhealth.config.RabbitMQConfig;
import br.com.fiap.qhealth.listener.json.AtendimentoUbsRequestJson;
import br.com.fiap.qhealth.model.Atendimento;
import br.com.fiap.qhealth.service.AtendimentoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AtendimentoUbsListener {

    private static final Logger log = LoggerFactory.getLogger(AtendimentoUbsListener.class);

    private final AtendimentoService atendimentoService;

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_ATENDIMENTO_UBS_1_USUAL,
                              RabbitMQConfig.QUEUE_ATENDIMENTO_UBS_1_PREFERENCIAL,
                              RabbitMQConfig.QUEUE_ATENDIMENTO_UBS_2_USUAL,
                              RabbitMQConfig.QUEUE_ATENDIMENTO_UBS_2_PREFERENCIAL})
    public void escutarMensagem(AtendimentoUbsRequestJson atendimentoAgendadoRequestJson) {
        //log.info(">>> Mensagem recebida da fila [{}]: '{}'", fila, atendimentoAgendadoRequestJson);
        log.info(">>> Mensagem recebida da fila: '{}'", atendimentoAgendadoRequestJson);

        Atendimento atendimento = Atendimento.builder()
                .id(atendimentoAgendadoRequestJson.id())
                .cpf(atendimentoAgendadoRequestJson.cpf())
                .status("AGUARDANDO")
                .filaId(atendimentoAgendadoRequestJson.filaId())
                .dataCriacao(atendimentoAgendadoRequestJson.dataCriacao())
                .dataUltimaAlteracao(atendimentoAgendadoRequestJson.dataUltimaAlteracao())
                .build();

        atendimentoService.criarAtendimento(atendimento, true);

        log.info(">>> Atendimento salvo: {}", atendimento);
    }
}
