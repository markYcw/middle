package keda.common.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * 提供json 字符串与对象之间的互相转换
 * @author TaoPeng
 *
 */
public class JSONObjectUtil {
	
	private JSONObjectUtil(){
		
	}
	
	/**
	 * 将一个对象转换成json格式的字符串
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object){
		return toJsonString(object, false);
	}
	
	/**
	 * 将一个对象转换成json格式的字符串
	 * @param object
	 * @param excludeNull true 如果对象中值为null的属性将不会出现在返回的结果中, false 反之
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJsonString(Object object, boolean excludeNull){
		if(object instanceof List){
			return toJsonString4List((List)object, excludeNull);
		}else if (object instanceof Map){
			return toJsonString4Map((Map)object, excludeNull);
		}else{
			return toJsonString0(object, excludeNull);
		}
	}
	
	private static String toJsonString0(Object object, boolean excludeNull){
		JSONObject json = new JSONObject(object);
		
		if(excludeNull){
			removeNULL(json);
		}
		return json.toString();
	}
	
	private static String toJsonString4List(List<Object> objects, boolean excludeNull){
		StringBuffer sb = new StringBuffer(50);
		sb.append("[");
		boolean first = true;
		for(Object o : objects){
			if(!first){
				sb.append(",");
			}else{
				first = false;
			}
			String s = toJsonString0(o, excludeNull);
			sb.append(s);
			
		}
		sb.append("]");
		return sb.toString();
	}

	private static String toJsonString4Map(Map<Object, Object> maps, boolean excludeNull){
		StringBuffer sb = new StringBuffer(50);
		sb.append("{");

		boolean first = true;
		for(Object key : maps.keySet()){
			if(!first){
				sb.append(",");
			}else{
				first = false;
			}
			
			Object value =  maps.get(key);
			sb.append("\"");
			sb.append(key);
			sb.append("\":\"");
			sb.append(value);
			sb.append("\"");
		}
		

		sb.append("}");
		return sb.toString();
	}
	
	private static void removeNULL(JSONObject json ){
			Iterator<?> keys = json.keys();
			if(keys != null){
				ArrayList<String> rks = new ArrayList<String>(10);
				while(keys.hasNext()){
					String k = keys.next().toString();
					Object v = json.opt(k);
					if(v instanceof JSONArray){
						JSONArray ja = (JSONArray)v;
						int l = ja.length();
						for(int i=0; i < l ; i ++){
							JSONObject jo = ja.optJSONObject(i);
							if(jo != null){
								removeNULL(jo);
							}
						}
					}else{
						if(v == null || v.toString().equalsIgnoreCase("null")){
							rks.add(k);
						}
					}
				}
				
				for(String k : rks){
					json.remove(k);
				}
			}
	}
	
	/**
	 * 将一个json格式的字符串数据转换成java对象
	 * @param cls 指示json数据中描述的数据对象
	 * @param jsonString
	 * @return
	 * @throws Exception
	 */
	public static Object parserJSONString(Class<?> cls, String jsonString) throws Exception{
		Map<String,String> args = new HashMap<String,String>();
		JSONObject json = new JSONObject(jsonString);
		Iterator<?> keys = json.keys();
		if(keys != null){
			while(keys.hasNext()){
				String k = keys.next().toString();
				Object v = json.opt(k);
				args.put(k, v != null ? v.toString() : null);
			}
		}
		
		ReflectUtil.setCheckNoFiled(false);
		Object object = ReflectUtil.getObject(cls, args);
		return object;
	}
	
	public static List<?> parserJSONArray(Class<?> cls, String jsonString) throws Exception{
		List<Object> list = new LinkedList<Object>();
		JSONArray ja = new JSONArray(jsonString);
		for(int i=0; i < ja.length(); i ++){
			Object jo = ja.get(i);
			Object o = parserJSONString(cls, jo.toString());
			list.add(o);
		}
		return list;
	}
	
	public static Map<String, String> parseJSON2Map(String jsonString) throws Exception{
		Map<String,String> args = new HashMap<String,String>();
		JSONObject json = new JSONObject(jsonString);
		Iterator<?> keys = json.keys();
		if(keys != null){
			while(keys.hasNext()){
				String k = keys.next().toString();
				Object v = json.opt(k);
				args.put(k, v != null ? v.toString() : null);
			}
		}
		return args;
	}
	
	public static List<?> parseJSON2List(String jsonString) throws Exception{
		List<Object> list = new ArrayList<Object>();
		JSONArray ja = new JSONArray(jsonString);
		for(int i=0; i < ja.length(); i ++){
			Object jo = ja.get(i);
			list.add(jo);
		}
		return list;
	}
	
	public static void main(String args[]) throws Exception{
//		Assess as = new Assess();
//		as.setId(1);
//		
//		AssessContent ac = new AssessContent();
//		ac.setAssessId(2);
//		ac.setContent("3332");
//		
//		as.addContent(ac);
//		
//		String s = toJsonString(as);
//		System.out.println(s);
		
//		String jsonString = "{\"userName\":\"admin\"}";
//		Map<String, String> map = parseJSON2Map(jsonString);
//		System.out.println(map.toString());
		
//		String jsonString = "[\"puId2-channelNo2\",\"puId3-channelNo3\"]";
//		List<String> list = (List<String>) parseJSON2List(jsonString);
//		if(list != null && list.size() > 0){
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i));
//			}
//		}
	}
}
