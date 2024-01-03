package br.com.lojasrenner.client.sefaz;


import br.com.lojasrenner.client.sefaz.request.OrderRequest;
import br.com.lojasrenner.client.sefaz.response.NFEResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "API-SEFAZ", url = "http://localhost:8082")
public interface APISefaz {

    @PostMapping("/authorize")
    NFEResponse authorize(@RequestBody OrderRequest order);
}
