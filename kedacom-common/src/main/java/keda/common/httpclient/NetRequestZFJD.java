package keda.common.httpclient;

import keda.common.io.MultiPartFormOutputStream;
import keda.common.util.ByteArray;
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

public class NetRequestZFJD {
	private static Logger log = LogManager.getLogger(NetRequestZFJD.class);
	public static String server;
	public static String sessionId;
	private static final String BOUNDARY = "---------7d4a6d158c9";           // 定义数据分隔线
	private static final byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
	public static String post(String namespace, String location, String param) throws Exception {
		log.debug("zfjd server==>>"+server);
		if(server == null)
			throw new Exception("网上督察未向本系统注册!");
		
		String strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
		
		
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
			throw new Exception("发送请求的URL错误[ " + strUrl + " ]");
		}
		
		con.connect();
		
		OutputStream out = null;
		if(param != null && param.length() > 0) {
			try {
				out = con.getOutputStream();
			} catch(UnknownServiceException e) {
				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]");
			} catch(IOException e) {
				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]");
			}
			out.write(param.getBytes("UTF-8"));
			out.flush();
		}
		
		InputStream in = null;
		try {
			in = con.getInputStream();
		} catch(UnknownServiceException e) {
			e.printStackTrace();
			throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]");
		} catch(IOException e) {
			e.printStackTrace();
			throw new Exception("连接服务器出错，URL[ " + strUrl + " ]");
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
	
	public static String get(String namespace, String location, String param) throws Exception {
		String strUrl = null;
		
		if(param != null && param.length() > 0)
			strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}?{4}", server, namespace, location, sessionId, param);
		else
			strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
		
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
	
	private static String getSessionIdFromConnection(URLConnection con) {
		String cookies = con.getHeaderField("Set-Cookie");
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
	
	
	public static String uploatImage(String namespace, String location, String fileName,String oldFileName) throws Exception {
		if(fileName==null || "".equals(fileName))
			return null;
		fileName = new String(fileName.getBytes(),"UTF-8");
//		if(oldFileName != null)
//			oldFileName = new String(oldFileName.getBytes(),"UTF-8");
		String strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
		URL url = new URL(strUrl);
		String boundary = MultiPartFormOutputStream.createBoundary();
		URLConnection con = null;
		try {
			con = MultiPartFormOutputStream.createConnection(url);
		} catch (IOException e) {
			throw new Exception("发送请求的URL错误[ " + strUrl + " ]");
		}
		File file = new File(fileName);
		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("Charset", "utf-8");
		//con.setRequestProperty("Content-length", ""+files.length);
		con.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		//con.setRequestProperty("Content-type", "mulitpart/form-data;boundary="+boundary);
		//con.setRequestProperty("enctype", "MULTIPART/FORM-DATA");
		MultiPartFormOutputStream out = null;
		if(fileName != null){
			try {
				out = new MultiPartFormOutputStream(con.getOutputStream(),boundary);
			} catch(UnknownServiceException e) {
				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]");
			} catch(IOException e) {
				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]");
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
			throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]");
		} catch(IOException e) {
			throw new Exception("连接服务器出错，URL[ " + strUrl + " ]");
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
	
	
	public static byte[] getImageData(String namespace, String location, String filePath) throws Exception {

		String strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
		
		
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
			throw new Exception("发送请求的URL错误[ " + strUrl + " ]");
		}
		
		con.connect();
		
		OutputStream out = null;
		if(filePath != null && filePath.length() > 0) {
			try {
				out = con.getOutputStream();
			} catch(UnknownServiceException e) {
				throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]");
			} catch(IOException e) {
				throw new Exception("连接服务器出错，URL[ " + strUrl + " ]");
			}
			out.write(filePath.getBytes("UTF-8"));
			out.flush();
		}
		
		InputStream in = null;
		try {
			in = con.getInputStream();
		} catch(UnknownServiceException e) {
			e.printStackTrace();
			throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]");
		} catch(IOException e) {
			e.printStackTrace();
			throw new Exception("连接服务器出错，URL[ " + strUrl + " ]");
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
	
	
	public static byte[] getImageByte(File file)throws Exception{
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
	
	
	
	public static String fileUpload(String namespace, String location, Hashtable<String,String> params,Hashtable<String,String> files) throws Exception {
		String strUrl = MessageFormat.format("{0}{1}{2};jsessionid={3}", server, namespace, location, sessionId);
		
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
			throw new Exception("发送请求的URL错误[ " + strUrl + " ]");
		}
		
		con.connect();
		
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
			e.printStackTrace();
			throw new Exception("发送请求的协议未指定，URL[ " + strUrl + " ]");
		} catch(IOException e) {
			e.printStackTrace();
			throw new Exception("连接服务器出错，URL[ " + strUrl + " ]");
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
