package br.com.lojasrenner.service;

import br.com.lojasrenner.client.canal.controller.APICanal;
import br.com.lojasrenner.client.canal.request.PedidoNfeRequest;
import br.com.lojasrenner.client.sefaz.APISefaz;
import br.com.lojasrenner.client.sefaz.request.CustomerRequest;
import br.com.lojasrenner.client.sefaz.request.OrderRequest;
import br.com.lojasrenner.client.sefaz.request.ProductRequest;
import br.com.lojasrenner.client.sefaz.response.NFEResponse;
import br.com.lojasrenner.client.tributario.response.TributatioResponse;
import br.com.lojasrenner.domain.entity.VendaEntity;
import br.com.lojasrenner.domain.request.AutorizarVendaRequest;
import br.com.lojasrenner.domain.request.ClienteRequest;
import br.com.lojasrenner.domain.request.ItemRequest;
import br.com.lojasrenner.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static br.com.lojasrenner.domain.type.SituacaoType.FALHA_AO_PROCESSAR;
import static br.com.lojasrenner.domain.type.SituacaoType.PROCESSADO;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final APISefaz apiSefaz;
    private final APICanal apiCanal;
    private final TributarioService tributarioService;
    private final VendaRepository repository;

    public void generateOrder(AutorizarVendaRequest request) {
        log.info("Iniciando o processamento da venda");
        try {
            OrderRequest orderRequest = mapperToOrderRequest(request);

            NFEResponse nfeResponse = consultarApiSefaz(orderRequest);

            PedidoNfeRequest pedidoNfeRequest = new PedidoNfeRequest(orderRequest.orderNumber(),
                orderRequest.externalOrderNumber(),  nfeResponse.nfeKey(), nfeResponse.invoiceNumber(),
                nfeResponse.issuanceDate(), nfeResponse.invoice(), "PROCESSADO");

            consultandoApiCanal(pedidoNfeRequest);

            VendaEntity vendaEntity = convertEventToEntity(request);
            vendaEntity.setChaveNfe(nfeResponse.nfeKey());
            vendaEntity.setNumeroNota(Long.parseLong(nfeResponse.invoiceNumber()));
            vendaEntity.setDataEmissao(nfeResponse.issuanceDate());
            vendaEntity.setPdf(nfeResponse.invoice());
            vendaEntity.setSituacao(PROCESSADO);

            repository.save(vendaEntity);
            log.info("Processamento da venda realizada com sucesso");
        } catch (Exception e) {
            log.info("Falha no processamento da venda, gravando os dados no banco...");
            VendaEntity vendaEntity = convertEventToEntity(request);
            vendaEntity.setSituacao(FALHA_AO_PROCESSAR);
            repository.save(vendaEntity);
        }
    }

    private OrderRequest mapperToOrderRequest(AutorizarVendaRequest payload) {
        List<ProductRequest> productRequests = new ArrayList<>();

        for (ItemRequest item : payload.itens()) {
            TributatioResponse tributatioResponse = tributarioService.getBySKU(item.sku());

            productRequests.add(new ProductRequest(tributatioResponse.sku(), item.quantidade(), item.valor(),
                tributatioResponse.valorIcms(), tributatioResponse.valorPis(), tributatioResponse.valorDifaul(),
                tributatioResponse.valorFcpIcms()));
        }

        ClienteRequest cliente = payload.cliente();
        CustomerRequest customerRequest = new CustomerRequest(cliente.id(), cliente.nome(), cliente.documento(),
            cliente.tipoDocumento(), cliente.tipoPessoa(), cliente.endereco(), cliente.numeroEndereco().toString(),
            cliente.complementoEndereco(), cliente.pais(), cliente.cidade(), cliente.estado(), cliente.bairro(),
            cliente.cep(), cliente.codigoIbge(), cliente.telefone(), cliente.email());

        return new OrderRequest(payload.ordemPedido().numeroPedido(), payload.ordemPedido().numeroOrdemExterno(),
            customerRequest, productRequests);
    }

    private VendaEntity convertEventToEntity(AutorizarVendaRequest payload) {
        return VendaEntity
            .builder()
            .canal(payload.canal())
            .codigoEmpresa(Integer.parseInt(payload.empresa()))
            .codigoLoja(Integer.parseInt(payload.loja()))
            .numeroPdv(payload.pdv())
            .numeroPedido(payload.ordemPedido().numeroPedido())
            .numeroOrdemExterno(payload.ordemPedido().numeroOrdemExterno())
            .valorTotal(Double.valueOf(payload.totalItens()))
            .qtdItem(payload.itens().stream().mapToInt(ItemRequest::quantidade).sum())
            .vendaRequest(UUID.randomUUID().toString())
            .dataAtualizacao(LocalDateTime.now())
            .dataRequisicao(payload.ordemPedido().dataAutorizacao())
            .build();
    }

    private void consultandoApiCanal(final PedidoNfeRequest pedidoNfeRequest) {
        log.info("Consultando api canal");
        apiCanal.callbackvenda(pedidoNfeRequest);
    }

    private NFEResponse consultarApiSefaz(final OrderRequest orderRequest) {
        log.info("Consultando api SEFAZ");
        return apiSefaz.authorize(orderRequest);
    }
}
