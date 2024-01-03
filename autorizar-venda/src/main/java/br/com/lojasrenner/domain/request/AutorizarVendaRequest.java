package br.com.lojasrenner.domain.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static java.util.Objects.nonNull;

public record AutorizarVendaRequest(
        @NotEmpty String canal,
        @NotEmpty String empresa,
        @NotEmpty String loja,
        @Positive @NotNull Integer pdv,
        @NotNull @Valid OrdemPedidoRequest ordemPedido,
        @NotNull @Valid ClienteRequest cliente,
        @Positive @NotNull BigDecimal totalItens,
        @Positive @NotNull Integer quantidadeItens,
        @NotEmpty @Size(min = 1) @Valid List<ItemRequest> itens) {

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public AutorizarVendaRequest {
        if (nonNull(totalItens) && totalItens.intValue() > 0) {
            totalItens = totalItens.divide(ONE_HUNDRED, MathContext.DECIMAL64);
        }
    }
}