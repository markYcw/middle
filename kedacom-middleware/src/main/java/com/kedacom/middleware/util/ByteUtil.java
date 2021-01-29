package com.kedacom.middleware.util;

/**
 * 字节序列转换工具。
 * <pre>主要提供字节序列的大端模式和小端模式的转换。
 *     大端模式（左->右）：整数5的四字节序列是（0x 00 00 00 05），数据高字节放在（内存/网络流）低地址；<br>
 *     小端模式（右->左）：整数5的四字节序列是（0x 05 00 00 00），数据高字节放在（内存/网络流）高地址；
 * </pre>
 * @author TaoPeng
 *
 */
public class ByteUtil {
	/**
	 * 大端序列。整数5的四字节序列是（0x 00 00 00 05），数据高字节放在（内存/网络流）低地址；
	 */
	public static final boolean BigEndian = true;
	
	/**
	 * 小端序列。整数5的四字节序列是（0x 05 00 00 00），数据高字节放在（内存/网络流）高地址；
	 */
	public static final boolean LittleEndian = false;
	

	/**
	 * 将短整数data转换成2字节序列
	 * @param data 短整数值 
	 * @param endian 大端模式|小端模式
	 * @return
	 */
	public final static byte[] getBytes4Short(short data, Endian endian) {
		return getBytes(data, endian, 2);
	}
	
	/**
	 * 将整数data转换成1个字节。
	 * @param data
	 * @return
	 */
	public final static byte getByte(int data){
		return (byte) (data & 0x00FF);
	}

	/**
	 * 将整数data转为4字节序列。<br>
	 *     大端模式（左->右）：整数5的四字节序列是（0x 00 00 00 05），数据高字节放在（内存/网络流）低地址；<br>
	 *     小端模式（右->左）：整数5的四字节序列是（0x 05 00 00 00），数据高字节放在（内存/网络流）高地址；
	 * @param data 数值
	 * @param endian 大端模式|小端模式
	 * @return
	 */
	public final static byte[] getBytes4Int(int data, Endian endian) {
		return getBytes(data, endian, 4);

	}
	
	/**
	 * 将data转换为8字节序列。
	 * @param data
	 * @param endian 大端模式|小端模式
	 * @return
	 */
	public final static byte[] getBytes4Long(long data, Endian endian) {
		return getBytes(data, endian, 8);
	}
	/**
	 * 将数值data转换成长度为length的字节序列。<br/>
	 * <pre>
	 * <b>注意</b>：如果data的字节长度大于length，多余的字节会被舍去。
	 *     大端模式（左->右）：整数5的四字节序列是（0x 00 00 00 05），数据高字节放在（内存/网络流）低地址；
	 *     小端模式（右->左）：整数5的四字节序列是（0x 05 00 00 00），数据高字节放在（内存/网络流）高地址；
	 * </pre>
	 * @param data 数值，实际类型可以是short、int、long
	 * @param endian 大端模式|小端模式
	 * @param length 字节序列长度
	 * @return 返回长为length的字节序列
	 */
	public final static byte[] getBytes(long data, Endian endian, int length ){
		byte[] buf = new byte[length];
		if (endian == Endian.LittleEndian){
			//小端模式
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (data & 0x00000000000000ff);
				data >>= 8;
			}
		}else{
			//大端模式
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (data & 0x00000000000000ff);
				data >>= 8;
			}
		}
		return buf;
	}

	public static void main(String[] args) {

		String str = "0x01020304";
		int i = 0x01020304;
		System.out.println("整数" + str + "转字节序（大端模式）");
		byte[] bytes = ByteUtil.getBytes4Int(i, Endian.BigEndian);
		out(bytes);
		
		System.out.println("整数" + str + "转字节序（小端模式）");
		bytes = ByteUtil.getBytes4Int(i, Endian.LittleEndian);
		out(bytes);

		System.out.println("将整数" + str + "转换成8字节（大端模式）");
		bytes = ByteUtil.getBytes(i, Endian.BigEndian, 8);
		out(bytes);

		System.out.println("将整数" + str + "转换成8字节（小端模式）");
		bytes = ByteUtil.getBytes(i, Endian.LittleEndian, 8);
		out(bytes);
		

		System.out.println("ByteUtil.getBytes0(0x0102, Endian.LittleEndian, 1);");
		bytes = ByteUtil.getBytes(i, Endian.LittleEndian, 1);
		out(bytes);
		
		byte x = ByteUtil.getByte(0x01020304);
		System.out.println("x=" + x);
		
	}
	
	private static void out(byte[] bytes){
		for(byte b : bytes){
			System.out.print(b);
			System.out.print(" ");
		}
		System.out.println("\n\n----------------------------");
	}
}
