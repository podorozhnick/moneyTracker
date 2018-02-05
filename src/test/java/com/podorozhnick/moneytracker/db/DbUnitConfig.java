package com.podorozhnick.moneytracker.db;

import com.podorozhnick.moneytracker.config.HibernateConfiguration;
import com.podorozhnick.moneytracker.settings.DatabaseSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.podorozhnick.moneytracker.db.dao")
@EnableTransactionManagement
@PropertySource(value = { "classpath:test.application.properties" })
@Import(HibernateConfiguration.class)
public class DbUnitConfig {

    @Bean
    public DatabaseSettings databaseSettings() {
        return new DatabaseSettings();
    }

}
