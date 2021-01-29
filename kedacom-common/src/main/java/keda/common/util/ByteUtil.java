package keda.common.util;

public class ByteUtil {
	//代本机络序
	public static final boolean LOCALSEQIENCE = true;
	//代网络机序
	public static final boolean NETSEQIENCE = false;
	/**
	 * 在字节数组中的指定位置写入一个byte的数据
	 * 
	 * @param buf
	 *            字节数组
	 * @param offset
	 *            偏移量
	 * @param value
	 *            写入的byte值
	 */
	public static void writeByte(byte[] buf, int offset, byte value) {
		buf[offset] = value;
	}

	/**
	 * 从字节数组中读取指定位置的一个byte的数据
	 * 
	 * @param buf
	 *            字节数组
	 * @param offset
	 *            偏移量
	 * @return 返回读取到的byte值
	 */
	public static byte readByte(byte[] buf, int offset) {
		return buf[offset];
	}

	/**
	 * 在字节数组中的指定位置写入一个int类型的数据
	 * 
	 * @param buf
	 *            字节数组
	 * @param offset
	 *            偏移量
	 * @param value
	 *            写入的int值
	 */
	public static void writeInt(byte[] buf, int offset, int value) {
		int temp = value;
		for (int i = offset + 3; i >= offset; i--) {
			buf[i] = new Integer(temp & 0xff).byteValue();
			temp = temp >> 8;
		}
	}

	/**
	 * 从字节数组中读取指定位置的一个int的数据
	 * 
	 * @param buf
	 *            字节数组
	 * @param offset
	 *            偏移量
	 * @return 返回读取到的int值
	 */
	public static int readInt(byte[] buf, int offset) {
		int val = 0;

		val = buf[offset] & 0xff;

		for (int i = 1; i < 4; i++) {
			val |= (((int) buf[offset + i] & 0xff) << (8 * i));
		}

		return val;
	}

	/**
	 * 在字节数组中的指定位置写入一个long类型的数据
	 * 
	 * @param buf
	 *            字节数组
	 * @param offset
	 *            偏移量
	 * @param value
	 *            写入的long值
	 */
	public static void writeLong(byte[] buf, int offset, long value) {
		long temp = value;
		for (int i = offset + 7; i >= offset; i--) {
			buf[i] = new Long(temp & 0xff).byteValue();
			temp = temp >> 8;
		}
	}

	/**
	 * 从字节数组中读取指定位置的一个int的数据
	 * 
	 * @param buf
	 *            字节数组
	 * @param offset
	 *            偏移量
	 * @return 返回读取到的int值
	 */
	public static long readLong(byte[] buf, int offset) {
		long val = 0;

		val = buf[offset];

		for (int i = 1; i < 8; i++) {
			val |= ((long) buf[offset + i] << (8 * (i)));
		}

		return val;
	}

	/**
	 * 在字节数组中的指定位置写入一个byte数组
	 * 
	 * @param buf
	 *            字节数组
	 * @param offset
	 *            偏移量
	 * @param value
	 *            写入的byte数组
	 */
	public static void writeBytes(byte[] buf, int offset, byte[] value) {
		for (int i = 0; i < value.length; i++)
			buf[offset + i] = value[i];
	}

	/**
	 * 将传入的所有参数拼接成为一个整体的byte数组
	 * 
	 * @param items
	 * @return
	 */
	public static byte[] buildBytes(Object... items) {
		byte[] res = null;

		if (items.length > 0) {
			int length = 0;
			for (Object ti : items) {
				if (ti instanceof Byte) {
					length += 1;
				} else if (ti instanceof Integer) {
					length += 4;
				} else if (ti instanceof Long) {
					length += 8;
				} else if (ti instanceof String) {
					byte[] strBytes = ((String) ti).getBytes();
					if (strBytes == null || strBytes.length == 0)
						length += 4;
					else
						length += 4 + strBytes.length;
				}
			}

			res = new byte[length];

			int offset = 0;
			for (Object ti : items) {
				if (ti instanceof Byte) {
					ByteUtil.writeByte(res, offset, ((Byte) ti).byteValue());
					offset += 1;
				} else if (ti instanceof Integer) {
					ByteUtil.writeInt(res, offset, ((Integer) ti).intValue());
					offset += 4;
				} else if (ti instanceof Long) {
					ByteUtil.writeLong(res, offset, ((Long) ti).longValue());
					offset += 8;
				} else if (ti instanceof String) {
					byte[] strBytes = ((String) ti).getBytes();
					if (strBytes == null || strBytes.length == 0) {
						ByteUtil.writeInt(res, offset, 0);
						offset += 4;
					} else {
						ByteUtil.writeInt(res, offset, strBytes.length);
						ByteUtil.writeBytes(res, offset + 4, strBytes);
						offset += 4 + strBytes.length;
					}
				}
			}
		}

		return res;
	}

