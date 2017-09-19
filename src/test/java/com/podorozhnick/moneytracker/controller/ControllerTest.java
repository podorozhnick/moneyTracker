package com.podorozhnick.moneytracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.podorozhnick.moneytracker.AbstractTest;
import com.podorozhnick.moneytracker.util.JsonUtils;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public abstract class ControllerTest extends AbstractTest {

    static MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    static {
        mappingJackson2HttpMessageConverter = new  MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
    }

}
