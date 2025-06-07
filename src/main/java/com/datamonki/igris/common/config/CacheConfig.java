package com.datamonki.igris.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(
            com.github.benmanes.caffeine.cache.Caffeine.newBuilder()
                .expireAfterWrite(5, java.util.concurrent.TimeUnit.MINUTES)  // Cache expira após 5 minutos
                .recordStats()  // Habilita estatísticas de cache
        );
        return cacheManager;
    }
}
