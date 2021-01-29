package com.kedacom.middleware.util;

/**
 * 字节序。
 * @author TaoPeng
 *
 */
public enum Endian {
	
	/**
	 *  大端序列。整数5的四字节序列是（0x 00 00 00 05），数据高字节放在（内存/网络流）低地址；
	 */
	BigEndian,
	
	/**
	 * 小端序列。整数5的四字节序列是（0x 05 00 00 00），数据高字节放在（内存/网络流）高地址；
	 */
	LittleEndian
}
