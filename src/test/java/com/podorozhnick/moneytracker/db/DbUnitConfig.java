package com.podorozhnick.moneytracker.db;

import com.podorozhnick.moneytracker.config.HibernateConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.podorozhnick.moneytracker.db.dao")
@EnableTransactionManagement
@PropertySource(value = { "classpath:dbunit.properties" })
@Import(HibernateConfiguration.class)
public class DbUnitConfig {
}
