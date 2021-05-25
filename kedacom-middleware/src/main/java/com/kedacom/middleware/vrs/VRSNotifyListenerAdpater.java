package com.kedacom.middleware.vrs;


/**
 * 录播服务器 事件监听 适配器
 * @author LinChaoYu
 *
 */
public class VRSNotifyListenerAdpater implements VRSNotifyListener {

	@Override
	public void onVRSOffine(String vrsId, String vrsIp) {

	}

	@Override
	public void onPlayStatus(String vrsId, String vrsIp, int playtaskid, int curplaystate, int curplayrate, int curplayprog) {

	}
}
