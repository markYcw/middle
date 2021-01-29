package keda.common.util;

import java.io.File;

public class ClientLogFileUtil {
	public static final String DEFAULTCLIENTLOGHOME = System.getProperty("user.home") + System.getProperty("file.separator") + "kedacom";
	public static final int DATE_DAY = 1000*60*60*24;
	/**
	 * 创建文件目录
	 * @param home 文件目录
	 * @return
	 */
	public static boolean createLogHome(String home){
		if(home == null || "".equals(home))
			return false;
		File file = new File(home);
		if(!file.exists()){
			return file.mkdirs();
		}
		return true;
	}
	/**
	 * 删除文件
	 * @param home 文件主目录
	 * @param maxDate 最长保持天数
	 */
	public static void deleteLogFile(String home,int maxDate){
		if(home == null || "".equals(home))
			return;
		File homeFile = new File(home);
		if(homeFile.isDirectory()){
			long curTime = System.currentTimeMillis();
			File[] files = homeFile.listFiles();
			if(files != null){
				for(File file : files){
					long lastModified = file.lastModified();
					long diff = curTime - lastModified;
					if(diff >= DATE_DAY){
						diff = diff/DATE_DAY;
						if(diff>0){
							file.delete();
						}
					}
				}
			}
		}
	}
}
