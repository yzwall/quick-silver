package com.yuanjie.quicksilver.singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/22
 */
public class JacksonObjectMapperSingleTon {

    private JacksonObjectMapperSingleTon() {
    }

    private static class ObjectMapperHolder {
        static final ObjectMapper mapper = new ObjectMapper();
    }

    public static ObjectMapper getObjectMapper() {
        return ObjectMapperHolder.mapper;
    }
}
