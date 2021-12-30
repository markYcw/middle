package com.kedacom.middleware.upu;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.epro.EProSession;
import com.kedacom.middleware.upu.domain.UPU;
import com.kedacom.middleware.upu.notify.LostCntNotify;


import org.apache.log4j.Logger;

import java.util.List;

/**
 * 会话 UPU 事件监听器
 * 
 * @author LinChaoYu
 * @alterby ycw 2021/7/15 17:17
 */
public class UPUClientListener extends TCPClientListenerAdapter {
	private static final Logger log = Logger.getLogger(UPUClientListener.class);
	
	private UPUClient client;

	public UPUClientListener(UPUClient client) {
		this.client = client;
	}

	@Override
	public void onNotify(INotify notify) {

		int ssid = notify.getSsid();
		UPUSession session = client.getSessionManager().getSessionBySsid(ssid);
		String id = null;
		if(session != null){
			id = session.getUpu().getId();
		}else{
			log.warn("无效的会话,ssid=" + ssid);
			return;
		}
		
		if(id == null){
			log.warn("会话中无有效的UPU信息,ssid=" + ssid);
			return;
		}
		
		if (notify instanceof LostCntNotify) {
			//UPU掉线
			this.onUPUOffine((LostCntNotify) notify);
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
	 * 全部UPU下线
	 */
	private void onAllOffine(){
		List<UPUSession> sessions = client.getSessionManager().getAllSessions();
		for(UPUSession session : sessions){
			int ssid = session.getSsid();
			client.getSessionManager().removeSession(ssid);
		}
	}
	
	/**
	 * UPU掉线通知
	 * 
	 * @param notify
	 */
	private void onUPUOffine(LostCntNotify notify) {
		log.error("======> UPU掉线通知(onUPUOffine) ssid="+notify.getSsid()+"，sson="+notify.getSsno());

		int ssid = notify.getSsid();
		UPUSession session = client.getSessionManager().removeSession(ssid);
		if (session != null) {
			session.setStatus(UPUSessionStatus.DISCONNECT);
			UPU upu = session.getUpu();
			if(upu != null){
				
				log.error("======> UPU掉线通知(onUPUOffine) ssid="+notify.getSsid()+"，sson="+notify.getSsno()+"，ip="+upu.getIp()+" 开始重连！");
				//现设备属于业务自己控制链路不需要掉线以后重新连接
				//String id = upu.getId();
				//client.reStartConnect(id);
				
				for (UPUNotifyListener l : client.getAllListeners()) {
					l.onUPUOffine(upu.getId(), upu.getIp());
				}
			}
		}
	}
}
