package com.podorozhnick.moneytracker.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

import java.util.Locale;

@Configuration
@Import({ JettyConfig.class, HibernateConfiguration.class, WebSecurityConfig.class })
@ComponentScan(basePackages = "com.podorozhnick.moneytracker",
        excludeFilters = { @ComponentScan.Filter(Configuration.class), @ComponentScan.Filter(Controller.class) })
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.ENGLISH);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

}
