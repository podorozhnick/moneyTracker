package com.podorozhnick.moneytracker.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    private static Gson gson = new Gson();

    static {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        gson = builder.create();
    }

    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }

    public static  <T> T fromJson(Class<T> clazz, String json) {
        return (T) gson.fromJson(json, clazz);
    }

    public static <T> List<T> getListFromJson(Class<T[]> clazz, String json) {
        return Arrays.asList(gson.fromJson(json, clazz));
    }

}
