package br.com.lojasrenner.domain.response;

import java.time.LocalDateTime;

public record AutorizarVendaResponse(String status, LocalDateTime dataResposta) { }
