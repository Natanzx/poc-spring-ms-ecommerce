package br.com.lojasrenner.sefaz.domain.request;

import java.util.List;

public record OrderRequest(
        String orderNumber,
        String externalOrderNumber,
        CustomerRequest customer,
        List<ProductRequest> products
) {}
