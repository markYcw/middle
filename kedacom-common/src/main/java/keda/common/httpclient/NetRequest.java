package keda.common.httpclient;

import keda.common.io.MultiPartFormOutputStream;
import keda.common.util.ByteArray;
import keda.common.util.ToolsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * 基于HTTP协议进行交互
 * @author root
 *
 */
public class NetRequest {
	private static Logger log = LogManager.getLogger(NetRequest.class);
	public String server;
	public String sessionId;
	private static final String BOUNDARY = "---------7d4a6d158c9";// 定义数据分隔线
	private static final byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public static void main(String[] args) throws Exception {
		String l = "123123.jsp?1=1&1121=12?123&^&#$!";
		String[] ss = parserLocation(l);
		System.out.println(ss[0]);
		System.out.println(ss[1]);
		NetRequest req = new NetRequest();
		req.setServer("http://172.16.132.83:8080");
		System.out.println(req.post("/rydwweb", "/gettaglocationinfo.do", "lastEventId=10"));
	}
	
	/**
	 * 将资源描述符号分为资源路径和查询字符串两部分
	 * @param location
	 * @return
	 */
	public static String[] parserLocation(String location){
		if(location != null){
			int i = location.indexOf('?');
			
			String l;
			String q;
			if(i >= 0){
				l = location.substring(0, i);
			}else{
				l = location;
			}
			
			if(i >=0 && i < location.length() - 1){
				q = location.substring(i + 1, location.length());
			}else{
				q = "";
			}
					
			String[] ss = new String[2];
			ss[0] = l;
			ss[1] = q;
			return ss;
		}else{
			return null;
		}
	}
	
	/**
	 * http请求POST方法
	 * @param namespace 命名空间
	 * @param location action名字
	 * @param param 传递参数
	 * @return
	 * @throws Exception
	 */
	private Object postLock = new Object();
	public byte[] getImage(String namespace, String location, String param) throws Exception {
		synchronized(postLock){
	//		param = new String(param.getBytes(), "UTF-8");
//			long t1 = System.currentTimeMillis();
			if(sessionId == null)
				sessionId = "";
			
			String ss[] = parserLocation(location);
			String strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}?{4}", server, namespace, ss[0], sessionId, ss[1]);
			log.debug("NetRequest post url: " + strUrl);
			
			URL url = new URL(strUrl);
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection)url.openConnection();
				//con.setConnectTimeout(1000*30);
				con.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible) ");
				con.setRequestProperty("Accept", "*/*");
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);
				con.setRequestProperty("contentType", "utf-8");
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			} catch (IOException e) {
				log.error("post IOException",e);
				throw new Exception("发送请求的URL错误[" + strUrl + "]", e);
			}
			
			con.connect();
			
			log.debug("post parameter: " + param);
			OutputStream out = null;
			if(param != null && param.length() > 0) {
				try {
					out = con.getOutputStream();
				} catch(UnknownServiceException e) {
					log.error("post UnknownServiceException",e);
					throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
				} catch(IOException e) {
					log.error("post IOException",e);
					throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
				}
				out.write(param.getBytes("UTF-8"));
				out.flush();
			}
//			long t2 = System.currentTimeMillis();
//			log.error("post time1 : " + (t2-t1));
			InputStream in = null;
			try {
				in = con.getInputStream();
			} catch(UnknownServiceException e) {
				log.error("post UnknownServiceException",e);
				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
			} catch(IOException e) {
				log.error("post IOException",e);
				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
			}
			
			ByteArray buffer = new ByteArray(10240, 10240);
			byte [] cbuf = new byte[10240];
			int len;
	
			while((len=in.read(cbuf)) != -1) {
				buffer.append(cbuf, len);
			}
