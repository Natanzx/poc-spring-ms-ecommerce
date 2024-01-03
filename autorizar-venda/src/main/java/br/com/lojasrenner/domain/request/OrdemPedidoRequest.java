package br.com.lojasrenner.domain.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OrdemPedidoRequest(
        @NotEmpty String numeroPedido,
        @NotEmpty String numeroOrdemExterno,
        @NotNull LocalDateTime dataAutorizacao) {

}
