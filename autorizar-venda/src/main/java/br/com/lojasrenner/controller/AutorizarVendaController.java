package br.com.lojasrenner.controller;

import br.com.lojasrenner.domain.request.AutorizarVendaRequest;
import br.com.lojasrenner.domain.response.AutorizarVendaResponse;
import br.com.lojasrenner.service.KafkaProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/autorizar-venda")
@RequiredArgsConstructor
public class AutorizarVendaController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<AutorizarVendaResponse> create(@Valid @RequestBody AutorizarVendaRequest request) {

        kafkaProducerService.send(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new AutorizarVendaResponse("EM_PROCESSAMENTO", LocalDateTime.now()));
    }

}
