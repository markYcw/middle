package keda.common.util;

/**
 * 属性改变监听器。注意：监听器不能监听到文件在外部系统的更改
 * @author TaoPeng
 * @see PropertiesFile
 *
 */
public interface PropertiesFileListener {
	/**
	 * 如果属性改变触发此事件。
	 * @param name
	 * @param value
	 */
	public void onChange(String name, String value);
}
