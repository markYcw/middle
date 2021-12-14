package keda.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;

/**
 * 描述<b>多个</b>资源文件，每个资源文件可能有多种版本
 * @author TaoPeng
 *
 */
public class ResourceFiles implements I18nResources {

	private static final Logger log = LogManager.getLogger(ResourceFiles.class);
	
	private Map<Locale, HashMap<String, String>> maps = new HashMap<Locale, HashMap<String, String>>();
	
	private List<ResourceFile> files = new ArrayList<ResourceFile>();
	
	public ResourceFiles(){
	}
	
	public void addResourceFile(ResourceFile file){
		this.files.add(file);
	}
	
	private HashMap<String, String> getMap(Locale locale){
		try{
			HashMap<String, String> map = maps.get(locale);
			if(map == null){
				map = new HashMap<String, String>(); 
				for(ResourceFile file : files){
					ResourceBundle bundle = file.getBundle(locale);
					if(bundle != null){
						for(String k : bundle.keySet()){
							map.put(k, bundle.getString(k));
						}
					}
				}
				
				maps.put(locale, map);
			}
			return map;
		}catch(Exception e){
			log.error("加载资源文件出错, file size=" + files.size() + ", Locale=" + locale, e);
			return null;
		}
	}
	
	/**
	 * 获取指定key的值
	 * @param key 
	 * @param locale 语言环境
	 * @return
	 */
	@Override
	public String get(String key, Locale locale){
		if(key == null || key.length() <= 0){
			return key;
		}
		try{
			HashMap<String, String> map = getMap(locale);
			String value = map != null ? map.get(key) : key;
			return value != null ? value : key;
		}catch(NullPointerException e){
			String msg = concat("无效的资源标识[001]: key=[" , key, "], locale=", locale);
			log.warn(msg, e);
			return key;
		}catch(MissingResourceException e){
			return key;
		}catch(Exception e){
			String msg = concat("无效的资源标识[002]: key=[" , key, "], locale=", locale);
			log.warn(msg, e);
			return key;
		}
	}
	
	
	/**
	 * 获取指定key的值，并用指定集合格式化结果
	 * @param key key可能遵循MessageFormat可格式化的字符串的规范
	 * @param parameters 用于格式化结果的值的集合
	 * @param locale 语言环境
	 * @return
	 */
	@Override
	public String get(String key, Object[] parameters, Locale locale){
		try{
			String value = get(key, locale);
			value = MessageFormat.format(value, parameters);
			return value;
		}catch(Exception e){
			log.warn("错误的资源文件", e);
			return key;
		}
	}
	
	/**
	 * 拼装多个字符串
	 * @param args
	 * @return
	 */
	private String concat(Object ...args){
		if(args == null){
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		for(Object a : args){
			sb.append(a);
		}
		return sb.toString();
	}
	
}
