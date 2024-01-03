package br.com.lojasrenner.client.canal.controller;


import br.com.lojasrenner.client.canal.request.PedidoNfeRequest;
import br.com.lojasrenner.client.sefaz.request.OrderRequest;
import br.com.lojasrenner.client.sefaz.response.NFEResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "API-Canal", url = "http://localhost:8082")
public interface APICanal {

    @PostMapping("/callback-venda")
    public String callbackvenda(@RequestBody PedidoNfeRequest request);
}
