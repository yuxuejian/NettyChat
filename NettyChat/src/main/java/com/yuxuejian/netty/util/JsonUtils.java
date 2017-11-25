package com.yuxuejian.netty.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
	
	// 对象转换为Json
	public static String objectToJson(Book  book) {
		return JSON.toJSONString(book);
	}
	// Json转换为对象
	public static Book jsonToObject(String json) {
		return JSON.parseObject(json, Book.class);
	}
	// List转Json
	public static String listToJson(List<Book> list) {
		return JSON.toJSONString(list);
	}
	// Json转List
	public static List<Book> jsonToList(String json) {
		return JSON.parseArray(json, Book.class);
	}
	// Map转Json
	public static String mapToJson(Map<String, Object> map) {
		return JSON.toJSONString(map);
	}
	// Json转Map
	public static Map<String, Object> jsonToMap(String json) {
		return JSON.parseObject(json, Map.class);
	}
	// Json数据操作
	public static String getProperty(String json, String id) {
		JSONObject jsonObj = JSON.parseObject(json);
		return jsonObj.getString(id);
	}
	public static String deleteProperty(String json, String id) {
		JSONObject jsonObj = JSON.parseObject(json);
		Set set = jsonObj.keySet();
		set.remove(id);
		return jsonObj.toJSONString();
	}
	public static String addProperty(String json, String key, String value) {
		JSONObject jsonObj = JSON.parseObject(json);
		jsonObj.put(key, value);
		return jsonObj.toJSONString();
	}
	
	public static void main(String[] args) {
		List<Book> list = new ArrayList<>();
		Book book = new Book();
		book.setId(UUID.randomUUID().toString());
		book.setBookName("追风筝的人");
		book.setPage(1);
		book.setSale(false);
		Book book1 = new Book();
		book1.setId(UUID.randomUUID().toString());
		book1.setBookName("追风筝的人");
		book1.setPage(2);
		book1.setSale(false);
		list.add(book);
		list.add(book1);
		System.out.println(listToJson(list));
		
		JSONArray jsonArray = JSON.parseArray(listToJson(list));
		Book book2 = new Book();
		book2.setId(UUID.randomUUID().toString());
		book2.setBookName("追风筝的人");
		book2.setPage(3);
		book2.setSale(false);
		jsonArray.add(book2);
		System.out.println(jsonArray.toJSONString());
		
		List<Book> list1 = jsonToList(listToJson(list));
		System.out.println(list1.get(0).getBookName());
		
		String json =  "{\"0\":\"jack\",\"1\":\"tom\"}";
		Map<String, Object> map = jsonToMap(json);
		for (String key : map.keySet()) {
			System.out.println(key+":"+map.get(key));
		}
		
		System.out.println(mapToJson(map));
		
		System.out.println(getProperty(json, "1"));
		
		System.out.println(deleteProperty(json, "1"));
		
		System.out.println(addProperty(json, "2", "jerry"));
	}
}
