package com.kedacom.middleware.gk;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.gk.domain.GK;
import com.kedacom.middleware.gk.notify.LostCntNotify;
import org.apache.log4j.Logger;

/**
 * 会话 GK 事件监听器
 * 
 * @author LinChaoYu
 * 
 */
public class GKClientListener extends TCPClientListenerAdapter {

	private static final Logger log = Logger.getLogger(GKClientListener.class);
	
	private GKClient client;

	public GKClientListener(GKClient client) {
		this.client = client;
	}

	@Override
	public void onNotify(INotify notify) {

		int ssid = notify.getSsid();
		GKSession session = client.getSessionManager().getSessionBySsid(ssid);
		String id = null;
		if(session != null){
			id = session.getGk().getId();
		}else{
			log.warn("无效的会话,ssid=" + ssid);
			return;
		}
		
		if(id == null){
			log.warn("会话中无有效的GK信息,ssid=" + ssid);
			return;
		}
		
		if (notify instanceof LostCntNotify) {
			//GK掉线
			this.onGKOffine((LostCntNotify) notify);
			
		} 

	}
	
	/**
	 * GK掉线通知
	 * 
	 * @param notify
	 */
	private void onGKOffine(LostCntNotify notify) {
		log.error("======>GK掉线通知(onGKOffine) ssid="+notify.getSsid()+"，sson="+notify.getSsno());

		int ssid = notify.getSsid();
		GKSession session = client.getSessionManager().removeSession(ssid);
		if (session != null) {
			session.setStatus(GKSessionStatus.DISCONNECT);
			GK gk = session.getGk();
			if(gk != null){
				
				log.error("======>GK掉线通知(onGKOffine) ssid="+notify.getSsid()+"，sson="+notify.getSsno()+"，ip="+gk.getIp()+" 开始重连！");
				
				String id = gk.getId();
				client.reStartConnect(id);
				
				for (GKNotifyListener l : client.getAllListeners()) {
					l.onGKOffine(gk.getId(), gk.getIp());
				}
			}
		}
	}
}
