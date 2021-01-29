package keda.common.util;


/**
 * @author root
 *    获取当前登录的用户的系统路径属性工具类
 *    @deprecated replacy by {@link SystemUtil}
 */
public class SysUserDirUtil {

	public static String getUserHome() {
		return SystemUtil.getUserHome();
	}
	
	public static String getUserDir(){
		return SystemUtil.getUserDir();
	}
	public static String getTempDir(){
		return SystemUtil.getTempDir();
	}
	public static String getUserName(){
		return SystemUtil.getUserName();
	}
	
	public static String getFileSeparator(){
		return SystemUtil.getFileSeparator();
	}
	
	public static String getPathSeparator(){
		return SystemUtil.getPathSeparator();
	}

}
