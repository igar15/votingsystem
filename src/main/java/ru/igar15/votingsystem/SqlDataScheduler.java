package ru.igar15.votingsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Component
public class SqlDataScheduler {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Caching(evict = {
            @CacheEvict(value = "restaurants", allEntries = true),
            @CacheEvict(value = "menusToday",  allEntries = true)
    })
    @Scheduled(cron = "0 0 0 * * *")
    public void  runSqlScript() {
        entityManager.getEntityManagerFactory().getCache().evictAll();
        ResourceDatabasePopulator resourceDatabasePopulator =
                new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("db/extra_data.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }
}