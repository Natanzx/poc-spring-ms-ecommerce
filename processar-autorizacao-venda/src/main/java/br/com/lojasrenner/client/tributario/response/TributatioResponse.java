package br.com.lojasrenner.client.tributario.response;

public record TributatioResponse(
    Integer sku,
    Integer valorIcms,
    Integer valorPis,
    Integer valorDifaul,
    Integer valorFcpIcms
) {

}
