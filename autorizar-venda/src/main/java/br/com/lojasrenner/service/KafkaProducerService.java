package br.com.lojasrenner.service;

import br.com.lojasrenner.domain.request.AutorizarVendaRequest;
import br.com.lojasrenner.exception.core.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value("${app.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, AutorizarVendaRequest> kafkaTemplate;

    public void send(AutorizarVendaRequest autorizarVendaRequest) {
        try {
            UUID uuid = UUID.randomUUID();
            Message<AutorizarVendaRequest> message = createMessageWithHeaders(uuid, autorizarVendaRequest);

            this.kafkaTemplate.send(message).whenComplete((result, ex) -> {
                if (Objects.nonNull(ex)) {
                    log.error("Failed to send event {}, with messageID {}", ex.getCause(), uuid);
                    throw new RuntimeException(ex);
                }

                log.info("Success to send event {}, with messageID {}", result.getRecordMetadata().toString(), uuid);
            });

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new BusinessException(ex.getLocalizedMessage(), ex);
        }
    }

    private Message<AutorizarVendaRequest> createMessageWithHeaders(UUID uuid, AutorizarVendaRequest autorizarVendaRequest) {
        return MessageBuilder.withPayload(autorizarVendaRequest)
                .setHeader("hash", autorizarVendaRequest.hashCode())
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(1L))
                .setHeader("type", "fct")
                .setHeader("cid", uuid.toString())
                .setHeader(KafkaHeaders.TOPIC, this.topic)
                .setHeader(KafkaHeaders.KEY, uuid.toString())
                .build();
    }

}
