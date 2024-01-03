package br.com.lojasrenner.client.sefaz.response;

import java.time.LocalDateTime;

public record NFEResponse(
        String nfeKey,
        String invoiceNumber,
        LocalDateTime issuanceDate,
        String invoice
) {}