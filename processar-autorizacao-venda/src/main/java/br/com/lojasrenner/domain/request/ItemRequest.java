package br.com.lojasrenner.domain.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemRequest(
        @NotEmpty String sku,
        @Positive Integer quantidade,
        @Positive BigDecimal valor) {

}
