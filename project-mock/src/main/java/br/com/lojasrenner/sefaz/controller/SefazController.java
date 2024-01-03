package br.com.lojasrenner.sefaz.controller;

import br.com.lojasrenner.sefaz.domain.request.OrderRequest;
import br.com.lojasrenner.sefaz.domain.response.NFEResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Slf4j
@RestController
@RequestMapping("/authorize")
@RequiredArgsConstructor
public class SefazController {

    @PostMapping
    public ResponseEntity<NFEResponse> authorize(@RequestBody OrderRequest order) {
        return ResponseEntity.ok(
            new NFEResponse(
                randomNumeric(44),
                randomNumeric(7),
                LocalDateTime.now(),
                UUID.randomUUID().toString()
            )
        );
    }
}
