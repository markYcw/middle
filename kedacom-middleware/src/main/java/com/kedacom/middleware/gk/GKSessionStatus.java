package com.kedacom.middleware.gk;

import com.kedacom.middleware.DeviceType;

import java.util.HashMap;
import java.util.Map;

/**
 * “GK”连接状态（登录状态）
 * @author LinChaoYu
 *
 */
public enum GKSessionStatus {


	/**
	 * 未连接（未登录）
	 */
	NONE(0),
	/**
	 * 连接中（登录中）
	 */
	CONNECTING(1),
	
	/**
	 * 已连接（已登录）
	 */
	CONNECTED(2),
	
	/**
	 * 连接失败（登录失败）
	 */
	FAILED(3),
	
	/**
	 * 连接中断。一般指异常中断。
	 */
	DISCONNECT(4)
	
	;

	private int value; 
	private GKSessionStatus(int value){
		this.value = value;
	};
	
	public int getValue(){
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
