package com.pop.pcms.web.rest.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;

import java.util.Map;

/**
 * Created by meng013 on 2016/11/2.
 */
public class JsonUtils {

    public static Map<String, Object> str2map(String jsonString) {
        JsonParser parser = new JacksonJsonParser();
        return parser.parseMap(jsonString);

    }

    public static String map2str(Map<String, Object> objMap) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("{");
        objMap.forEach((key, val) -> {
            if (strBuff.length() > 1) {
                strBuff.append(",");
            }
            strBuff.append("\"").append(key).append("\":\"").append(val).append("\"");
        });
        strBuff.append("}");
        return strBuff.toString();
    }

    public static ObjectMapper objectMapper;

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T str2obj(String jsonStr, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * json数组转List
     *
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T str2obj(String jsonStr, TypeReference<T> valueTypeRef) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String obj2str(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String[] str2array(String text) {
        String[] results = null;
        if (text.matches("^\\[.+\\]$")) {
            text = text.substring(1, text.length() - 1);
            results = text.split(",");
            for (int i=0;i<results.length;i++) {
                String str = results[i];
                str = str.trim();
                results[i] = str.substring(1,str.length()-1);
            }
        }
        return results;
    }
}
