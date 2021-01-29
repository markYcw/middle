package com.kedacom.middleware.mt;


/**
 * MT消息监听器
 * @author TaoPeng
 *
 */
public interface MTNotifyListener{

	/**
	 * 终端掉线通知
	 * @param mtId 会议终端标识
	 * @param mcuIp 会议终端IP
	 */
	public void onMtOffine(String mtId, String mtIp);
	
	/**
	 * 终端抢占通知
	 * @param mtId 会议终端标识
	 * @param status
	 */
	public void onMTRobbed(String mtId, String mtIp);
	
	/**
	 * 刻录机刻录进度通知
	 * @param recIp
	 */
	public void onBurnStauts(String mtId, String mtIp, int ssid);
	
	/**
	 * 停止点对点会议通知
	 * @param mtId
	 * @param mtIp
	 */
	public void onStopP2P(String mtId, String mtIp, int ssid);
	
	/**
	 * 信令透传通知
	 * @param mtId
	 * @param mtIp
	 */
	public void transmitInfo(String mtId, String mtIp, int ssid, String msg, MTClient client);
	
}
