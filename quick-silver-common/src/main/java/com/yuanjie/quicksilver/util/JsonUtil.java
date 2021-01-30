package com.yuanjie.quicksilver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuanjie.quicksilver.constant.PunctuationConstant;
import com.yuanjie.quicksilver.singleton.JacksonObjectMapperSingleTon;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.List;


/**
 * Jackson序列化工具类
 *
 * @author yuanjie(qinhua)
 * @date 2021/01/22
 */
public class JsonUtil {

    private JsonUtil() {
    }

    private static String replaceString(String json) {
        if (StringUtils.startsWith(json, PunctuationConstant.DOUBLE_QUOTATION)) {
            json = StringUtils.substringBetween(json, PunctuationConstant.DOUBLE_QUOTATION,
                PunctuationConstant.DOUBLE_QUOTATION);
        }
        return json;
    }

    public static Optional<String> serialize(Serializable obj) {
        try {
            String json = JacksonObjectMapperSingleTon.getObjectMapper().writeValueAsString(obj);
            return Optional.ofNullable(json).map(JsonUtil::replaceString);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> serialize(Serializable obj, SerializationFeature... features) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<SerializationFeature> featureList = Lists.newArrayList(features);
            if (CollectionUtils.isNotEmpty(featureList)) {
                mapper.enable(featureList.iterator().next(),
                    Lists.newArrayList(features).toArray(new SerializationFeature[featureList.size()]));
            }
            return Optional.ofNullable(mapper.writeValueAsString(obj)).map(JsonUtil::replaceString);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> deserialize(String json, Class<T> clazz) {
        try {
            if (clazz == String.class && !StringUtils.startsWith(json, PunctuationConstant.DOUBLE_QUOTATION)) {
                json = new StringBuilder()
                    .append(PunctuationConstant.DOUBLE_QUOTATION)
                    .append(json)
                    .append(PunctuationConstant.DOUBLE_QUOTATION)
                    .toString();
            }
            return Optional.ofNullable(JacksonObjectMapperSingleTon.getObjectMapper().readerFor(clazz).readValue(json));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static <T> List<T> deserializeList(String json) {
        try {
            List<T> result = JacksonObjectMapperSingleTon.getObjectMapper().readValue(json, new TypeReference<List<T>>() {});
            return result;
        } catch (JsonProcessingException e) {
            return Lists.newArrayList();
        }
    }

    public static <K, V> Map<K, V> deserializeMap(String json) {
        try {
            return JacksonObjectMapperSingleTon.getObjectMapper().readValue(json, new TypeReference<Map<K, V>>() {});
        } catch (JsonProcessingException e) {
            return Maps.newHashMap();
        }
    }
}
