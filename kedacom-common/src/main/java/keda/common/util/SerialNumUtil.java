package keda.common.util;

/**
 * 一个序列号工具
 * 
 * @author TaoPeng
 * @已知使用模块 级联通信模块、审讯业务系统、监管业务系统
 *
 */
public class SerialNumUtil {
	
	
	private static long threadInitNumber = 0;

	/**
	 *获取一个序列号
	 *@deprecated replace by {@link #next()}
	 * @return
	 */
	public static long nextThreadNum() {
		return next();
	}
	/**
	 *获取一个序列号
	 * @return
	 */
	public static synchronized long next() {
		return threadInitNumber++;
	}
}