	public static long bytesToLong(byte[] b) {
		long val = 0;

		val = b[0];

		for (int i = 1; i < b.length; i++) {
			val |= ((long) b[1] << (8 * i));
		}

		return val;
	}

	public static long bytesToLong(byte[] b, int offset, int length) {
		long val = 0;

		val = b[offset];

		for (int i = 1; i < length; i++) {
			val |= ((long) b[offset + i] << (8 * (i)));
		}

		return val;
	}

	public static byte[] longToBytes(long val) {
		byte[] b = new byte[8];

		long temp = val;
		for (int i = b.length - 1; i >= 0; i--) {
			b[i] = new Long(temp & 0xff).byteValue();
			temp = temp >> 8;
		}

		return b;
	}

	public static int bytesToInt(byte[] b, boolean asc) {
		if(asc){//本机序
			int val = 0;
			val = b[0] & 0xff;
			for (int i = 1; i < b.length; i++) {
				val |= (((long) b[i] & 0xff) << (8 * i));
			}
			return val;
		}else{//网络序
			int val = 0;
//			val = b[b.length-1] & 0xff;
			for (int i =  b.length-1; i >= 0; i--) {
				val |= (((long) b[i] & 0xff) << (8 * ( b.length-1-i)));
			}
			return val;
		}
	}

	// public static int bytesToInt1(byte [] b){
	// return b[0] & 0xff |(b[1]&0xff) <<8 |(b[2]&0xff) <<16|(b[3]&0xff) <<24;
	// }
//	2147483648
//	2886758145
	public static void main(String[] args) {
		byte[] b = { 1, 11};
		System.out.println(bytesToInt(b,true));
		byte[] rTypeByte = {64, 61};
		int rTypeInt = ByteUtil.getInt(rTypeByte, true);
		System.out.println("rTypeInt = " + rTypeInt);
		System.out.println("AC106F01 : " + Integer.parseInt("0C106F01", 16));
		System.out.println("AC106F01 : " + Long.parseLong("AC106F01", 16));
//		byte[] bytes = intToBytes(2147483647);
		System.out.println(Integer.toHexString(2147483647));
	}

	public static int bytesToInt(byte[] b, int offset, int length) {
		int val = 0;

		val = b[offset];

		for (int i = 1; i < length; i++) {
			val |= ((long) b[offset + i] << (8 * (i)));
		}

		return val;
	}

	public static byte[] intToBytes(int val) {
		byte[] b = new byte[4];

		int temp = val;
		for (int i = b.length - 1; i >= 0; i--) {
			b[i] = new Integer(temp & 0xff).byteValue();
			temp = temp >> 8;
		}

		return b;
	}

