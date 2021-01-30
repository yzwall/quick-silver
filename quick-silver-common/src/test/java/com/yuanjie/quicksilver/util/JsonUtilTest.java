package com.yuanjie.quicksilver.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.yuanjie.quicksilver.AbstractSpringBootTest;
import lombok.Data;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/22
 */
@Getter
public class JsonUtilTest extends AbstractSpringBootTest {

    private enum TestEnum {

        TEST_ENUM_1(1000, "test_enum_1");

        private Integer code;

        private String desc;

        TestEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 自定义序列化器
     */
    private static class CustomDateSerializer extends StdSerializer<Date> {

        public CustomDateSerializer() {
            this(null);
        }

        public CustomDateSerializer(Class<Date> t) {
            super(t);
        }

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
            jsonGenerator.writeString(DateTimeUtil.formatDateToString(date).orElse(null));
        }
    }

    private static class CustomDeserilzer extends StdDeserializer<Date> {

        public CustomDeserilzer() {
            this(null);
        }

        public CustomDeserilzer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
            String json = jsonParser.getText();
            return Optional.ofNullable(json)
                .flatMap(date -> DateTimeUtil.parseTextIntoDateTime(date).map(DateTimeUtil::asDate))
                .orElse(null);
        }
    }

    @Data
    private static class TestObj implements Serializable {

        private static final long serialVersionUID = 4222078144147159082L;

        private Integer id;

        private String name;

        @JsonDeserialize(using = CustomDeserilzer.class)
        @JsonSerialize(using = CustomDateSerializer.class)
        private Date date;

        private List<TestObj> list;

        private Map<Integer, TestObj> map;

        public static TestObj copyOf(TestObj obj) {
            TestObj newObj = new TestObj();
            newObj.setId(obj.id);
            newObj.setName(obj.getName());
            newObj.setList(obj.getList());
            return newObj;
        }

        public static TestObj of(Integer id, String name) {
            TestObj newObj = new TestObj();
            newObj.setId(id);
            newObj.setName(name);
            newObj.setDate(new DateTime().plusHours(1).toDate());
            return newObj;
        }
    }

    @Test
    public void returnKeyWhenSerialize() {

        String testStr = "abc";
        Integer testInt = 123;
        Long testLong = 123L;
        Double testDouble = 123.0;
        Float testFloat = 123.0F;
        List<String> testListStr = Lists.newArrayList("123", "456", "789");

        String enumJson = JsonUtil.serialize(TestEnum.TEST_ENUM_1, SerializationFeature.WRITE_ENUMS_USING_TO_STRING).orElse(null);
        assertThat(enumJson).isNotNull().isEqualTo(TestEnum.TEST_ENUM_1.name());
        assertThat(JsonUtil.serialize(testStr).orElse(null)).isNotNull().isEqualTo(testStr);
        assertThat(JsonUtil.serialize(testInt).orElse(null)).isEqualTo(String.valueOf(testInt));
        assertThat(JsonUtil.serialize(testLong).orElse(null)).isEqualTo(String.valueOf(testLong));
        assertThat(JsonUtil.serialize(testDouble).orElse(null)).isEqualTo(String.valueOf(testDouble));
        assertThat(JsonUtil.serialize(testFloat).orElse(null)).isEqualTo(String.valueOf(testFloat));
        assertThat(JsonUtil.serialize((Serializable)testListStr)).isPresent();

        assertThat(JsonUtil.deserialize(testStr, String.class).orElse(null)).isNotNull();

        TestObj testObj = new TestObj();
        List<TestObj> testObjs = Stream.iterate(0, i -> i + 1)
            .limit(10)
            .map(num -> TestObj.of(num, "name_" + num))
            .collect(Collectors.toList());
        testObj.setId(1000);
        testObj.setName("name_1000");
        testObj.setList(testObjs);

        Map<Integer, TestObj> map = testObjs.stream().collect(Collectors.toMap(entry -> entry.getId(), entry -> entry));
        testObj.setMap(map);

        String jsonStr = JsonUtil.serialize(testObj).orElse(null);
        assertThat(jsonStr).isNotNull();
        TestObj testObjDes = JsonUtil.deserialize(jsonStr, TestObj.class).orElse(null);
        assertThat(testObjDes).isNotNull();
        assertThat(testObjDes.getName()).isEqualTo(testObj.getName());
        assertThat(testObjDes.getId()).isEqualTo(testObj.getId());
        assertThat(testObjDes.getList()).isNotEmpty().hasSize(testObj.getList().size());
        assertThat(testObjDes.getMap()).isNotEmpty().hasSize(testObj.getMap().size());
        String jsonStr2 = JsonUtil.serialize(testObj, SerializationFeature.WRAP_ROOT_VALUE).orElse(null);
        assertThat(jsonStr2).isNotNull();
    }

    @Test
    public void returnObjWhenDeserialize() {
        TestObj testObj = new TestObj();
        List<TestObj> testObjs = Stream.iterate(0, i -> i + 1)
            .limit(10)
            .map(num -> TestObj.of(num, "name_" + num))
            .collect(Collectors.toList());
        testObj.setId(1000);
        testObj.setName("name_1000");
        testObj.setList(testObjs);
        Map<Integer, TestObj> map = testObjs.stream().collect(Collectors.toMap(entry -> entry.getId(), entry -> entry));
        testObj.setMap(map);

        String listJson = JsonUtil.serialize((Serializable)testObjs).orElse(null);
        assertThat(listJson).isNotNull().isNotEmpty();
        List<TestObj> list = JsonUtil.deserializeList(listJson);
        assertThat(list).isNotNull().isNotEmpty().hasSize(testObjs.size());

        String mapJson = JsonUtil.serialize((Serializable)map).orElse(null);
        assertThat(mapJson).isNotNull().isNotEmpty();
        //Map<Integer, TestObj> map1 = JsonUtil.deserializeMap(mapJson, Integer.class, TestObj.class);
        Map<Integer, TestObj> map1 = JsonUtil.deserializeMap(mapJson);
        assertThat(map1).isNotNull().isNotEmpty().hasSize(map.size());




    }

}