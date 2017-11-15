package com.podorozhnick.moneytracker.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JsonUtils {

    private static ObjectMapper objectMapper = getMapper();

    private static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule m = new SimpleModule();
        m.addSerializer(long.class, new ToStringSerializer());
        m.addSerializer(Long.class, new ToStringSerializer());
        objectMapper.registerModule(m);
        objectMapper.registerModule(new Hibernate5Module());
        return objectMapper;
    }

    public static <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Cannot get json from the object {}", object);
        }
        return "";
    }

    public static <T> T fromJson(Class<T> clazz, String json) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Cannot object {} from a json {}", clazz, json);
        }
        return null;
    }

    public static <T> T fromJsonFile(Class<T> clazz, File file) {
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            log.error("Error in reading config file");
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> getListFromJson(Class<T[]> clazz, String json) {
        try {
            return objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            log.error("Cannot object {} from a json {}", clazz, json);
        }
        return Collections.emptyList();
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
