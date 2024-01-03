package br.com.lojasrenner.client.tributario;

import br.com.lojasrenner.client.tributario.response.TributatioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "API-Tributario", url = "http://localhost:8082")
public interface APITributario {

    @RequestMapping(method = RequestMethod.GET, value = "/tributo")
    TributatioResponse getBySKU(@RequestParam("sku") String sku);
}
