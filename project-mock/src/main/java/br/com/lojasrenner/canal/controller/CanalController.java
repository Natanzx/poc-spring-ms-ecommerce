package br.com.lojasrenner.canal.controller;

import br.com.lojasrenner.canal.domain.request.PedidoNfeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("/callback-venda")
@RequiredArgsConstructor
public class CanalController {

    @PostMapping
    public ResponseEntity<String> callbackVenda(@RequestBody PedidoNfeRequest request) {
        return ResponseEntity.ok(format("Venda {0} recebida com sucesso", request.numeroOrdemExterno()));
    }
}
