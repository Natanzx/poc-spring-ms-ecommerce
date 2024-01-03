package br.com.lojasrenner.kafka;

import br.com.lojasrenner.domain.request.AutorizarVendaRequest;
import br.com.lojasrenner.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final CountDownLatch latch = new CountDownLatch(1);

    private final OrderService orderService;

    @KafkaListener(id = "main-kafka-listener", topics = "${app.kafka.topic}")
    public void consumer(@Payload ConsumerRecord<String, AutorizarVendaRequest> consumerRecord, Acknowledgment ack) {
        log.info("process record {}", consumerRecord.value());

        log.info("key: " + consumerRecord.key());
        log.info("Headers: " + consumerRecord.headers());
        log.info("topic: " + consumerRecord.topic());
        log.info("Partion: " + consumerRecord.partition());
        log.info("Person: " + consumerRecord.value());

        AutorizarVendaRequest payload = consumerRecord.value();
        latch.countDown();

        orderService.generateOrder(payload);

        ack.acknowledge();
    }
}
