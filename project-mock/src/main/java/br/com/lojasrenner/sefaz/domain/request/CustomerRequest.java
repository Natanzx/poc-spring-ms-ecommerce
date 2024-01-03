package br.com.lojasrenner.sefaz.domain.request;

public record CustomerRequest(
        String id,
        String name,
        String document,
        String documentType,
        String personType,
        String address,
        String addressNumber,
        String addressComplement,
        String district,
        String city,
        String state,
        String country,
        String zipCode,
        String ibgeCode,
        String phoneNumber,
        String email
) {}
