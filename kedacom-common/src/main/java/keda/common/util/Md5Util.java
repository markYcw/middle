package keda.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类。
 * @author root
 *
 */
public class Md5Util {
	private static Logger log = LogManager.getLogger(Md5Util.class);
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };  

	/**
	 * MD5 32位<b>小写</b>加密
	 * @param str
	 * @see #md5To32BitUpperCase(String)
	 * @see #md5To32Bit(String, boolean)
	 * @return
	 */
	public static String md5To32Bit(String str){
		return md5To32Bit(str, false);
	}
	
	/**
	 * MD5 32位<b>大写</b>加密
	 * @param str 需要加密在字符串
	 * @return
	 */
	public static String md5To32BitUpperCase(String str){
		return md5To32Bit(str, true);
	}
	
	/**
	 * MD5 32位加密
	 * @param str 需要加密在字符串
	 * @param upperCase 是否转换为大写。 true 转换为大写; false 转换为粘写
	 * @return
	 */
	public static String md5To32Bit(String str, boolean upperCase){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("MD5To32", e);
			return "";
		}
		
		md.update(str.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");

			buf.append(Integer.toHexString(i));
		}
		
		String md5 = buf.toString();
		if(upperCase){
				md5 = md5.toUpperCase();
		}
		return md5;
	}

	/**
	 * 对文件md5加密
	 * @param pathname　文件全路径
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5File(String file) throws IOException, NoSuchAlgorithmException{
		File f = new File(file);
		return md5File(f);
	}
	
	/**
	 * 获取文件的MD5值 
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5File(File file) throws IOException, NoSuchAlgorithmException {
		String md5 = null;
		FileInputStream fis = null;
		FileChannel fileChannel = null;
		try {
			fis = new FileInputStream(file);
			fileChannel = fis.getChannel();
			MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(byteBuffer);
			md5 = byteArrayToHexString(md.digest());
		} finally {
			try {
				if(fileChannel != null){
					fileChannel.close();
				}
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
			}
		}

		return md5;
	}
    /** 
     * 字节数组转十六进制字符串 
     * @param digest 
     * @return 
     */  
    private static String byteArrayToHexString(byte[] digest) {  
          
        StringBuffer buffer = new StringBuffer();  
        for(int i=0; i<digest.length; i++){  
            buffer.append(byteToHexString(digest[i]).toUpperCase());  
        }  
        return buffer.toString();  
    }  
      
    /** 
     * 字节转十六进制字符串 
     * @param b 
     * @return 
     */  
    private static String byteToHexString(byte b) {  
             int d1 = (b&0xf0)>>4;  
             int d2 = b&0xf;  
             return hexDigits[d1] + hexDigits[d2];  
    }  

    public static void main(String[] args) {
		try {
			//System.out.println(md5File("/root/kctsql.sql"));
			System.out.println(md5To32Bit("123456"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}

