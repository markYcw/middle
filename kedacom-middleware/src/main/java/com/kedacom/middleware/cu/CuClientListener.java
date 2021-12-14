package com.kedacom.middleware.cu;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.cu.domain.Cu;
import com.kedacom.middleware.cu.notify.DeviceStatusNotify;
import com.kedacom.middleware.cu.notify.GetDeviceNotify;
import com.kedacom.middleware.cu.notify.GetGroupNotify;
import com.kedacom.middleware.cu.notify.LostCnntNotify;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CuClientListener extends TCPClientListenerAdapter {

	private static final Logger log = LogManager.getLogger(CuClientListener.class);
	
	private CuClient client;

	public CuClientListener(CuClient client) {
		this.client = client;
	}
	@Override
	public void onNotify(INotify notify) {

		int ssid = notify.getSsid();
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		int id = 0;
		if(session != null){
			id = session.getCu().getId();
		}else{
			log.warn("无效的会话,ssid=" + ssid);
			return;
		}
		
		if(id <= 0){
			log.warn("会话中无有效的监控平台信息,ssid=" + ssid);
			return;
		}
		
		if (notify instanceof LostCnntNotify) {
			//监控平台掉线
			this.onCuOffine((LostCnntNotify) notify);
			
		} else if (notify instanceof GetGroupNotify) {
			//获取分组。这里不需要处理，由CuDeviceLoadThread处理
			
		}else if (notify instanceof GetDeviceNotify) {
			//获取设备。这里不需要处理，由CuDeviceLoadThread处理
			
		}else if (notify instanceof DeviceStatusNotify) {
			//设备状态。这里不需要处理，由CuDeviceLoadThread处理
			
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
	 * 指定监控平台掉线通知
	 * @param notify
	 */
	private void onCuOffine(LostCnntNotify notify) {
		log.info("======>监控平台掉线通知( onCuOffine) ssid="+notify.getSsid()+" ;sson="+notify.getSsno());
		int ssid = notify.getSsid();
		this.onCuOffline(ssid);
		
	}
	
	/**
	 * 全部监控平台下线
	 */
	private void onAllOffine(){
		List<CuSession> sessions = client.getSessionManager().getAllSessions();
		for(CuSession session : sessions){
			this.onCuOffline(session.getSsid());
		}
	}
	
	private void onCuOffline(int ssid){
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		if (session != null) {
			session.setStatus(CuSessionStatus.disconnect);
			Cu cu = session.getCu();
			if (cu != null) {
				int id = cu.getId();
				client.reStartConnect(id);
				
				for (CuNotifyListener l : client.getAllListeners()) {
					try{
						l.onCuOffine(cu.getId());
					}catch(Exception e){
						log.warn(e.getMessage(), e);
					}
				}
			}
		}
	}
}