	public final static byte[] getBytes(short s, boolean asc) {
		byte[] buf = new byte[2];
		if (asc)
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (s & 0x00ff);
				s >>= 8;
			}			
		else
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (s & 0x00ff);
				s >>= 8;
			}
		return buf;
	}
	public final static byte getBytes(int s){
		return (byte) (s & 0x00FF);
	}

	public final static void getBytes(int s, boolean asc, byte[] bytes) {
		if (asc){
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte) (s & 0x000000ff);
				s >>= 8;
			}
		}else{
			for (int i = bytes.length - 1; i >= 0; i--) {
				bytes[i] = (byte) (s & 0x000000ff);
				s >>= 8;
			}
		}
	}

	public final static byte[] getBytes(int s, boolean asc) {
		byte[] buf = new byte[4];
		if (asc){
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (s & 0x000000ff);
				s >>= 8;
			}
		}else{
			
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (s & 0x000000ff);
				s >>= 8;
			}
		}
		return buf;
	}

	public final static byte[] getBytes(long s, boolean asc) {
		byte[] buf = new byte[8];
		if (asc){
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (s & 0x00000000000000ff);
				s >>= 8;
			}
		}else{
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (s & 0x00000000000000ff);
				s >>= 8;
			}
		}
		return buf;
	}

	public final static short getShort(byte[] buf, boolean asc) {
		if (buf == null) {
			throw new IllegalArgumentException("byte array is null!");
		}
		if (buf.length > 2) {
			throw new IllegalArgumentException("byte array size > 2 !");
		}
		short r = 0;
		if (asc){
			for (int i = 0; i < buf.length; i++) {
				r <<= 8;
				r |= (buf[i] & 0x00ff);
			}
		}else{
			for (int i = buf.length - 1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x00ff);
			}
		}
		return r;
	}

	public final static int getInt(byte[] buf, boolean asc) {
		if (buf == null) {
			throw new IllegalArgumentException("byte array is null!");
		}
		if (buf.length > 4) {
			throw new IllegalArgumentException("byte array size > 4 !");
		}
		int r = 0;
		if (asc)
			for (int i = 0; i < buf.length; i++) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		else
			for (int i = buf.length - 1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		return r;
	}

	public final static long getLong(byte[] buf, boolean asc) {
		if (buf == null) {
			throw new IllegalArgumentException("byte array is null!");
		}
		if (buf.length > 8) {
			throw new IllegalArgumentException("byte array size > 8 !");
		}
		long r = 0;
		if (asc)
			for (int i = 0; i < buf.length; i++) {
				r <<= 8;
				r |= (buf[i] & 0x00000000000000ff);
			}
		else
			for (int i = buf.length - 1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x00000000000000ff);
			}
		return r;
	}
	/**
	 * 转成网络序
	 * @param bytes
	 * @return
	 */
	public static byte[] toNetSerical(byte[] bytes){
		if(bytes == null || bytes.length == 0)
			return null;
		byte[] netBytes = new byte[bytes.length];
		int i = 0;
		for(int index=bytes.length-1;index>=0; index--, i++){
			netBytes[i] = bytes[index];
		}
		return netBytes;
	}
	/**
	 * 转成本机序
	 * @param bytes
	 * @return
	 */
	public static byte[] toLocalSerical(byte[] bytes){
		if(bytes == null || bytes.length == 0)
			return null;
		byte[] netBytes = new byte[bytes.length];
		int i = netBytes.length;
		for(int index=0;index<bytes.length; index++, i--){
			netBytes[i] = bytes[index];
		}
		return netBytes;
	}
	/**
	 * 将二进制数据转换成二进制字符串
	 * @param bytes
	 * @return
	 */
	public static String toBinaryString(byte[] bytes){
		StringBuffer sb = new StringBuffer();
		for(byte b : bytes){
			 int z = b;
			 z |= 256;
			 String str = Integer.toBinaryString(z);
			 int len = str.length();
			 sb.append(str.substring(len-8, len));
		}
		return sb.toString();
	}
	/**
	 * 将二进制转换成十六进制字符串
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes){
		StringBuffer sb = new StringBuffer();
		for(byte b : bytes){
			 int z = b & 0xFF;
			 String str = Integer.toHexString(z);
			 if(str.length()<2){
				 sb.append("0");
			 }
			 sb.append(str);
		}
		return sb.toString();
	}
	/**
	 * 将十六进制字符串转换成二进制数据
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {  
		if (hexString == null || hexString.equals("")) {  
		  return null;  
		}  
		hexString = hexString.toUpperCase();  
		int length = hexString.length() / 2;  
		char[] hexChars = hexString.toCharArray();  
		byte[] d = new byte[length];  
		for (int i = 0; i < length; i++) {  
			int pos = i * 2;  
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
		}  
		return d;  
	} 
	private static byte charToByte(char c) {  
		return (byte) "0123456789ABCDEF".indexOf(c);  
	}
}
