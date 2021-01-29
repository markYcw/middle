package com.kedacom.middleware.cu;

import java.util.HashMap;
import java.util.Map;

/**
 * “监控平台”连接状态（登录状态）
 * @author TaoPeng
 *
 */
public enum CuSessionStatus {


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
	disconnect(4)
	
	;

	private int value; 
	private CuSessionStatus(int value){
		this.value = value;
	};
	
	public int toInt(){
		return this.value;
	}
	
	private static Map<Integer, CuSessionStatus> maps = null;

	private static void initMap() {
		CuSessionStatus[] values = CuSessionStatus.values();
		maps = new HashMap<Integer, CuSessionStatus>(values.length);
		for (CuSessionStatus type : values) {
			maps.put(type.toInt(), type);
		}
	}
	
	public static CuSessionStatus parse(int value){
		if(maps == null){
			initMap();
		}
		return maps.get(value);
	}
}
