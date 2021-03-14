package ru.igar15.rest_voting_system.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.igar15.rest_voting_system.config.AppConfig;
import ru.igar15.rest_voting_system.config.BeanPropsOverrideConfig;

@ContextConfiguration(classes = {AppConfig.class, BeanPropsOverrideConfig.class})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {
}