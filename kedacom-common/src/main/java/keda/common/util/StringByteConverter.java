package keda.common.util;

/**
 * 二进制与字符串互转工具类
 * @author root
 *
 */
public class StringByteConverter {
	
	/**
	 * 二进制转字符串
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}
	public static boolean test(int value){
		int temp = value & 0x01;
		if(temp == 1)
			return true;
		return false;
	}

	/**
	 * 字符串转二进制
	 * @param str
	 * @return
	 */
	public static byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len%2 != 0)
			return null;

		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}
	public static void main(String[] args){
		long t1 = System.currentTimeMillis();
		for(int i=0;i<2000000000;i++){
			boolean isO_1 = test(i);
			boolean isO_2 = (i%2!=0);
			if(isO_1 != isO_2)
				System.out.println("i = " + i + " : " + isO_1);
		}
		System.out.println("0 : " + test(0));
		long t2 = System.currentTimeMillis();
		System.out.println("total time : " + (t2-t1));
	}
}
