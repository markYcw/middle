package keda.common.util;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


/**
 * 操作系统属性
 * @author TaoPeng
 *
 */
public class SystemUtil {

	private static Logger log = Logger.getLogger(SystemUtil.class);
	
	public final static String OS_NAME = "os.name";

	private static Properties pro = new Properties(System.getProperties()); 
	
	/**
	 * 获取用户主目录（由操作系统指定）。例如：user.home=/root
	 * @return
	 */
	public static String getUserHome() {
		if(pro != null)
			return pro.getProperty("user.home");
		return null;
	}
	
	/**
	 * 获取用户工作目录（与当前程序有关）。例如:/home/taopeng/trocp/code/workspace_SXMS1.0_Daily/kedacommon
	 * @return
	 */
	public static String getUserDir(){
		if(pro != null)
			return pro.getProperty("user.dir");
		return null;
	}
	
	/**
	 * 获取操作系统临时目录
	 * @return
	 */
	public static String getTempDir(){
		if(pro != null)
			return pro.getProperty("java.io.tmpdir");
		return null;
	}
	/**
	 * 获取用户帐户全称
	 * @return
	 */
	public static String getUserName(){
		if(pro != null)
			return pro.getProperty("user.name");
		return null;
	}
	
	public static String getFileSeparator(){
		if(pro != null)
			return pro.getProperty("file.separator");
		return null;
	}
	
	public static String getPathSeparator(){
		if(pro != null)
			return pro.getProperty("path.separator");
		return null;
	}
	
	/**
	 * 获取指定的属性
	 * @param key
	 * @return
	 */
	private static String getProperty(String key){
//		return System.getProperty(key);
		return pro != null ? pro.getProperty(key) : null;
	}
	
	/**
	 * 返回操作系统的名称
	 * @return
	 */
	public static String getOSName(){
		return getProperty(OS_NAME);
	}
	
	/**
	 * 当前操作系统是否是Windows
	 * @return
	 */
	public static boolean isWindows(){
		String os = getOSName();
		return os != null && 
				(os.indexOf("windows") >= 0
				|| os.indexOf("Windows") >= 0);
	}
	
	/**
	 * 当前操作系统是否是Linux
	 * @return
	 */
	public static boolean isLinux(){
		String os = getOSName();
		return os != null && 
				(os.indexOf("linux") >= 0
				|| os.indexOf("Linux") >= 0);
	}
	
	/**
	 * 返回当前操作系统是否64位。另外，如果操作系统不是64位，则是32位.
	 * @return
	 */
	public static boolean isOs64bit(){
		/*
		 * 操作系统位数标识[os.arch]
		 * Win32: x86
		 * Win64: X64
		 * 
		 * Mac32: x86_32
		 * Mac64: x86_64
		 * 
		 * Linux32: i386/i486/i586/i686 
		 * Linux64: X86_64/amd64/
		 */
		
		String arch = getProperty("os.arch");
		return arch.indexOf("64") >= 0;
	}
	
	/**
	 * 返回当前JVM是否64位。另外，如果不是64位，则是32位
	 * @return
	 */
	public static boolean isJvm64bit(){
		String model = getProperty("sun.arch.data.model");
		return model.equalsIgnoreCase("64");
	}
	/**
	 * 更新系统时间
	 * @param t
	 * @return
	 * @throws IOException 
	 */
	public static void setTime(long time){
		
		Date date = new Date(time);
		String cmd = null;
		if(isLinux()){
			//date 月日时分年.秒 %2d%2d%2d%2d%4d.%2d
			SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmyyyy.ss");
			cmd = sdf.format(date);
			cmd = "date " + cmd;
		}
		
		if(cmd != null){
			try {
				Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
				log.warn("设置系统时间错误：" + cmd, e);
			}
		}
	}
	
	public static void main(String args[]){
		System.out.println(new Date());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 10);
		setTime(c.getTimeInMillis());
		System.out.println(new Date());
		
		
		Properties ps = System.getProperties();
		for(Object key : ps.keySet()){
			Object value = ps.getProperty(key.toString());
			System.out.println(key + "=" + value);
		}
	}
}
