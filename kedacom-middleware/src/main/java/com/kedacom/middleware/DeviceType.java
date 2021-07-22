package com.kedacom.middleware;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过中件间可以登录的设备类型，包括：监控平台、会议平台、会议终端；
 * @author TaoPeng
 *
 */
public enum DeviceType {

	/**
	 * 监控平台
	 */
	CU(0),
	/**
	 * 会议平台4.7
	 */
	MCU(1),
	
	/**
	 * 会议终端3代高清
	 */
	MT(2),
	
	/**
	 * 会议平台5.0
	 */
	MCU5(4),
	
	/**
	 * GK服务器
	 */
	GK(5),

	/**
	 * 监控平台2.0
	 */
	CU2(7),
	
	/**
	 * 存储柜
	 */
	CUPBOARD(8),
	/**
	 * Svr
	 */
	SVR(10),

	/**
	 * SVR_2931
	 */
	SVR_NVRV7(30),
	
	/**
	 * 会议终端5.0
	 */
	MT5(11),
	
	/**
	 * 录播服务器5.0
	 */
	VRS50(14),
	
	/**
	 * 录播服务器5.1
	 */
	VRS51(15),

	/**
	 * 硬盘刻录机SVR-2kb
	 */
	VRS2000B(18),
	
	/**
	 * 人脸对比服务
	 */
	FACE(20),

	/**
	 * RK100
	 */
	RK100(22),
	
	/**
	 * UPU
	 */
	UPU(23),
	
	/**
	 * NVR设备
	 */
	NVR(19),
	
	/**
	 * 软终端（科达天行）
	 */
	SKY(25),

	/**
	 * 签名板PRO
	 */
	E10PRO(31),
	;

	private int value; 
	private DeviceType(int value){
		this.value = value;
	};
	
	public int getValue(){
		return this.toInt();
	}
	
	public int toInt(){
		return this.value;
	}
	
	private static Map<Integer, DeviceType> maps = null;

	private static void initMap() {
		DeviceType[] values = DeviceType.values();
		maps = new HashMap<Integer, DeviceType>(values.length);
		for (DeviceType type : values) {
			maps.put(type.getValue(), type);
		}
	}
	
	public static DeviceType parse(int value){
		if(maps == null){
			initMap();
		}
		return maps.get(value);
	}
}
