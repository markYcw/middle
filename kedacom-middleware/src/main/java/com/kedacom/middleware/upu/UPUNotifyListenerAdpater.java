package com.kedacom.middleware.upu;

/**
 * UPU 事件监听 适配器
 * @author LinChaoYu
 *
 */
public class UPUNotifyListenerAdpater implements UPUNotifyListener {

	/**
	 * UPU掉线通知
	 * @param upuId UPU的标识（数据库ID）
	 * @param upuIp UPU的IP
	 */
	public void onUPUOffine(String upuId, String upuIp) {

	}
}
