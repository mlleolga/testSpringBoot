package com.testspringboot.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MapperJsonUtil {

    private  static ObjectMapper mapper = new ObjectMapper();

    public static  <T> String parseToJson(T t){
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  <T> T parseFromJson(String json, Class<T> clazz){
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

