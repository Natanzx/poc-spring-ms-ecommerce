package br.com.lojasrenner.domain.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record ClienteRequest(
        @NotEmpty String id,
        @NotEmpty String nome,
        @NotEmpty String documento,
        @NotEmpty String tipoDocumento,
        @NotEmpty String tipoPessoa,
        @NotEmpty String endereco,
        @Positive Integer numeroEndereco,
        @NotEmpty String complementoEndereco,
        @NotEmpty String bairro,
        @NotEmpty String cidade,
        @NotEmpty String estado,
        @NotEmpty String pais,
        @NotEmpty String cep,
        @NotEmpty String codigoIbge,
        @NotEmpty String telefone,
        @NotEmpty String email) {

}