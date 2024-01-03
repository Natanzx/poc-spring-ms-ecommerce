package br.com.lojasrenner.service;

import br.com.lojasrenner.domain.request.AutorizarVendaRequest;
import br.com.lojasrenner.domain.request.ClienteRequest;
import br.com.lojasrenner.domain.request.ItemRequest;
import br.com.lojasrenner.domain.request.OrdemPedidoRequest;
import br.com.lojasrenner.exception.core.BusinessException;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerServiceTest {

    @InjectMocks
    private KafkaProducerService service;

    @Mock
    private KafkaTemplate<String, AutorizarVendaRequest> kafkaTemplate;

    @BeforeEach
    void init() {
        setField(service, "topic", "teste");
    }

    @Captor
    private ArgumentCaptor<Message<AutorizarVendaRequest>> captor;

    @Test
    void deveEnviarMensagemComSucesso() {
        var request = createRequest();

        final SendResult<String, AutorizarVendaRequest> mockSendResult = mock(SendResult.class);
        final RecordMetadata metadata = new RecordMetadata(
            new TopicPartition("test", 1), 0, 0, 0, 0, 0);
        final CompletableFuture<SendResult<String, AutorizarVendaRequest>> mockFuture = new CompletableFuture<>();
        mockFuture.complete(mockSendResult);

        when(mockSendResult.getRecordMetadata()).thenReturn(metadata);
        when(kafkaTemplate.send(any(Message.class))).thenReturn(mockFuture);

        service.send(request);

        verify(kafkaTemplate).send(captor.capture());

        final AutorizarVendaRequest payload = captor.getValue().getPayload();
        assertEquals(request.hashCode(), payload.hashCode());
    }

    @Test
    void deveRetornarExcessaoNoRetornoDaMensagem() {
        var request = createRequest();

        final CompletableFuture<SendResult<String, AutorizarVendaRequest>> mockFuture = new CompletableFuture<>();
        mockFuture.completeExceptionally(new RuntimeException());

        when(kafkaTemplate.send(any(Message.class))).thenReturn(mockFuture);

        service.send(request);

        verify(kafkaTemplate).send(any(Message.class));
    }

    @Test
    void deveFalharAoEnviarMensagem() {
        var request = createRequest();

        final CompletableFuture<SendResult<String, AutorizarVendaRequest>> mockFuture = new CompletableFuture<>();
        mockFuture.completeExceptionally(new RuntimeException());

        doThrow(RuntimeException.class).when(kafkaTemplate).send(any(Message.class));

        assertThrows(BusinessException.class, () -> service.send(request));

        verify(kafkaTemplate).send(any(Message.class));
    }

    private AutorizarVendaRequest createRequest() {
        return new AutorizarVendaRequest(
            "APP",
            "00001",
            "0001",
            501,
            new OrdemPedidoRequest(
                "101628208632",
                "100423672693-1",
                LocalDateTime.now()
            ),
            new ClienteRequest(
                "123456",
                "Givaldo Santos Vasconcelos",
                "70420816097",
                "CPF",
                "F",
                "Travessa Francisco Vieira",
                11,
                "Apto 405",
                "Trapiche da Barra",
                "Maceió",
                "AL",
                "BR",
                "57010460",
                "7162435",
                "(82) 36774-7713",
                "givaldo.santos.vasconcelos@gmail.com"
            ),
            new BigDecimal("38744"),
            6,
            singletonList(new ItemRequest("324226428", 3, new BigDecimal("5691")))
        );
    }
}