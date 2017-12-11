package com.podorozhnick.moneytracker.config;

import com.podorozhnick.moneytracker.settings.DatabaseSettings;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

    @Autowired
    private DatabaseSettings databaseSettings;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.podorozhnick.moneytracker.db.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseSettings.getDriverClassName());
        dataSource.setUrl(databaseSettings.getUrl());
        dataSource.setUsername(databaseSettings.getUsername());
        dataSource.setPassword(databaseSettings.getPassword());
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", databaseSettings.getDialect());
        properties.put("hibernate.show_sql", databaseSettings.isShowSql());
        properties.put("hibernate.format_sql", databaseSettings.isFormatSql());
        properties.put("hibernate.hbm2ddl.auto", databaseSettings.getHbmToDdl());
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
}