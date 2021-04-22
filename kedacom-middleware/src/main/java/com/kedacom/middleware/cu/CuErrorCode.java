package com.kedacom.middleware.cu;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 监控平台错误码
 * @author TaoPeng
 *
 */
public class CuErrorCode {

	private static Properties properties = new  Properties();
	private static final Logger log = Logger.getLogger(CuErrorCode.class);
	
	static {
		init();
	}
	
	private static void init(){


		InputStream is = CuErrorCode.class.getClassLoader().getResourceAsStream("CuSdkErrCode.properties");


		if(properties == null){
			properties = new Properties();
		}
		properties.clear();
		
		try {
			properties.load(is);
		} catch (IOException e) {
			log.error("加载错误码文件失败", e);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				log.error("is.close() error", e);
			}
		}
		
		
	}
	
	/**
	 * 返回错误消息
	 * @param code 错误码
	 * @return
	 */
	public static String getMessage(int code){
		return properties.getProperty(String.valueOf(code));
	}
}
