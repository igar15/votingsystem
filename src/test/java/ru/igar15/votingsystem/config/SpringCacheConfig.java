package ru.igar15.votingsystem.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableCaching
public class SpringCacheConfig {

    @Bean
    public CacheManager ehCacheManager() throws IOException {
        return new NoOpCacheManager();
    }
}