package com.kedacom.middleware.mcu;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 会议平台错误码
 * 
 * @author LinChaoYu
 *
 */
public class McuErrorCode {

	private static Properties properties = new  Properties();
	private static final Logger log = Logger.getLogger(McuErrorCode.class);
	
	static {
		init();
	}
	
	private static void init(){
		
		InputStream is = McuErrorCode.class.getResourceAsStream("McuSdkErrCode.properties");
		
		
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
