package br.com.fiap.qhealth.listener;

import br.com.fiap.qhealth.config.RabbitMQConfig;
import br.com.fiap.qhealth.listener.json.AtendimentoUbsRequestJson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AtendimentoUbsListener {

    private static final Logger log = LoggerFactory.getLogger(AtendimentoUbsListener.class);

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_ATENDIMENTO_UBS_1_USUAL,
                              RabbitMQConfig.QUEUE_ATENDIMENTO_UBS_2_PREFERENCIAL,
                              RabbitMQConfig.QUEUE_ATENDIMENTO_UBS_2_USUAL,
                              RabbitMQConfig.QUEUE_ATENDIMENTO_UBS_2_PREFERENCIAL})
    public void escutarMensagem(AtendimentoUbsRequestJson atendimentoAgendadoRequestJson) {
        //log.info(">>> Mensagem recebida da fila [{}]: '{}'", fila, atendimentoAgendadoRequestJson);
        log.info(">>> Mensagem recebida da fila: '{}'", atendimentoAgendadoRequestJson);

        //log.info(">>> Atendimento salvo: {}", atendimento);
    }
}