//			long t3 = System.currentTimeMillis();
//			log.error("post time2 : " + (t3-t2));
			String rtSessionId = getSessionIdFromConnection(con);
			if(rtSessionId != null && !rtSessionId.equals(sessionId))
				sessionId = rtSessionId;
			
			if(out != null)
				out.close();
			in.close();
			if(con instanceof HttpURLConnection)
				((HttpURLConnection)con).disconnect();
			
			return buffer.toArray();
		}
	}
	public String post(String namespace, String location, String param) throws Exception {
		synchronized(postLock){
	//		param = new String(param.getBytes(), "UTF-8");
//			long t1 = System.currentTimeMillis();
			if(sessionId == null)
				sessionId = "";
			
			String strUrl = null;
			if(!ToolsUtil.isEmpty(location)){
				String ss[] = parserLocation(location);
				strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}?{4}", server, namespace, ss[0], sessionId, ss[1]);
			}else{
				strUrl = MessageFormat.format("{0}", namespace);
			}
			
			log.debug("NetRequest post url: " + strUrl);
			
			URL url = new URL(strUrl);
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection)url.openConnection();
				//con.setConnectTimeout(1000*30);
				con.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible) ");
				con.setRequestProperty("Accept", "*/*");
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);
				con.setRequestProperty("contentType", "utf-8");
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			} catch (IOException e) {
				log.error("post IOException",e);
				throw new Exception("发送请求的URL错误[" + strUrl + "]", e);
			}
			
			con.connect();
			
			log.debug("post parameter: " + param);
			OutputStream out = null;
			if(param != null && param.length() > 0) {
				try {
					out = con.getOutputStream();
				} catch(UnknownServiceException e) {
					log.error("post UnknownServiceException",e);
					throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
				} catch(IOException e) {
					log.error("post IOException",e);
					throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
				}
				out.write(param.getBytes("UTF-8"));
				out.flush();
			}
//			long t2 = System.currentTimeMillis();
//			log.error("post time1 : " + (t2-t1));
			InputStream in = null;
			try {
				in = con.getInputStream();
			} catch(UnknownServiceException e) {
				log.error("post UnknownServiceException",e);
				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
			} catch(IOException e) {
				log.error("post IOException",e);
				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
			}
			
			ByteArray buffer = new ByteArray(10240, 10240);
			byte [] cbuf = new byte[10240];
			int len;
	
			while((len=in.read(cbuf)) != -1) {
				buffer.append(cbuf, len);
			}
//			long t3 = System.currentTimeMillis();
//			log.error("post time2 : " + (t3-t2));
			String rtSessionId = getSessionIdFromConnection(con);
			if(rtSessionId != null && !rtSessionId.equals(sessionId))
				sessionId = rtSessionId;
			
			if(out != null)
				out.close();
			in.close();
			if(con instanceof HttpURLConnection)
				((HttpURLConnection)con).disconnect();
			
			byte [] res = buffer.toArray();
			buffer = null;
			return new String(res, "UTF-8");
