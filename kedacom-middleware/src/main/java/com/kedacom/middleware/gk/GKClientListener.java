package com.kedacom.middleware.gk;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.epro.EProSession;
import com.kedacom.middleware.gk.domain.GK;
import com.kedacom.middleware.gk.notify.LostCntNotify;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 会话 GK 事件监听器
 * 
 * @author LinChaoYu
 * @alterby ycw 2021/7/15 17:17
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

	@Override
	public void onClosed(TCPClient client) {
		this.onAllOffine();
	}
	@Override
	public void onInterrupt(TCPClient client) {
		this.onAllOffine();
	}

	/**
	 * 全部GK下线
	 */
	private void onAllOffine(){
		List<GKSession> sessions = client.getSessionManager().getAllSessions();
		for(GKSession session : sessions){
			int ssid = session.getSsid();
			client.getSessionManager().removeSession(ssid);
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
