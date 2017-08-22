package com.podorozhnick.moneytracker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;

@Configuration
@Import({ JettyConfig.class, HibernateConfiguration.class, WebSecurityConfig.class })
@ComponentScan(basePackages = "com.podorozhnick.moneytracker",
        excludeFilters = { @ComponentScan.Filter(Configuration.class), @ComponentScan.Filter(Controller.class) })
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig {
}
