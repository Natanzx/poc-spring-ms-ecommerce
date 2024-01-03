package br.com.lojasrenner.service;

import br.com.lojasrenner.client.tributario.APITributario;
import br.com.lojasrenner.client.tributario.response.TributatioResponse;
import br.com.lojasrenner.config.CacheConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TributarioService {

    private final APITributario apiTributario;

    @Cacheable(CacheConfig.TRIBUTARIO)
    public TributatioResponse getBySKU(final String sku) {
        log.info("Consultando api tributaria, sku: {}", sku);

        return apiTributario.getBySKU(sku);
    }
}
