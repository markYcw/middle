package com.kedacom.middleware.mcu;

import com.kedacom.middleware.mcu.domain.ConfStatus;
import com.kedacom.middleware.mcu.domain.MTStatus;
import com.kedacom.middleware.mcu.domain.VcrStatus;

import java.util.List;

/**
 * MCU消息监听器
 * @author TaoPeng
 *
 */
public interface McuNotifyListener{

	/**
	 * 平台掉线通知
	 * @param mcuId 会议平台标识
	 * @param mcuIp 会议平台IP
	 */
	public void onMcuOffine(String mcuId, String mcuIp);
	
	/**
	 * 终端状态通知
	 * @param mcuId 会议平台标识
	 * @param status
	 */
	public void onMTStatus(String mcuId, MTStatus status);
	
	/**
	 * 会议基本状态通知
	 * @param mcuId 会议平台标识
	 * @param status
	 */
	public void onConfStatus(String mcuId, ConfStatus status);
	
	/**
	 * 录像机状态通知
	 * @param mcuId 会议平台标识
	 * @param status
	 */
	public void onVcrStatus(String mcuId, VcrStatus status);
	
	/**
	 * 会议列表通知（创会或结会）
	 * @param mcuId 会议平台标识
	 * @param e164s 会议e164集合
	 */
	public void onConfList(String mcuId, List<String> e164s);
}
