package br.com.lojasrenner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.lojasrenner.domain.request.AutorizarVendaRequest;
import br.com.lojasrenner.domain.request.ClienteRequest;
import br.com.lojasrenner.domain.request.ItemRequest;
import br.com.lojasrenner.domain.request.OrdemPedidoRequest;
import br.com.lojasrenner.service.KafkaProducerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class AutorizarVendaControllerTest {

    public MockMvc mockMvc;
    public final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private AutorizarVendaController controller;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void init() {
        mockMvc = standaloneSetup(controller).build();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void create() throws Exception {
        var request = new AutorizarVendaRequest(
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
            Collections.singletonList(new ItemRequest("324226428", 3, new BigDecimal("5691")))
        );

        mockMvc.perform(post("/autorizar-venda")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());

        verify(kafkaProducerService).send(any(AutorizarVendaRequest.class));
    }
}