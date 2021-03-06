package ru.igar15.rest_voting_system.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@EnableCaching
public class SpringCacheConfig {

    @Bean
    public CacheManager ehCacheManager() throws IOException {
        JCacheCacheManager jCacheCacheManager = new JCacheCacheManager();
        JCacheManagerFactoryBean jCacheManagerFactoryBean = new JCacheManagerFactoryBean();
        jCacheManagerFactoryBean.setCacheManagerUri(new ClassPathResource("cache/ehcache.xml").getURI());
        jCacheManagerFactoryBean.afterPropertiesSet();
        jCacheCacheManager.setCacheManager(jCacheManagerFactoryBean.getObject());
        return jCacheCacheManager;
    }
}