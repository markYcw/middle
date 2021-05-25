package com.kedacom.middleware.vrs;


/**
 * 录播服务器 消息监听器
 * @author LinChaoYu
 *
 */
public interface VRSNotifyListener{
	
	/**
	 * VRS掉线通知
	 * @param vrsId VRS标识
	 * @param vrsIp VRSIP
	 */
	public void onVRSOffine(String vrsId, String vrsIp);

	/**
	 * 录像回放状态上报
	 * @param vrsId VRS标识
	 * @param vrsIp VRSIP
	 * @param playtaskid
	 * @param curplaystate 放像状态 0：无效 1：正常播放 2：暂停 3：单帧播放 4：关键帧播放，当前不支持 5：倒放 6：正常结束 7：异常终止
	 * @param curplayrate 放像速度
	 * @param curplayprog 放像进度
	 */
	public void onPlayStatus(String vrsId, String vrsIp, int playtaskid, int curplaystate, int curplayrate, int curplayprog);
	
}
