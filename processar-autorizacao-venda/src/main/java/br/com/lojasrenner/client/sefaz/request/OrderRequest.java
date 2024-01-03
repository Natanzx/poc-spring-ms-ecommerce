package br.com.lojasrenner.client.sefaz.request;

import java.util.List;

public record OrderRequest(
        String orderNumber,
        String externalOrderNumber,
        CustomerRequest customer,
        List<ProductRequest> products
) {}
