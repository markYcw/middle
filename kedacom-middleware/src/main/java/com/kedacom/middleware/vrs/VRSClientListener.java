package com.kedacom.middleware.vrs;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.vrs.domain.VRS;
import com.kedacom.middleware.vrs.notify.LostCntNotify;
import org.apache.log4j.Logger;

/**
 * 会话 录播服务器 事件监听器
 * 
 * @author LinChaoYu
 * 
 */
public class VRSClientListener extends TCPClientListenerAdapter {

	private static final Logger log = Logger.getLogger(VRSClientListener.class);
	
	private VRSClient client;

	public VRSClientListener(VRSClient client) {
		this.client = client;
	}

	@Override
	public void onNotify(INotify notify) {

		int ssid = notify.getSsid();
		VRSSession session = client.getSessionManager().getSessionBySsid(ssid);
		String id = null;
		if(session != null){
			id = session.getVrs().getId();
		}else{
			log.warn("无效的会话,ssid=" + ssid);
			return;
		}
		
		if(id == null){
			log.warn("会话中无有效的VRS信息,ssid=" + ssid);
			return;
		}
		
		if (notify instanceof LostCntNotify) {
			//VRS掉线
			this.onVRSOffine((LostCntNotify) notify);
			
		} 

	}
	
	/**
	 * VRS掉线通知
	 * 
	 * @param notify
	 */
	private void onVRSOffine(LostCntNotify notify) {
		log.error("======>VRS掉线通知(onVRSOffine) ssid="+notify.getSsid()+"，sson="+notify.getSsno());

		int ssid = notify.getSsid();
		VRSSession session = client.getSessionManager().removeSession(ssid);
		if (session != null) {
			session.setStatus(VRSSessionStatus.DISCONNECT);
			VRS vrs = session.getVrs();
			if(vrs != null){
				
				log.error("======>VRS掉线通知(onVRSOffine) ssid="+notify.getSsid()+"，sson="+notify.getSsno()+"，ip="+vrs.getIp()+" 开始重连！");
				
				String id = vrs.getId();
				client.reStartConnect(id);
				
				for (VRSNotifyListener l : client.getAllListeners()) {
					l.onVRSOffine(vrs.getId(), vrs.getIp());
				}
			}
		}
	}
}
