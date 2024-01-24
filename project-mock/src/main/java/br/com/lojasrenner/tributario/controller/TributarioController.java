package br.com.lojasrenner.tributario.controller;

import br.com.lojasrenner.tributario.domain.response.TributatioResponse;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tributo")
@RequiredArgsConstructor
public class TributarioController {

    @GetMapping
    public ResponseEntity<TributatioResponse> buscarPorId(@PathParam("sku") String sku) {
        log.info("Solicitação para a Tributatio com o sku {}", sku);

//        throw new RuntimeException();

        return listMock().stream()
            .filter(p -> p.sku().toString().equals(sku))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    public List<TributatioResponse> listMock() {
        List<TributatioResponse> tributatioResponseList = new ArrayList<>();

        tributatioResponseList.add(new TributatioResponse(324226428, 38, 12, 9, 58));
        tributatioResponseList.add(new TributatioResponse(286441499, 29, 53, 90, 49));
        tributatioResponseList.add(new TributatioResponse(183675297, 82, 58, 69, 24));
        return tributatioResponseList;
    }
}
