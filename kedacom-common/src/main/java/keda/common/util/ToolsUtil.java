package keda.common.util;

import keda.common.exception.MountFileNotFoundException;
import keda.common.exception.RunTimeExecException;
import keda.common.jsonobject.JsonAreaTreeGridNode;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 工具类
 * @author root
 *
 */
public class ToolsUtil {
	private static Logger log = Logger.getLogger(ToolsUtil.class);
	/**状态在线标识*/
	public static final int STATUS_ONLINE = 1;
	/**状态不在线标识*/
	public static final int STATUS_OFFLINE = 0;
	public final static String defaultStyle= "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
//	public final static char[] chars = new char[]{'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public final static char[] chars = new char[]{'0','1','2','3','4','5','6','7','8','9'};
	/**开头位置**/
	public final static int POSITION_START = 1;
	/**末尾位置**/
	public final static int POSITION_END = 2;
	/**两头位置**/
	public final static int POSITION_START_END = 3;
	/**时间格式*/
//	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**日期格式*/
//	private static SimpleDateFormat new SimpleDateFormat("yyyy-MM-dd") = new SimpleDateFormat("yyyy-MM-dd");
	
	public final static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
		"十亿", "百亿", "千亿", "万亿" };
	public final static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
	
	/**
	 * 根据设备PUID获取上级设备ID（平台相关）
	 * @param id 设备ID
	 * @return 字符串
	 */
	public static String getParentID(String id){
		for(int i = 16;i > 0 ;i-=2){
			if(!id.substring(i-2, i ).equals("00")){
				StringBuffer sb = new StringBuffer(id);
				sb.replace(i-2, i,"00");
				id = sb.toString();
				break;
			}
		}
		return id;
	}

	/**
	 * 生成全球唯一性ID
	 * @return 字符串
	 */
	public static String getUUID(){ 
		String s = UUID.randomUUID().toString(); 
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);//去掉"-"符号 
	}
	
	/**
	 * 用Base64编码
	 * @param bytes 字节流
	 * @return 字符串
	 * @throws Exception
	 */
	public static String encodeBASE64(byte[] bytes)throws Exception{
		if(bytes == null)
			return null;
		
		BASE64Encoder baE64 = new BASE64Encoder();
		String result = null;
		result = baE64.encode(bytes);
		return result;
	}

	/**
	 * 用Base64解码
	 * @param str 字符串
	 * @return 字节流
	 * @throws Exception
	 */
	public static byte[] decodeBASE64(String str)throws Exception{
		byte dBytes[] = null;
		BASE64Decoder baD64 = new BASE64Decoder();
		try {
			dBytes = baD64.decodeBufferToByteBuffer(str).array();
		} catch (IOException e) {
			throw new Exception("inner.ToolsUtil.java.zhuanmashibai&&" + e);
		}
		return dBytes;
	}

	/**
	 * 压缩文件
	 * @param filePath 文件路径
	 * @return
	 * @throws IOException
	 */
	public static String createTarFile(String filePath) throws IOException{
		String desFile =filePath + ".zip";
		File file = new File(filePath);
		if(!file.exists()){
			throw new IOException("inner.ToolsUtil.java.wenjianbucunzai&&"+filePath);
		}
		FileInputStream fis = new FileInputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		FileOutputStream fos = new FileOutputStream(desFile);
		GZIPOutputStream gos = new GZIPOutputStream(fos);
		BufferedOutputStream bos = new BufferedOutputStream(gos);
		try{
			byte[] buffer = new byte[1024];
			int  i = 0;
			while((i = bis.read(buffer))!= -1){
				bos.write(buffer,0,i);
			}
			bos.flush();
			
		}catch(IOException ioe){
			log.error("createTarFile IOException",ioe);
			throw ioe;
		}finally{
			if(bis!=null)
				bis.close();
			if(bos!=null)
				bos.close();
		}
		return desFile;
	}
	
	/**
	 * 解压文件
	 * @param filePath 文件路径
	 * @return
	 * @throws IOException
	 */
	public static String getTarFile(String filePath) throws IOException{
		String desFile =filePath.substring(0, filePath.length() - 4);
		File file = new File(filePath);
		if(!file.exists()){
			throw new IOException("inner.ToolsUtil.java.wenjianbucunzai&&fileName");
		}
		FileInputStream fis = new FileInputStream(filePath);
		GZIPInputStream gis = new GZIPInputStream(fis);
		BufferedInputStream bis = new BufferedInputStream(gis);
		FileOutputStream fos = new FileOutputStream(desFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		try{
			byte[] buffer = new byte[1024];
			int  i = 0;
			while((i = bis.read(buffer))!= -1){
				bos.write(buffer,0,i);
			}
			bos.flush();
		}catch(IOException ioe){
			log.error("createTarFile getTarFile",ioe);
			throw ioe;
		}finally{
			if(bis!=null)
				bis.close();
			if(bos!=null)
				bos.close();
		}
		return desFile;
	}
	
	/**
	 * 截取平台CUMNO（平台相关）
	 * @param cumno
	 * @return
	 */
	public static String getPrefixCumno(String cumno){
		char[] chars = cumno.toCharArray();
		for(int i=chars.length;i>0;i--){
			char c = chars[i-1];
			if(c != '0'){
				return cumno.substring(0,i);
			}
		}
		return cumno;
	}
	
	/**
	 * 开始刻录光盘检测（平台相关）
	 * @param devid 盘符：1代表DVD1，2代表DVD2
	 * @param doorstate 仓门状态
	 * @param disctype 盘片类型
	 * @param discstate 盘片状态
	 * @param totalcap 盘片总容量，单位：M
	 * @param remaincap 盘片剩余容量，单位：M
	 * @param mode 设备刻录模式：0：DVD-R模式，1：DVD+R模式，2：蓝光模式
	 * @return result 错误信息
	 */
	public static String isDvdWritable(int devid,int doorstate,int disctype,int discstate,int totalcap,int remaincap,int mode){
		String result = "";
		//仓门状态必须正常(3：正常)
		if(doorstate != 3){
			if(doorstate == 0){//初始化中
				result ="inner.ToolsUtil.java.dvdmangzhengzaichushihua&&"+devid;
			}else if(doorstate == 1){
				result ="inner.ToolsUtil.java.dvdmeiyoudiepian&&"+devid;
			}else if(doorstate == 2){
				result ="inner.ToolsUtil.java.dvdmeiyouguanshangchangmen&&"+devid;
			}else if(doorstate == 4){
				result ="inner.ToolsUtil.java.dvdmangzhengzaishibiediepian&&"+devid;
			}else{
				result ="inner.ToolsUtil.java.dvdzhuangtaibudui&&"+devid;
			}
		}else{
			//必须是空白盘
			if(totalcap != remaincap){
				result ="inner.ToolsUtil.java.dvdfeikongbaipan&&"+devid;
			}
			//必须是可写的碟片：未封盘，可追加
			if(discstate != 1 && discstate != 2){//磁盘状态
				result ="inner.ToolsUtil.java.dvdbukexierudediepian&&"+devid;
			}else{
				switch(disctype){//磁盘类型
					case 0:
						result = "inner.ToolsUtil.java.dvdweizhidiepianleixing&&"+devid;
						break;
					case 1:
						result ="inner.ToolsUtil.java.dvddvdromshizhidugeshi&&"+devid;
						break;
					case 2:
					case 3:
					case 4:
					case 8:
					case 9:
						if(mode == 1){//0:DVD-R模式；1：DVD＋R模式；2：蓝光模式
							result ="inner.ToolsUtil.java.dvddvdrzhizhicidvdrmoshi&&"+devid;
						}else if(mode == 2){
							result = "inner.ToolsUtil.java.dvddvdrbuzhicilanguanmoshi&&"+devid;
						}
						break;
					case 6:
					case 7:
					case 10:
						if(mode == 2){
							result ="inner.ToolsUtil.java.dvddvdrwbuzhicilanguanmoshi&&"+devid;
						}
						break;
					case 5:
					case 11:
						if(mode == 2){
							result = "inner.ToolsUtil.java.dvddvdrwbuzhicilanguanmoshi&&"+devid;
						}
						break;
					case 12:
						result ="inner.ToolsUtil.java.dvddbromshizhidugeshi&&"+devid;
						break;	
					case 13:
					case 14:
					case 15:
						if(mode == 0){//0:DVD-R模式；1：DVD＋R模式；2：蓝光模式
							result = "inner.ToolsUtil.java.dvdlanguanbuzhicidvdrmoshi&&"+devid;
						}else if(mode == 1){
							result =  "inner.ToolsUtil.java.dvdlanguanbuzhicidvdrmoshijiar&&"+devid;
						}
						break;
					case 16:
						if(mode == 0){//0:DVD-R模式；1：DVD＋R模式；2：蓝光模式
							result = "inner.ToolsUtil.java.dvdlanguanbuzhicidvdrmoshi&&"+devid;
						}else if(mode == 1){
							result = "inner.ToolsUtil.java.dvdlanguanbuzhicidvdrmoshijiar&&"+devid;
						}
						break;
				}
			}
		}

		return result;
	}
	
	/**
	 * 光盘校验 （平台相关）
	 * @param devid 盘符：1代表DVD1，2代表DVD2
	 * @param doorstate 仓门状态
	 * @return
	 */
	public static String isCheckDvd(int devid,int doorstate){
		String result = "";
		//仓门状态必须是关闭状态才可以进行光盘校验
		if(doorstate != 3){
			if(doorstate == 0){//初始化中
				result ="inner.ToolsUtil.java.dvdmangzhengzaichushihua&&"+devid;
			}else if(doorstate == 1){
				result ="inner.ToolsUtil.java.dvdmeiyoudiepian&&"+devid;
			}else if(doorstate == 2){
				result ="inner.ToolsUtil.java.dvdmeiyouguanshangchangmen&&"+devid;
			}else if(doorstate == 4){
				result ="inner.ToolsUtil.java.dvdmangzhengzaishibiediepian&&"+devid;
			}else{
				result ="inner.ToolsUtil.java.dvdzhuangtaibudui&&"+devid;
			}
		}
		
		return result;
	}

	/**
	 * 文件复制
	 * @param fin
	 * @param fout
	 * @throws Exception
	 */
	public static void FileCopy(File fin, File fout) throws Exception {
		FileInputStream in = new FileInputStream(fin);
		FileOutputStream out = new FileOutputStream(fout);
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = in.read(buffer))!=-1) {
				out.write(buffer,0,length);
				out.flush();
		}
		out.flush();
		in.close();
		out.close();
	}
	
	/**
	 * 判断全符串的长度，全角与半角不同
	 * @param str 字符串
	 * @return 字符串的长度
	 * @throws UnsupportedEncodingException
	 */
	public static int getLength(String str) throws UnsupportedEncodingException{
		int count = 0;
		for(int i = 0;i<str.length();i++){
			int codePoint = str.codePointAt(i); 
			if (codePoint < 27 || codePoint > 126) { 
				// 全角    
				count += 2; 
			} else { 
				count++; 
			} 
		}
		return count;
	}
	
	/**
	 * 恢复出厂值
	 * @param username
	 * @param password
	 * @param dataBasePath
	 * @param dataBase
	 * @throws IOException
	 */
	public static void resetDatabase(String username,String password, String dataBasePath,String dataBase) throws IOException{
		Runtime r = Runtime.getRuntime();
		String command[] = new String[]{"/bin/sh","-c", "mysql -u"+username + " -p"+ password + " "+dataBase+"<"+dataBasePath};
		Process proc = r.exec(command);
		try{
			InputStream stderr = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while((line =br.readLine())!=null){
				if(line.indexOf("inflating")>0){
					line = line.trim();
					break;
				}
			}
			br.close();
			int resultInt = proc.waitFor();
			if(resultInt != 0){
				InputStream errorIS = proc.getErrorStream();
				InputStreamReader errorISR = new InputStreamReader(errorIS);
				BufferedReader errorBR = new BufferedReader(errorISR);
				log.error("recover to database is error:");
				while((line =errorBR.readLine())!=null){
					log.error(line);
					if(line.indexOf("inflating")>0){
						line = line.trim();
						break;
					}
				}
				errorBR.close();
				proc.destroy();
				throw new IOException("调用数据库脚本出错");
			}
		}catch(Exception e){
			log.error("recover to database is error:" ,e);
			throw new IOException("调用数据库脚本出错");
		}
	}
	
	/**
	 * 备份数据库
	 * @param username
	 * @param password
	 * @param dataBasePath
	 * @param dataBase
	 * @throws IOException
	 */
	public static void backupDatabase(String username,String password, String dataBasePath,String dataBase) throws IOException{
		Runtime r = Runtime.getRuntime();
		String command[] = new String[]{"/bin/sh","-c", "mysqldump -u"+username + " -p"+ password + " -R -B "+dataBase+">"+dataBasePath};
		Process proc = r.exec(command);
		try{
			InputStream stderr = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while((line =br.readLine())!=null){
				if(line.indexOf("inflating")>0){
					line = line.trim();
					break;
				}
			}
			br.close();
			int resultInt = proc.waitFor();
			if(resultInt != 0){
				InputStream errorIS = proc.getErrorStream();
				InputStreamReader errorISR = new InputStreamReader(errorIS);
				BufferedReader errorBR = new BufferedReader(errorISR);
				log.error("backup to database is error:");
				while((line =errorBR.readLine())!=null){
					log.error(line);
					if(line.indexOf("inflating")>0){
						line = line.trim();
						break;
					}
				}
				errorBR.close();
				proc.destroy();
				throw new IOException("调用数据库脚本出错");
			}
		}catch(Exception e){
			log.error("backup to database is error:",e);
			throw new IOException("调用数据库脚本出错");
		}
	}
	
	/**
	 * 判断字符串是否为空，为空则返回true，否则返回false
	 * @param str 要判断的字符串
	 * @return boolean 为空则返回true，否则返回false
	 */
	public static boolean isNull(String str){
		if(str == null || str.length() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 判断字符串是否为空，为空则返回true，否则返回false
	 * @param str 要判断的字符串
	 * @return boolean 为空则返回true，否则返回false
	 */
	public static boolean isNullString(String str){
		if(str == null || str.length() == 0 || "null".equalsIgnoreCase(str))
			return true;
		else
			return false;
	}
	
	public static String getRandom(int length){
		synchronized(chars){
			StringBuffer strBuf = new StringBuffer();
			for(int index=0; index<length; index++){
				int i = (int)(Math.random()*chars.length);
				strBuf.append(chars[i]);
			}
			return strBuf.toString();
		}
	}
	
	/***
	 * 通过父节点、子节点封装区域树
	 * @param par 父节点
	 * @param child 子节点组
	 * @throws JSONException
	 */
	@SuppressWarnings("deprecation")
	public static void setAreaTree(JsonAreaTreeGridNode par, JSONArray child) throws JSONException {
		/// 遍历子节点组
		for(int i = 0; i < child.length();i++){
			/// 创建区域树节点
			JsonAreaTreeGridNode a = new JsonAreaTreeGridNode();
			/// 取得子节点json对象
			JSONObject children = child.getJSONObject(i);
			/// 封装节点编号
			a.setId(children.getString("id"));
			/// 封装节点
			a.setName(children.getString("text"));
			/// 设置节点在线状态
//			String iconCls = children.getString("iconCls");
			//是否在线
			//boolean isOnLine = children.getBoolean("isOnLine");
			boolean isOnLine = children.getBoolean("online");
			a.setOnLine(isOnLine);
//			if(JsonAreaTreeGridNode.PIC_AREA_ONLINE.equals(iconCls)){
//				a.setStatus(JsonAreaTreeGridNode.AREA_STATUS_ONLINE);
//			}else if(JsonAreaTreeGridNode.PIC_AREA_OFFLINE.equals(iconCls)){
//				a.setStatus(JsonAreaTreeGridNode.AREA_STATUS_OFFLINE);
//			}
			/// 父节点添加子节点
			
			par.addChild(a);
			/// 取得子节点的子节点的子节点组
			JSONArray childrens = children.getJSONArray("children");
			if(childrens == null){
				return;
			}
			else{
				/// 递归调用
				setAreaTree(a,childrens);
			}			
		}
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str 字符串
	 * @return ture标识为空，否则返回false
	 */
	public static boolean isEmpty(String str){
		if(str == null)
			return true;
		
		str = str.trim();
		if("null".equalsIgnoreCase(str))
			return true;
		
		if("".equals(str))
			return true;
		
		return false;
	}
	
	/**
	 * 挂载samba服务
	 * @param loacalFilePath 本地挂载路径
	 * @param url samba服务的地址
	 * @param userName 访问samba服务时的用户名
	 * @param password 访问samba服务时的密码
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws RunTimeExecException 运行命令失败异常
	 */
	public static void mountSamba(String localFilePath, String url, String userName, String password) throws IOException, InterruptedException, RunTimeExecException {
		Runtime r = Runtime.getRuntime();
		//组合成mount命令
		String command = MessageFormat.format("mount -t cifs -o username={0},password={1} {2} {3}", userName, password, url, localFilePath);
		Process proc = r.exec(command);
		int resultInt = proc.waitFor();
		if(resultInt == 0)
			return;
		InputStream stderr = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line =br.readLine())!=null){
			log.debug(line);
			sb.append(line);
		}
		br.close();
		if(sb.length()>0)
			throw new RunTimeExecException(sb.toString());
		InputStream errorIS = proc.getErrorStream();
		InputStreamReader errorISR = new InputStreamReader(errorIS);
		BufferedReader errorBR = new BufferedReader(errorISR);
		log.error("recover to database is error:");
		while((line =errorBR.readLine())!=null){
			log.error(line);
			sb.append(line);
		}
		errorBR.close();
		proc.destroy();
		throw new RunTimeExecException(sb.toString());
	}
	
	/**
	 * 卸载mount目录
	 * @param localFilePath 本地挂载samba服务目录
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws RunTimeExecException
	 */
	public static void umountSamba(String localFilePath) throws IOException, InterruptedException, RunTimeExecException {
		Runtime r = Runtime.getRuntime();
		//组合成mount命令
		String command = MessageFormat.format("umount {0}", localFilePath);
		Process proc = r.exec(command);
		int resultInt = proc.waitFor();
		if(resultInt == 0)
			return;
		InputStream stderr = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line =br.readLine())!=null){
			log.debug(line);
			sb.append(line);
		}
		br.close();
		if(sb.length()>0)
			throw new RunTimeExecException(sb.toString());
		InputStream errorIS = proc.getErrorStream();
		InputStreamReader errorISR = new InputStreamReader(errorIS);
		BufferedReader errorBR = new BufferedReader(errorISR);
		log.error("recover to database is error:");
		while((line =errorBR.readLine())!=null){
			log.error(line);
			sb.append(line);
		}
		errorBR.close();
		proc.destroy();
		throw new RunTimeExecException(sb.toString());
	}
	
	/**
	 * 检测该目录下是否挂载了服务
	 * @param filePath
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws RunTimeExecException
	 * @throws MountFileNotFoundException
	 */
	public static boolean checkMountFile(String filePath , String ftpip) throws IOException, InterruptedException, RunTimeExecException, MountFileNotFoundException {
		Runtime r = Runtime.getRuntime();
		//组合成mount命令
		String command = "df -H " + filePath;
		Process proc = r.exec(command);
		proc.waitFor();
		StringBuffer sb = new StringBuffer();
		String line = null;
		//如果执行出错将抛出异常
		InputStream errorIS = proc.getErrorStream();
		InputStreamReader errorISR = new InputStreamReader(errorIS);
		BufferedReader errorBR = new BufferedReader(errorISR);
		log.error("recover to database is error:");
		while((line =errorBR.readLine())!=null){
			log.error(line);
			sb.append(line);
		}
		errorBR.close();
		//如果执行出错
		if(sb.length()>0)
			throw new RunTimeExecException(sb.toString());
		//读取执行结果
		InputStream stderr = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		while((line =br.readLine())!=null){
			//log.debug(line);
			if(line.contains(ftpip) || line.contains("127.0.0.1"))
				return true;
		}
		
		br.close();
//		if(sb.length()>0)
//			throw new RunTimeExecException(sb.toString());
		proc.destroy();
		throw new MountFileNotFoundException();
	}
	
	/**
	 * 检测该目录下是否挂载了服务
	 * @param filePath
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws RunTimeExecException
	 * @throws MountFileNotFoundException
	 */
	public static boolean checkMountFile(String filePath ) throws IOException, InterruptedException, RunTimeExecException, MountFileNotFoundException {
		Runtime r = Runtime.getRuntime();
		//组合成mount命令
		String command = "df -H " + filePath;
		Process proc = r.exec(command);
		proc.waitFor();
		StringBuffer sb = new StringBuffer();
		String line = null;
		//如果执行出错将抛出异常
		InputStream errorIS = proc.getErrorStream();
		InputStreamReader errorISR = new InputStreamReader(errorIS);
		BufferedReader errorBR = new BufferedReader(errorISR);
		log.error("recover to database is error:");
		while((line =errorBR.readLine())!=null){
			log.error(line);
			sb.append(line);
		}
		errorBR.close();
		//如果执行出错
		if(sb.length()>0)
			throw new RunTimeExecException(sb.toString());
		//读取执行结果
		InputStream stderr = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		while((line =br.readLine())!=null){
			//log.debug(line);
			if(line.contains(filePath) )
				return true;
		}
		br.close();
//		if(sb.length()>0)
//			throw new RunTimeExecException(sb.toString());
		proc.destroy();
		throw new MountFileNotFoundException();
	}
	
	/**
	 * 获取文件夹信息
	 * @param filePath 文件路径
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws RunTimeExecException
	 */
	public static String[] getFileInfo(String filePath) throws IOException, InterruptedException, RunTimeExecException {
		Runtime r = Runtime.getRuntime();
		//组合成mount命令
		String command = "df -H " + filePath;
		Process proc = r.exec(command);
		proc.waitFor();
		StringBuffer sb = new StringBuffer();
		String line = null;
		//如果执行出错将抛出异常
		InputStream errorIS = proc.getErrorStream();
		InputStreamReader errorISR = new InputStreamReader(errorIS);
		BufferedReader errorBR = new BufferedReader(errorISR);
		log.error("recover to database is error:");
		while((line =errorBR.readLine())!=null){
			log.error(line);
			sb.append(line);
		}
		errorBR.close();
		//如果执行出错
		if(sb.length()>0)
			throw new RunTimeExecException(sb.toString());
		//读取执行结果
		InputStream stderr = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		line = null;
		int lineNum = 0;
		while((line =br.readLine())!=null){
			//log.debug(line);
			if(line != null){
				if(lineNum > 0){
					sb.append(line);
				}
			}
			lineNum ++;
		}
		br.close();
		proc.destroy();
		String fileInfo = sb.toString();
		if(fileInfo != null){
			fileInfo = fileInfo.trim();
			final String[] strs = fileInfo.split(" ");
			final String[] result = new String[6];
			int index = 0;
			for(String str : strs){
				str = str.trim();
				if(!"".equals(str)){
					result[index] = str;
					index ++;
				}
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 将数据填充为指定长度、指定字符，指定位置(开头或末尾)的字符串
	 * @param number	填充数字
	 * @param length	填充长度
	 * @param fillChar	填充字符
	 * @param position	填充位置
	 * @return
	 */
	public static String getString(long number, int length, char fillChar, int position){
		String strNumber = String.valueOf(number);
		int fillLength = length - strNumber.length();
		if(fillLength>0){
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<fillLength; i++){
				sb.append(fillChar);
			}
			if(position == ToolsUtil.POSITION_START){
				sb.append(strNumber);
				return sb.toString();
			}else if(position == ToolsUtil.POSITION_END){
				return strNumber + sb.toString();
			}
		}
		return strNumber;
	}
	
	/**
	 * 截取字符串
	 * @param str 源符串
	 * @param trimChar 所要截取的字符
	 * @param position 截取的位置 {@link #POSITION_START}   {@link #POSITION_END}  {@link #POSITION_START_END}
	 * @return
	 */
	public static String trim(String str, char trimChar, int position){
		if(str == null || "".equals(str))
			return null;
		char[] cs= str.toCharArray();
		int start = 0;
		int end = cs.length;
		if(position == ToolsUtil.POSITION_START || position == ToolsUtil.POSITION_START_END){
			for(int index=0; index<cs.length; index++){
				if(cs[index] != trimChar){
					start = index;
					break;
				}
			}
		}
		if(position == ToolsUtil.POSITION_END || position == ToolsUtil.POSITION_START_END){
			for(int index=cs.length-1; index>0; index--){
				if(cs[index] != trimChar){
					end = index+1;
					break;
				}
			}
		}
		if(end>=start){
			return new String(cs,start,end-start);
		}
		return null;
	}
	
	/**
	 * 随机产生18位的数字了符串
	 * 主要用于指纹
	 * 以当前时间的毫秒数+随机数组成，基本能保证唯一性
	 * @return
	 */
	public static synchronized long get18RandomNumber(){
		String curTime = System.currentTimeMillis()+"";
		String randomNumber = getRandomNumber(18-curTime.length());
		return Long.parseLong(curTime + randomNumber);
	}
	
	/**
	 * 随机获取
	 * @param length 长度
	 * @return
	 */
	public static String getRandomNumber(int length){
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i< length; i++){
			int number = r.nextInt(10);
			sb.append(number);
		}
		return sb.toString();
	}
	
	public static void writeFileData(String fileName, String data){
		try{
			File direct = new File("/root/fingerdata");
			if(!direct.exists())
				direct.mkdir();
			String filePath = "/root/fingerdata/"+fileName;
			FileOutputStream fos = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(data.getBytes());
			bos.flush();
			bos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 解压文件
	 * @param filePath 源文件路径
	 * @param outPath 目标文件夹路径
	 * @return
	 */
	public static Boolean unZip(String filePath, String outPath) {
	  String unzipfile = filePath; // 解压缩的文件名
	  try {
    	   ZipInputStream zin = new ZipInputStream(new FileInputStream(unzipfile));
    	   ZipEntry entry;
    	   // 创建文件夹
    	   while ((entry = zin.getNextEntry()) != null) {
        	    if (entry.isDirectory()) {
            	     File directory = new File(outPath, entry.getName());
            	     if (!directory.exists()) {
                	      if (!directory.mkdirs()) {
                	       System.exit(0);
                	      }
            	     }
            	     zin.closeEntry();
        	    } else {
            	     File myFile = new File(entry.getName());
            	     FileOutputStream fout = new FileOutputStream(outPath
            	       + myFile.getPath());
            	     DataOutputStream dout = new DataOutputStream(fout);
            	     byte[] b = new byte[1024];
            	     int len = 0;
            	     while ((len = zin.read(b)) != -1) {
            	      dout.write(b, 0, len);
            	     }
            	     dout.close();
            	     fout.close();
            	     zin.closeEntry();
        	    }
    	   }
    	   return true;
	  } catch (IOException e) {
    	   e.printStackTrace();
    	   return false;
	  }
	}
	
	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String format(Timestamp time) {
		if(time == null)return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}
	
	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String format(Date time) {
		if(time == null)return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}
	
	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String format2(Timestamp date) {
		if(date == null)return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	/**
	 * 取得日期与当前日期天数差
	 * @throws ParseException 
	 */
	public static long datepoor(Date d) throws ParseException{
		String  now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Date n = new SimpleDateFormat("yyyy-MM-dd").parse(now);
		return (d.getTime() - n.getTime())/(1000*60*60*24);
	}
	
	/***
	 * 检测字符是否是数字
	 * @param s
	 * @return
	 */
	public static boolean isNum(String s){
		if(isEmpty(s))return true;
		boolean res = true;
		try {
			Long.parseLong(s);			
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	/***
	 * 检测字符是否是数字
	 * @param s
	 * @return
	 */
	public static boolean isTime(String s){
		if(isEmpty(s))return true;
		boolean res = true;
		try {
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	public static Timestamp parse(String t){
		if(isEmpty(t))return null;
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(t);
		} catch (ParseException e) {
			return null;
		}
		return new Timestamp(d.getTime());
		
	}
	
	public static Timestamp parse2(String t){
		if(isEmpty(t))return null;
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(t);
		} catch (ParseException e) {
			return null;
		}
		return new Timestamp(d.getTime());
		
	}
	
	/**
	 * 判断是否是日期格式
	 * @param s
	 * @return
	 */
	public static boolean isDate(String s) {
		if(s==null)return true;
		boolean res = true;
		try {
			new SimpleDateFormat("yyyy-MM-dd").parse(s);
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	/**
	 * 去空字符
	 * @param str
	 * @return
	 */
	public static String getEmpty(String str) {
		if(isEmpty(str) )
			return "";
		return str;
	}
	
	/**
	 * 定时删除文件
	 * @param file
	 * @throws IOException
	 */
	public static void deleteFile(final File file) throws IOException {
		Thread t = new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.error("deleteFile error",e);
				}
				// 路径为文件且不为空则进行删除
				if (file.isFile() && file.exists()) {
					file.delete();
				}
			}
			
		};
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * 判断字符串是不是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		for (int i = str.length();--i>=0;){ 
			if (!Character.isDigit(str.charAt(i))){
				return false; 
			} 
		}
		return true; 
	}
	
	public static String foematInteger(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.parseInt(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					// not need process if the last digital bits is 0
					continue;
				} else {
					// no unit for 0
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		
		return sb.toString();
	}


	public static String formatDecimal(double decimal) {
		String decimals = String.valueOf(decimal);
		int decIndex = decimals.indexOf(".");
		int integ = Integer.parseInt(decimals.substring(0, decIndex));
		int dec = Integer.parseInt(decimals.substring(decIndex + 1));
		String result = foematInteger(integ) + "." + formatFractionalPart(dec);
		
		return result;
	}


	public static String formatFractionalPart(int decimal) {
		char[] val = String.valueOf(decimal).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int n = Integer.parseInt(val[i] + "");
			sb.append(numArray[n]);
		}
		
		return sb.toString();
	}
	
	/**
	 * java 重启 指定的 window 服务
	 * @param servName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void reboot_window_server(String servName) throws IOException, InterruptedException {  
        log.info("重启服务:" + servName);  
        Process procStop;  
        Process procStart;  
        int stopState = -1;  
        int startState = -1;  
  
        // stop the specific service  
        procStop = Runtime.getRuntime().exec("net stop \"" + servName + "\"");  
  
        stopState = getProcExecStat(procStop);  
        log.info(getProcOutput(procStop));  
  
        // wait for 10 seconds   
        try {  
            Thread.sleep(10 * 1000);  
        } catch (InterruptedException e) {  
        	log.error("reboot "+servName+"线程等待时中断...");  
            e.printStackTrace();  
        }  
  
        // restart  
        procStart=Runtime.getRuntime().exec("net start \"" + servName + "\"");  
        startState = getProcExecStat(procStart);  
        log.info(getProcOutput(procStart));  
  
        //if stop exec and start exec both return with failed flag,exists  
        if (stopState != 0 && startState != 0) {  
        	log.info("重启失败,请确认服务名是否有效,程序将退出...");  
        } else {  
        	log.info("重启成功.");  
        }  
    }  
	
	/**
	 * java 重启 指定的 window 进程
	 * @param servName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void reboot_window_process(String filePath,String servName) throws IOException, InterruptedException {  
        log.info("重启服务:" + filePath);  
        Process procStop;  
        Process procStart;  
        int stopState = -1;  
        int startState = -1;  
  
        // stop the specific service  
        log.info("开始停止进程："+servName);
        procStop = Runtime.getRuntime().exec("Taskkill /IM " + servName + "\"");  
  
        stopState = getProcExecStat(procStop);  
        log.info("停止进程"+servName+"返回结果 : "+getProcOutput(procStop));  
        log.info("等待10秒重启启动进程："+servName);  
        // wait for 10 seconds   
        try {  
            Thread.sleep(10 * 1000);  
        } catch (InterruptedException e) {  
        	log.error("reboot "+servName+"线程等待时中断...");
        }  
  
        // restart  
        log.info("开始启动进程："+filePath);
        procStart=Runtime.getRuntime().exec("\""+filePath+"\"");  
        startState = getProcExecStat(procStart);  
        log.info("启动进程"+servName+"返回结果 : "+getProcOutput(procStart));  
  
        //if stop exec and start exec both return with failed flag,exists  
        if (stopState != 0 && startState != 0) {  
        	log.info("重启失败,请确认服务名是否有效,程序将退出...");  
        } else {  
        	log.info("重启成功.");  
        }  
    } 
	
	
	private static int getProcExecStat(Process proc) {  
        try {  
            return proc.waitFor();  
        } catch (InterruptedException e) {  
        	log.error("getProcExecStat 线程等待时中断...",e);
        }  
        
        return -1;  
    }  
  
    private static String getProcOutput(Process proc) throws IOException, InterruptedException {  
        InputStream is = proc.getInputStream();  
        String line;  
        StringBuffer strResult = new StringBuffer();  
  
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
        while ((line = reader.readLine()) != null) {  
            strResult.append(line);  
        }  
        is.close();  
          
        return strResult.toString().trim();  
    }
    
    public static boolean ping(String ipAddress){
    	boolean status = false;//当返回值是true时，说明host是可用的，false则不可。
    	try{
    		if(!isEmpty(ipAddress)){
    			int timeOut = 3000 ;//超时应该在3钞以上        
                status = InetAddress.getByName(ipAddress).isReachable(timeOut);
    		}
    	} catch (Exception e) {
    		log.error("ping ipAddress="+ipAddress, e);
    	}
        
        return status;
    }
    
    public static boolean ping2(String ipAddress){
    	boolean status = false;// 当返回值是true时，说明host是可用的，false则不可。
    	try{
    		Process process = null; // 声明处理类对象
        	InputStream is = null; // 输入流
        	InputStreamReader isr = null; // 字节流
        	BufferedReader buf = null;
            try {
            	if(!isEmpty(ipAddress)){
            		process = Runtime.getRuntime().exec("ping " + ipAddress+" -w 3");//linux系统
                    is = process.getInputStream(); // 实例化输入流
             	   	isr = new InputStreamReader(is);// 把输入流转换成字节流
             	    buf = new BufferedReader(isr);// 从字节中读取文本
                    
                    String line = null;
                    while ((line = buf.readLine()) != null){
                    	if(line.contains("TTL") || line.contains("ttl")){
                    		status = true;
                    		break;
                    	}else if(line.contains("无法访问") || line.contains("Unreachable") || line.contains("请求超时") || line.contains("100% packet loss")){
                    		break;
                    	}
                    }
            	}
            } catch (Exception ex) {
                log.error("ping2 ipAddress="+ipAddress,ex);
            }finally{
            	if(is != null)
            		is.close();
            	
            	if(isr != null)
            		isr.close();
            	
            	if(buf != null)
            		buf.close();
            }
    	}catch(Exception e){
    		 log.error("ping2 close",e);
    	}
 
        return status;
    }
    
    /** 
     * JSON字符串特殊字符处理，比如：“\A1;1300” 
     * @param s 
     * @return String 
     */  
    public static String string2Json(String s) {    
    	if(s == null)
    		return null;
    	
        StringBuffer sb = new StringBuffer();        
        for (int i=0; i<s.length(); i++) {  
            char c = s.charAt(i);    
             switch (c){  
             case '\"':        
                 sb.append("\\\"");        
                 break;        
             case '\\':        
                 sb.append("\\\\");        
                 break;        
             case '/':        
                 sb.append("\\/");        
                 break;        
             case '\b':        
                 sb.append("\\b");        
                 break;        
             case '\f':        
                 sb.append("\\f");        
                 break;        
             case '\n':        
                 sb.append("\\n");        
                 break;        
             case '\r':        
                 sb.append("\\r");        
                 break;        
             case '\t':        
                 sb.append("\\t");        
                 break;        
             default:        
                 sb.append(c);     
             }  
        }      
        
        return sb.toString();     
    }  
}
