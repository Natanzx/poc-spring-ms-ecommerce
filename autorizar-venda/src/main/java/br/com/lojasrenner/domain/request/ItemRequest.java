package br.com.lojasrenner.domain.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.util.Objects.nonNull;

public record ItemRequest(
        @NotEmpty String sku,
        @Positive Integer quantidade,
        @Positive BigDecimal valor) {

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    public ItemRequest {
        if (nonNull(valor) && valor.intValue() > 0) {
            valor = valor.divide(ONE_HUNDRED, MathContext.DECIMAL64);
        }
    }
}
