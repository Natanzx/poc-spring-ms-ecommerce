package br.com.lojasrenner.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
public class CacheConfig implements CachingConfigurer {

    public static final String TRIBUTARIO = "tributario";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(TRIBUTARIO);
    }

    @CacheEvict(allEntries = true, value = { TRIBUTARIO })
    @Scheduled(fixedDelay = 5 * 60 * 1000,  initialDelay = 500)
    public void reportCacheEvict() {
        log.info("limpando cache api tributaria, {}", LocalDateTime.now());
    }
}
