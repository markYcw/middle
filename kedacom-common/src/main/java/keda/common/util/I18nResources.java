package keda.common.util;

import java.util.Locale;

/**
 * 应用于本工程的资源文件
 * @author TaoPeng
 *
 */
public interface I18nResources {

	public String get(String key, Locale locale);

	/**
	 * 获取指定key的值，并用指定集合格式化结果
	 * @param key key可能遵循MessageFormat可格式化的字符串的规范
	 * @param parameters 用于格式化结果的值的集合
	 * @param locale 语言环境
	 * @return
	 */
	public String get(String key, Object[] parameters, Locale locale);
	
}
