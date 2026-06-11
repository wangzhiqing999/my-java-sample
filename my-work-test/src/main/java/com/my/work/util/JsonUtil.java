package com.my.work.util;


import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.module.SimpleModule;

// import tools.jackson.datatype.jsr310.JavaTimeModule; // 注意：此导入在3.0中已废弃，仅作对比

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Collections;

/**
 * json 工具类 (基于 Jackson 3.x)
 */
public class JsonUtil {

    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DEFAULT_TIME_ONLY_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public static final JsonMapper OBJECT_MAPPER;

    static {
        // 1. 创建 Builder 并应用所有配置
        JsonMapper.Builder builder = JsonMapper.builder();

        // 2. 配置 ObjectMapper 的特性 (与2.x基本一致)
        builder.enable(SerializationFeature.INDENT_OUTPUT);      // 启用格式化输出(可选)
        builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        builder.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 3. 【关键变更】配置日期时间格式
        //    使用 SimpleModule 来注册自定义格式的序列化器和反序列化器
        SimpleModule customTimeModule = new SimpleModule();
        customTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DEFAULT_TIME_FORMATTER));
        customTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DEFAULT_TIME_FORMATTER));
        customTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DEFAULT_DATE_FORMATTER));
        customTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DEFAULT_DATE_FORMATTER));
        customTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DEFAULT_TIME_ONLY_FORMATTER));
        customTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DEFAULT_TIME_ONLY_FORMATTER));

        builder.addModule(customTimeModule);

        // 4. 构建最终的 JsonMapper 实例
        OBJECT_MAPPER = builder.build();
    }

    /**
     * 将对象转换为 Map (Unmodifiable)
     *
     * @param object 输入对象
     * @return 不可变Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, ?> objToUnModifyMap(Object object) {
        return object == null ? Collections.emptyMap() :
                (Map<String, ?>) OBJECT_MAPPER.convertValue(object, Map.class);
    }

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param obj 输入对象
     * @return JSON 字符串
     */
    public static String toJson(Object obj) {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    /**
     * 将对象转换为 JSON 字节数组
     *
     * @param obj 输入对象
     * @return JSON 字节数组
     */
    public static byte[] toBytesJson(Object obj)  {
        return OBJECT_MAPPER.writeValueAsBytes(obj);
    }
}