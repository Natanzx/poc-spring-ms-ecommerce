package br.com.lojasrenner.service;

import br.com.lojasrenner.client.tributario.APITributario;
import br.com.lojasrenner.client.tributario.response.TributatioResponse;
import br.com.lojasrenner.config.CacheConfig;
import feign.FeignException;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TributarioService {

    private final APITributario apiTributario;

    @Retryable(retryFor = { FeignException.GatewayTimeout.class, FeignException.InternalServerError.class, FeignException.TooManyRequests.class })
    @Cacheable(CacheConfig.TRIBUTARIO)
    public TributatioResponse getBySKU(final String sku) {
        log.info("Consultando api tributaria, sku: {}", sku);

        return apiTributario.getBySKU(sku);
    }
}
