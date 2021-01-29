package com.kedacom.middleware.client;

import com.kedacom.middleware.exception.ProtocolException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


/**
 * 组包、拆包
 * @author TaoPeng
 *
 */
public class TCPPackageUtil {

	/**
	 * 字符集
	 */
	private static Charset charset = Charset.forName("UTF-8");
	private static String version = "SFV1R1";
	private static byte[] HEAD = version.getBytes(charset);
	
	
	/**
	 * 设置版本信息
	 * @param version
	 * @return
	 * @throws ProtocolException
	 */
	public static boolean setVersion(String version) throws ProtocolException{
		if(version == null || version.length() != 6){
			//版本号固定长度为6
			throw new ProtocolException("版本号长度固定为6位");
		}
		
		TCPPackageUtil.version = version;
		TCPPackageUtil.HEAD = version.getBytes(charset);
		return true;
		
	}

	/**
	 * 组包。生成网络数据包（参考既定TCP协议）
	 * @param string
	 * @return
	 */
	public static byte[] buidPack(String string){
		
		byte[] data = string.getBytes(charset);
		
		String str_dataLength = buildDataSize(data.length);
		byte[] dataLengthByte = str_dataLength.getBytes();
		
		int pkgSize = HEAD.length + dataLengthByte.length + data.length;
		
		//组包
		byte[] pkg = new byte[pkgSize];
		int next = 0;
		System.arraycopy(HEAD, 0, pkg, next, HEAD.length);
		next += HEAD.length;
		System.arraycopy(dataLengthByte, 0, pkg, next, dataLengthByte.length);
		next += dataLengthByte.length;
		System.arraycopy(data, 0, pkg, next, data.length);
		next += data.length;
		
		return pkg;
	}

	/**
	 * 将整数转换成6字节长的字符串。最大值支持999999，超过最大值将进行高位截段。
	 * <pre>
	 * 例如： 
	 *   0        =>   000000
	 *   123      =>   000123
	 *   123456   =>   123456
	 *   1234567  =>   234567
	 * </pre>
	 * @param size
	 * @return
	 */
	private static String buildDataSize(int size){
		
		String str = String.valueOf(size);
		
		char[] dest = {'0', '0', '0', '0', '0', '0'};
		char[] src = str.toCharArray(); 
		

		int srcLen = str.length();
		int length = srcLen > 6 ? 6 : srcLen;
		int srcPos = srcLen >= 6 ? srcLen - 6 : 0;
		int destPos = srcLen < 6 ? 6 - srcLen : 0;
		
		
		System.arraycopy(src,  srcPos, dest, destPos, length);
		
		String ret = new String(dest);
		return ret;
	}

	/**
	 * 解包。读取返回数据（协议中的body部分）
	 * @param in
	 * @return
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public static String readPack(InputStream in) throws ProtocolException, IOException{
		
		byte[] buf = readBytes(in, HEAD.length);
		if(!eq(buf, HEAD)){
			throw new ProtocolException("协议错误(协议头错误)");
		}
		
		byte[] bytes2 = readBytes(in, 6);				//6个字节表示后续数据长度
		String str_dataLen = new String(bytes2);
		int dataLen = Integer.parseInt(str_dataLen);
		
		byte[] bytes3 = readBytes(in, dataLen);
		String data = new String(bytes3, charset);
		
		return data;
	}
	
	/**
	 * 从输入流中读取len个字节。
	 * @param in 输入流
	 * @param len 字节长度
	 * @return
	 * @throws IOException
	 * @throws ProtocolException 
	 */
	private static byte[] readBytes(InputStream in, int len) throws IOException, ProtocolException{
		byte[] buf = new byte[len];
		int count = 0;
		while(count < len){
			int temp = in.read(buf, count, buf.length - count);
			if(temp == -1){
				break;
			}
			count += temp;
		}
		
		if(count < buf.length){
			throw new ProtocolException("无效的字节长度, 需要:" + len + ", 实得：" + count);
		}
		
		return buf;
	}
	/**
	 * 比较两个字节序列是否相等
	 * @param b1
	 * @param b2
	 * @return
	 */
	private static boolean eq(byte[] b1, byte[] b2){
		if(b1.length != b2.length){
			return false;
		}
		
		for(int i=0; i < b1.length; i ++){
			if(b1[i] != b2[i]){
				return false;
			}
		}
		
		return true;
	}

}

