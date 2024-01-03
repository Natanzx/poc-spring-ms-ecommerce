package br.com.lojasrenner.sefaz.domain.request;

import java.math.BigDecimal;

public record ProductRequest(
        long sku,
        int amount,
        BigDecimal value,
        double icmsValue,
        double pisValue,
        double difaulValue,
        double fcpIcmsValue
) {}
