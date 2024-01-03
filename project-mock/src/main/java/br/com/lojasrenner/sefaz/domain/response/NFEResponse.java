package br.com.lojasrenner.sefaz.domain.response;

import java.time.LocalDateTime;

public record NFEResponse(
        String nfeKey,
        String invoiceNumber,
        LocalDateTime issuanceDate,
        String invoice
) {}