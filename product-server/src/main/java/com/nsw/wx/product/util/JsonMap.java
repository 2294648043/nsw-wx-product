package com.nsw.wx.product.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsw.wx.product.pojo.User;
import org.apache.commons.lang.StringUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class JsonMap {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final com.fasterxml.jackson.databind.ObjectMapper objectMappper = new com.fasterxml.jackson.databind.ObjectMapper();

    /**
     * json字符串转JsonNode对象的方法
     */
    public static JsonNode str2JsonNode(String str){
        try {
            return  objectMappper.readTree(str);
        } catch (IOException e) {
            return null;
        }
    }
    /**
     * 对象转字符串
     * @param obj 对象
     * @param <T> 对象类型
     * @return 字符串
     */
    public static <T> String obj2String(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //字符串转对象

    /**
     *  字符串转对象
     * @param str 字符串
     * @param clazz 对象
     * @param <T> 对象类型
     * @return
     */
    public static <T> T string2Obj(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class)? (T) str :objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *
     * map转换json.
     * <br>详细说明
     * @param map 集合
     * @return
     * @return String json字符串
     * @throws
     * @author slj
     */
    public static String mapToJson(Map<String, String> map) {
        Set<String> keys = map.keySet();
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            key = (String) it.next();
            value = map.get(key);
            jsonBuffer.append("\""+key +"\"" + ":" +"\""+ value+"\"");
            if (it.hasNext()) {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }

    //test
    public static void main(String[] args) {
        System.out.println(obj2String( new User(1,"sss","sss","ssssss")));
    }
}
