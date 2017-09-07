package com.podorozhnick.moneytracker.config;

import com.podorozhnick.moneytracker.util.JsonUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan(useDefaultFilters = false, basePackages = { "com.podorozhnick.moneytracker" },
        includeFilters = { @ComponentScan.Filter(Controller.class) })
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowCredentials(false).maxAge(3600);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).
                defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(JsonUtils.getObjectMapper());
        return jackson2HttpMessageConverter;
    }

}