//			long t4 = System.currentTimeMillis();
//			log.error("post time3 : " + (t4-t3));
		}
	}

	/**
	 * http请求GET方法
	 * @param namespace 命名空间
	 * @param location action名字
	 * @param param 传递参数
	 * @return
	 * @throws Exception
	 */
	private Object getLock = new Object();
	public String get(String namespace, String location, String param) throws Exception {
		synchronized(getLock){
			String strUrl = null;

			String ss[] = parserLocation(location);
			String q = "";
			if(param != null){
				q += param + "&";
			}
			if(ss[1] != null){
				q += ss[1];
			}
			
			if(q != null && q.length() > 0)
				strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}?{4}", server, namespace, location, sessionId, q);
			else
				strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
			
			log.debug("NetRequest get url: " + strUrl);
			URL url = new URL(strUrl);
			
			URLConnection con = url.openConnection();
			con.connect();
			
			InputStream in = con.getInputStream();
			
			ByteArray buffer = new ByteArray(1024, 1024);
			byte [] cbuf = new byte[1024];
			int len;
	
			while((len=in.read(cbuf)) != -1) {
				buffer.append(cbuf, len);
			}
			
			String rtSessionId = getSessionIdFromConnection(con);
			if(rtSessionId != null && !rtSessionId.equals(sessionId))
				sessionId = rtSessionId;
			
			in.close();
			
			byte [] res = buffer.toArray();
			buffer = null;
			//System.gc();
			
			return new String(res, "UTF-8");
		}
	}
	
	private String getSessionIdFromConnection(URLConnection con) {
		String cookies = con.getHeaderField("Set-Cookie");
		log.debug("Set-Cookie : " + cookies);
		if(cookies == null)
			return null;
		
		String ss [] = cookies.split(";");
		if(ss == null || ss.length == 0)
			return null;
		
		for(String ts : ss) {
			String []cs = ts.split("=");
			if(cs != null && cs.length > 1) {
				String key = cs[0];
				if(key.trim().equalsIgnoreCase("JSESSIONID"))
					return cs[1].trim();
			}
		}
		
		return null;
	}
	
	private Object uploadImageLock = new Object();
	public String uploatImage(String namespace, String location, String fileName,String oldFileName) throws Exception {
		synchronized(uploadImageLock){
			log.debug(fileName);
	//		param = new String(param.getBytes(), "UTF-8");
	//		log.debug(param);
//			if(fileName!=null)
			fileName = new String(fileName.getBytes(),"UTF-8");
//			if(oldFileName != null)
//				oldFileName = new String(oldFileName.getBytes(),"UTF-8");
			String strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
			log.debug(strUrl);
			strUrl = "http://localhost:8080/testweb2/upload.do";
			log.debug("request url: " + strUrl);
			URL url = new URL(strUrl);
			String boundary = MultiPartFormOutputStream.createBoundary();
			URLConnection con = null;
			try {
				con = MultiPartFormOutputStream.createConnection(url);
			} catch (IOException e) {
				log.error("uploatImage IOException",e);
				throw new Exception("发送请求的URL错误[ " + strUrl + " ]", e);
			}
			File file = new File(fileName);
			byte[] files = getImageByte(file);
			con.setRequestProperty("Accept", "*/*");
			con.setRequestProperty("Charset", "utf-8");
			//con.setRequestProperty("Content-length", ""+files.length);
			con.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			log.debug("files.length = " + files.length);
			//con.setRequestProperty("Content-type", "mulitpart/form-data;boundary="+boundary);
			//con.setRequestProperty("enctype", "MULTIPART/FORM-DATA");
			MultiPartFormOutputStream out = null;
			if(fileName != null){
				try {
					out = new MultiPartFormOutputStream(con.getOutputStream(),boundary);
				} catch(UnknownServiceException e) {
					log.error("uploatImage UnknownServiceException",e);
					throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
				} catch(IOException e) {
					log.error("uploatImage IOException",e);
					throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
				}
				//out.writeField("fileName", fileName);
				
				out.writeFile("file",URLConnection.guessContentTypeFromName(file.getName()),file);
				//out.writeFile("file", URLConnection.guessContentTypeFromName(file.getName()),file.getName(),files);
				
				out.close();
			}
			
			InputStream in = null;
			try {
				in = con.getInputStream();
			} catch(UnknownServiceException e) {
				log.error("uploatImage UnknownServiceException",e);
				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
			} catch(IOException e) {
				log.error("uploatImage IOException",e);
				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
			}
			byte [] cbuf = new byte[1024];
			int len;
			StringBuffer result = new StringBuffer(1024);
			while((len=in.read(cbuf)) != -1) {
				result.append(new String(cbuf, 0, len, "UTF-8"));
			}
			
			if(out != null)
				out.close();
			in.close();
			
			return result.toString();
		}
	}
	
	private Object getImageDataLock = new Object();
	public byte[] getImageData(String namespace, String location, String filePath) throws Exception {
		synchronized(getImageDataLock){
			String strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
			
			log.debug("NetRequest post url: " + strUrl);
			
			URL url = new URL(strUrl);
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection)url.openConnection();
				con.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible) ");
				con.setRequestProperty("Accept", "*/*");
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);
				con.setRequestProperty("contentType", "utf-8");
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			} catch (IOException e) {
				log.error("getImageData IOException",e);
				throw new Exception("发送请求的URL错误[ " + strUrl + " ]", e);
			}
			
			con.connect();
			
			OutputStream out = null;
			if(filePath != null && filePath.length() > 0) {
				try {
					out = con.getOutputStream();
				} catch(UnknownServiceException e) {
					log.error("getImageData UnknownServiceException",e);
					throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
				} catch(IOException e) {
					log.error("getImageData IOException",e);
					throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
				}
				out.write(filePath.getBytes("UTF-8"));
				out.flush();
			}
			
			InputStream in = null;
			try {
				in = con.getInputStream();
			} catch(UnknownServiceException e) {
				log.error("getImageData UnknownServiceException",e);
				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
			} catch(IOException e) {
				log.error("getImageData IOException",e);
				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
			}
			
			ByteArray buffer = new ByteArray(1024, 1024);
			byte [] cbuf = new byte[1024];
			int len;
	
			while((len=in.read(cbuf)) != -1) {
				buffer.append(cbuf, len);
			}
			
			String rtSessionId = getSessionIdFromConnection(con);
			if(rtSessionId != null && !rtSessionId.equals(sessionId))
				sessionId = rtSessionId;
			
			if(out != null)out.close();
			in.close();
			if(con instanceof HttpURLConnection)
				((HttpURLConnection)con).disconnect();
			
			byte [] res = buffer.toArray();
			buffer = null;
			//System.gc();
			
			return res;
		}
	}
	private byte[] getImageByte(File file)throws Exception{
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int c;
		while((c=dis.read())!= -1)
			bos.write(c);
		byte[] bt = bos.toByteArray();
		bos.close();
		dis.close();
		fis.close();
		return bt;
	}
	
	
	private Object fileUploadLock = new Object();
	public String fileUpload(String namespace, String location, Hashtable<String,String> params,Hashtable<String,String> files) throws Exception {
		synchronized(fileUploadLock){
	//		log.debug(param);
	//		param = new String(param.getBytes(), "UTF-8");
	//		log.debug(param);
			String strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
			
			log.debug("NetRequest post url: " + strUrl);
			
			URL url = new URL(strUrl);
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection)url.openConnection();
				con.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible) ");
				con.setRequestProperty("Accept", "*/*");
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);
				con.setRequestProperty("contentType", "UTF-8");
				con.setRequestMethod("POST");
	//			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setRequestProperty("connection", "Keep-Alive");
	//			con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
				con.setRequestProperty("Charsert", "UTF-8");
				con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			} catch (IOException e) {
				log.error("fileUpload IOException",e);
				throw new Exception("发送请求的URL错误[ " + strUrl + " ]", e);
			}
			
			con.connect();
			
	//		log.debug("post parameter : " + params.size());
			OutputStream out = con.getOutputStream();
			if(params!=null){
				Iterator<String> it = params.keySet().iterator();
				while(it.hasNext()){
					String serverName = it.next();
					String name = files.get(serverName);
					StringBuilder sb = new StringBuilder();
					sb.append("--");
					sb.append(BOUNDARY);
					sb.append("\r\n");
					sb.append("Content-Disposition: form-data;name=\"" + serverName + "\"");
					sb.append("\r\n");
					sb.append(name);
					byte[] data = sb.toString().getBytes();
					out.write(data);
					out.write("\r\n".getBytes()); 
				}
			}
	//		if(param != null && param.length() > 0) {
	//			try {
	//				out = con.getOutputStream();
	//			} catch(UnknownServiceException e) {
	//				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]");
	//			} catch(IOException e) {
	//				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]");
	//			}
	//			out.write(param.getBytes("UTF-8"));
	//			out.flush();
	//		}
			if(files!=null){
				Iterator<String> it = files.keySet().iterator();
				while(it.hasNext()){
					String serverFileName = it.next();
					String fileName = files.get(serverFileName);
					File file = new File(fileName);
					StringBuilder sb = new StringBuilder();
		
					sb.append("--");
					sb.append(BOUNDARY);
					sb.append("\r\n");
					sb.append("Content-Disposition: form-data;name=\"" + serverFileName + "\";filename=\""+ file.getName() + "\"\r\n");//这里的name就是action里的File uploadFile
					sb.append("Content-Type:application/octet-stream\r\n\r\n");
					byte[] data = sb.toString().getBytes();
					out.write(data);
					DataInputStream dis = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = dis.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个
					dis.close();
				}
			}
			out.write(end_data);
			out.flush();
			
			
			
			InputStream in = null;
			try {
				in = con.getInputStream();
			} catch(UnknownServiceException e) {
				log.error("fileUpload UnknownServiceException",e);
				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]", e);
			} catch(IOException e) {
				log.error("fileUpload IOException",e);
				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]", e);
			}
			
			ByteArray buffer = new ByteArray(1024, 1024);
			byte [] cbuf = new byte[1024];
			int len;
	
			while((len=in.read(cbuf)) != -1) {
				buffer.append(cbuf, len);
			}
			
			String rtSessionId = getSessionIdFromConnection(con);
			if(rtSessionId != null && !rtSessionId.equals(sessionId))
				sessionId = rtSessionId;
			
			if(out != null)
				out.close();
			in.close();
			if(con instanceof HttpURLConnection)
				((HttpURLConnection)con).disconnect();
			
			byte [] res = buffer.toArray();
			buffer = null;
			//System.gc();
			
			return new String(res, "UTF-8");
		}
	}
}
