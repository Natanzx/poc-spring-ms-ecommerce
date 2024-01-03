package br.com.lojasrenner.domain.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record AutorizarVendaRequest(
        @NotEmpty String canal,
        @NotEmpty String empresa,
        @NotEmpty String loja,
        @Positive @NotNull Integer pdv,
        @NotNull @Valid OrdemPedidoRequest ordemPedido,
        @NotNull @Valid ClienteRequest cliente,
        @Positive @NotNull Integer totalItens,
        @Positive @NotNull Integer quantidadeItens,
        @NotEmpty @Size(min = 1) @Valid List<ItemRequest> itens) {

    public static final int ONE_HUNDRED = 100;

    public AutorizarVendaRequest {
        if (Objects.nonNull(totalItens) && Objects.nonNull(itens)) {
            totalItens = totalItens * ONE_HUNDRED;

            itens.forEach(itemResquest -> new ItemRequest(
                itemResquest.sku(),
                itemResquest.quantidade(),
                itemResquest.valor().multiply(BigDecimal.valueOf(ONE_HUNDRED))
            ));
        }
    }
}