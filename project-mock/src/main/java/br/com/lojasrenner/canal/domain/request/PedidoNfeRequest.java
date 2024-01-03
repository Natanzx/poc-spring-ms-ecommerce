package br.com.lojasrenner.canal.domain.request;

import java.time.LocalDateTime;

public record PedidoNfeRequest(
        String numeroPedido,
        String numeroOrdemExterno,
        String chaveNFE,
        String numeroNota,
        LocalDateTime dataEmissao,
        String pdf,
        String status
) {}
