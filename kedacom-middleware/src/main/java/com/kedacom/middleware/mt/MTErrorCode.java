package com.kedacom.middleware.mt;



import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * 终端错误码
 * 
 * @author LinChaoYu
 *
 */
public class MTErrorCode {

	private static Properties properties = new  Properties();
	private static final Logger log = Logger.getLogger(MTErrorCode.class);
	
	static {
		init();
	}
	
	private static void init(){
		
		InputStream is = MTErrorCode.class.getClassLoader().getResourceAsStream("MTSdkErrCode.properties");
		if(is==null){
			throw new MissingResourceException("MTSdkErrCode.properties，not found", MTErrorCode.class.getName(),"MTErrorCode");
		}
		
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
