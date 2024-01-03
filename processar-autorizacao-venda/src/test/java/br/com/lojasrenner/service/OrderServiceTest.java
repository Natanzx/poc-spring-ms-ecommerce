package br.com.lojasrenner.service;

import br.com.lojasrenner.client.canal.controller.APICanal;
import br.com.lojasrenner.client.sefaz.APISefaz;
import br.com.lojasrenner.client.sefaz.response.NFEResponse;
import br.com.lojasrenner.client.tributario.response.TributatioResponse;
import br.com.lojasrenner.domain.entity.VendaEntity;
import br.com.lojasrenner.domain.request.AutorizarVendaRequest;
import br.com.lojasrenner.domain.request.ClienteRequest;
import br.com.lojasrenner.domain.request.ItemRequest;
import br.com.lojasrenner.domain.request.OrdemPedidoRequest;
import br.com.lojasrenner.repository.VendaRepository;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.lojasrenner.domain.type.SituacaoType.FALHA_AO_PROCESSAR;
import static br.com.lojasrenner.domain.type.SituacaoType.PROCESSADO;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private VendaRepository repository;

    @Mock
    private APISefaz apiSefaz;

    @Mock
    private APICanal apiCanal;

    @Mock
    private TributarioService apiTributario;

    @Captor
    private ArgumentCaptor<VendaEntity> vendaEntityArgumentCaptor;

    @Test
    void deveGerarOrdemComSucesso() {
        var request = createRequest();
        var tributarioResponse = new TributatioResponse(324226428, 38, 12, 9, 58);
        var nfeResponse = new NFEResponse(randomNumeric(44), randomNumeric(7), LocalDateTime.now(), UUID.randomUUID().toString());

        when(apiTributario.getBySKU(any())).thenReturn(tributarioResponse);
        when(apiSefaz.authorize(any())).thenReturn(nfeResponse);

        orderService.generateOrder(request);

        verify(apiTributario, times(1)).getBySKU(any());
        verify(apiSefaz, times(1)).authorize(any());
        verify(apiCanal, times(1)).callbackvenda(any());
        verify(repository).save(vendaEntityArgumentCaptor.capture());

        final VendaEntity vendaEntityCaptor = vendaEntityArgumentCaptor.getValue();
        assertEquals(PROCESSADO, vendaEntityCaptor.getSituacao());
    }

    @Test
    void deveGerarOrdemComFalhaNaApiTributaria() {
        var request = createRequest();

        doThrow(FeignException.class).when(apiTributario).getBySKU(any());

        orderService.generateOrder(request);

        verify(apiTributario, times(1)).getBySKU(any());
        verifyNoInteractions(apiSefaz, apiCanal);
        verify(repository).save(vendaEntityArgumentCaptor.capture());

        final VendaEntity vendaEntityCaptor = vendaEntityArgumentCaptor.getValue();
        assertEquals(FALHA_AO_PROCESSAR, vendaEntityCaptor.getSituacao());
    }

    @Test
    void deveGerarOrdemComFalhaNaApiSefaz() {
        var request = createRequest();
        var tributarioResponse = new TributatioResponse(324226428, 38, 12, 9, 58);

        when(apiTributario.getBySKU(any())).thenReturn(tributarioResponse);
        doThrow(FeignException.class).when(apiSefaz).authorize(any());

        orderService.generateOrder(request);

        verify(apiTributario, times(1)).getBySKU(any());
        verify(apiSefaz, times(1)).authorize(any());
        verifyNoInteractions(apiCanal);
        verify(repository).save(vendaEntityArgumentCaptor.capture());

        final VendaEntity vendaEntityCaptor = vendaEntityArgumentCaptor.getValue();
        assertEquals(FALHA_AO_PROCESSAR, vendaEntityCaptor.getSituacao());
    }

    @Test
    void deveGerarOrdemComFalhaNaApiCanal() {
        var request = createRequest();
        var tributarioResponse = new TributatioResponse(324226428, 38, 12, 9, 58);
        var nfeResponse = new NFEResponse(randomNumeric(44), randomNumeric(7), LocalDateTime.now(), UUID.randomUUID().toString());

        when(apiTributario.getBySKU(any())).thenReturn(tributarioResponse);
        when(apiSefaz.authorize(any())).thenReturn(nfeResponse);
        doThrow(FeignException.class).when(apiCanal).callbackvenda(any());

        orderService.generateOrder(request);

        verify(apiTributario, times(1)).getBySKU(any());
        verify(apiSefaz, times(1)).authorize(any());
        verify(apiCanal, times(1)).callbackvenda(any());
        verify(repository).save(vendaEntityArgumentCaptor.capture());

        final VendaEntity vendaEntityCaptor = vendaEntityArgumentCaptor.getValue();
        assertEquals(FALHA_AO_PROCESSAR, vendaEntityCaptor.getSituacao());
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
            38744,
            6,
            singletonList(new ItemRequest("324226428", 3, new BigDecimal("5691")))
        );
    }
}