package keda.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

/**
 * 描述一个具有多种语言版本的资源文件
 * @author TaoPeng
 *
 */
public class ResourceFile implements I18nResources {

	private static final Logger log = LogManager.getLogger(ResourceFile.class);
	
	private Map<Locale, ResourceBundle> maps = new HashMap<Locale, ResourceBundle>();
	
	private String baseName;
	/**
	 * 
	 * @param baseName 默认资源文件的路径(相对classpath的根目录[a fully qualified class name])，如com/kedacom/resources/ApplicationResources
	 */
	public ResourceFile(String baseName){
		this.baseName = baseName;
	}
	
	protected ResourceBundle getBundle(Locale locale){
		try{
			ResourceBundle bundle = maps.get(locale);
			if(bundle == null){
				bundle = ResourceBundle.getBundle(baseName, locale);
				if(bundle != null){
					//工程中可能缺失指定的资源文件，但不能因此影响系统继续运行
					maps.put(locale, bundle);
				}
			}
			return bundle;
		}catch(Exception e){
			log.error("加载资源文件出错, file=" + baseName + ", Locale=" + locale, e);
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
			ResourceBundle bundle = getBundle(locale);
			return bundle != null ? bundle.getString(key) : key;
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
	 * 获取指定key的值
	 * @param key
	 * @param locale
	 * @deprecated replace by {@link #get(String, Locale)}
	 * @return
	 */
	public String get(String key, String locale){
		try {
			Locale l = parseLocale(locale);
			return get(key, l);
		} catch (ParseException e) {
			String msg = concat("无效的语言环境描述符[001]:" , locale);
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
	 * 
	 * @param key
	 * @param parameters
	 * @param locale
	 * @deprecated {@link #get(String, Object[], Locale)}
	 * @see #get(String, Object[], Locale)
	 * @return
	 */
	public String get(String key, Object[] parameters, String locale){
		try {
			Locale l = parseLocale(locale);
			return get(key, parameters, l);
		} catch (ParseException e) {
			String msg = concat("无效的语言环境描述符[002]:" , locale);
			log.warn(msg, e);
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
	/**
	 * 将string解析成Locale
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Locale parseLocale(String string) throws ParseException{
		if(isLocaleString(string)){
			String[] ls = string.split("_");
			return new Locale(ls[0], ls[1]); 
		}else{
			throw new ParseException("fomart exception ,this string must like zh_CN",0);
		}
	}
	/**
	 * 返回字符串string是否表示语言环境字符串。判定格式为"^[a-zA-Z]{2}_[a-zA-Z]{2}$"
	 * @param string
	 * @return
	 */
	private static boolean isLocaleString(String string){
		return string != null && string.matches("^[a-zA-Z]{2}_[a-zA-Z]{2}$");
	}
	
}
