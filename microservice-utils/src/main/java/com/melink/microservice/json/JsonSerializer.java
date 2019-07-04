package com.melink.microservice.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.melink.microservice.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class JsonSerializer {
	private static Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
	private static final ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		//去掉默认的时间戳格式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		//设置为中国上海时区
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		//空值不序列化
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//反序列化时，属性不存在的兼容处理
		objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		//序列化时，日期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//单引号处理
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}


	private JsonSerializer() {
	}

	public ObjectMapper getMapper() {
		return objectMapper;
	}

	public static JsonSerializer newInstance() {
		JsonSerializer js = new JsonSerializer();
		return js;
	}

	public static enum Feature {
		WRAP_ROOT_NODE
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T> String toJson(T entity) {
		try {
			return objectMapper.writeValueAsString(entity);
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T> T toCollection(String json, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * json string convert to map
	 */
	public static <T> Map<String, Object> json2map(String jsonStr)
			throws Exception {
		return objectMapper.readValue(jsonStr, Map.class);
	}

	/**
	 * json string convert to map with javaBean
	 */
//	public static <T> Map<String, T> fromJson(String jsonStr, Class<T> clazz){
//		Map<String, Map<String, Object>> map = null;
//		try {
//			map = objectMapper.readValue(jsonStr,
//                    new TypeReference<Map<String, T>>() {
//                    });
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Map<String, T> result = new HashMap<String, T>();
//		for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
//			result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
//		}
//		return result;
//	}


	public <T> T fromJson(String toUnMarshall, Class<T> clazz) {
		try {
			return objectMapper.readValue(toUnMarshall, clazz);
		} catch (JsonParseException e1) {
			logger.error("An error occurred parsing the json received.", e1);
			logger.error("json {}", toUnMarshall);
			throw PlatformException.asRuntimeException(e1);
		} catch (JsonMappingException e1) {
			logger.error("An error occurred mapping the json received.", e1);
			logger.error("json {}", toUnMarshall);
			throw PlatformException.asRuntimeException(e1);
		} catch (IOException e1) {
			throw PlatformException.asRuntimeException(e1);
		}
	}

	public <T> T fromJson(String toUnMarshall, JavaType type) {
		try {
			return objectMapper.readValue(toUnMarshall, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json array string convert to list with javaBean
	 */
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
		List<Map<String, Object>> list = null;
		try {
			list = objectMapper.readValue(jsonArrayStr,
                    new TypeReference<List<T>>() {
                    });
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<T> result = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			result.add(map2pojo(map, clazz));
		}
		return result;
	}

	/**
	 * map convert to javaBean
	 */
	public static <T> T map2pojo(Map map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);

	}
}
