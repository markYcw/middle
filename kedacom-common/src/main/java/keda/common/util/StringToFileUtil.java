package keda.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 大文本转换文件
 * @author LinChaoYu
 *
 */
public class StringToFileUtil {
	private static StringToFileUtil instance;
	
	private static Object lock = new Object();
	
	public static StringToFileUtil getInstance(){
		synchronized (lock) {
			if(instance == null){
				synchronized (lock) {
					if(instance == null){
						instance = new StringToFileUtil();
					}
				}
			}
		}
		return instance;
	}
	
	public StringToFileUtil(){
		
	}
	
	/**
	 * 16进字文本数据转换文件
	 * @param src 16进字文本数据
	 * @param filePath 文件保存路径
	 */
	public void saveToFileFor16(final String src,final String filePath){           
		if(src==null||src.length()==0){                
			return;            
		}  
		
		new Thread(){
			public void run(){
				try{
					FileOutputStream out = new FileOutputStream(new File(filePath));               
					byte[] bytes = src.toUpperCase().getBytes();                
					for(int i=0;i<bytes.length;i+=2){                    
						out.write(charToInt(bytes[i])*16+charToInt(bytes[i+1]));               
					}               
					out.close();       
				}catch(Exception e){                
					e.printStackTrace();            
				}       
			}
		}.start();
	}   
	
	/**
	 * Base64位文本数据转换成文件
	 * @param src base64文本数据
	 * @param filePath 文件保存路径
	 */
	public void saveToFileForBase64(String src, String filePath){      
		 saveToFileForBase64(src, filePath, true);
	}
	
	/**
	 * Base64位文本数据转换成文件
	 * @param src base64文本数据
	 * @param filePath 文件保存路径
	 * @param async 是否异步处理
	 */
	public void saveToFileForBase64(final String src,final String filePath,boolean async){           
		if(src==null||src.length()==0){                
			return;            
		}  
		if(async){
			new Thread(){
				public void run(){
					decodeBASE64(src,filePath);     
				}
			}.start();
		}else{
			decodeBASE64(src,filePath);    
		}
	} 
	
	private void decodeBASE64(String src, String filePath){
		try{
			byte[] imgByte = ToolsUtil.decodeBASE64(src);
			InputStream  in = new ByteArrayInputStream(imgByte);
			FileOutputStream out = new FileOutputStream(new File(filePath));  
			byte[] buff = new byte[1024 * 10];
			int len = 0;
			while ((len = in.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
			in.close();
			out.flush();
			out.close();     
		}catch(Exception e){                
			e.printStackTrace();            
		}  
	}
	
	/**
	 * 将服务器文件转换成本地文件
	 * @param src http路径
	 * @param filePath 文件保存路径
	 */
	public void saveToFileForUrl(String src,String filePath){    
		saveToFileForUrl(src,filePath,true);
	}
	
	/**
	 * 将服务器文件转换成本地文件
	 * @param src http路径
	 * @param filePath 文件保存路径
	 * @param async 是否异步处理
	 */
	public void saveToFileForUrl(final String src, final String filePath, boolean async){           
		if(src==null||src.length()==0){                
			return;            
		}  
		
		if(async){
			new Thread(){
				public void run(){
					decodeFileUrl(src, filePath); 
				}
			}.start();
		}else{
			decodeFileUrl(src, filePath);
		}
	}
	
	private void decodeFileUrl(String src, String filePath){
		try{
	        URL url = new URL(src);
	        HttpURLConnection urlc = (HttpURLConnection )url.openConnection();
	        DataInputStream dis = new DataInputStream(urlc.getInputStream());
	        DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath));
	        byte[] bb = new byte[1024*300];
	        int count = 0 ;
	        while((count = dis.read(bb)) >0){
	        	dos.write(bb,0,count);
	        }
	        dis.close();
			dos.close();
		}catch(Exception e){                
			e.printStackTrace();            
		} 
	}
	
	/**
	 * 将文件转化为字节数组字符串，并对其进行Base64编码处理 
	 * @param filePath 源文件路径
	 */
	public static String getFileStr(String filePath)  {
        try {
 	        InputStream in = null;  
 	        byte[] data = null;  
 	        try{  
 	            in = new FileInputStream(filePath);          
 	            data = new byte[in.available()];//读取图片字节数组
 	            in.read(data);  
 	            in.close();  
 	        }catch (IOException e){  
 	            e.printStackTrace();  
 	        }  
        	
			return ToolsUtil.encodeBASE64(data);
		} catch (Exception e) {
			e.printStackTrace();
		}  
        
        return null;
    } 
	
	private int charToInt(byte ch){          
		int val = 0;            
		if(ch>=0x30&&ch<=0x39){               
			val=ch-0x30;            
		}else if(ch>=0x41&&ch<=0x46){               
			val=ch-0x41+10;            
		}      
		
		return val;        
	} 
}
