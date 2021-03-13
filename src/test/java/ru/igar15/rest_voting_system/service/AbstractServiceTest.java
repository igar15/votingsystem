package ru.igar15.rest_voting_system.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.igar15.rest_voting_system.config.AppConfig;
import ru.igar15.rest_voting_system.util.JpaUtil;

@ContextConfiguration(classes = {AppConfig.class})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setup() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        jpaUtil.clear2ndLevelHibernateCache();
    }
}