package com.kedacom.middleware.vrs;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.epro.EProSession;
import com.kedacom.middleware.vrs.domain.VRS;
import com.kedacom.middleware.vrs.notify.LostCntNotify;
import com.kedacom.middleware.vrs.notify.PlayStatusNotify;
import org.apache.log4j.Logger;

import java.util.List;

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
		} else if(notify instanceof PlayStatusNotify){
			//录像回放状态上报
			this.onPlayStatus((PlayStatusNotify) notify);
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
	 * 全部VRS下线
	 */
	private void onAllOffine(){
		List<VRSSession> sessions = client.getSessionManager().getAllSessions();
		for(VRSSession session : sessions){
			int ssid = session.getSsid();
			client.getSessionManager().removeSession(ssid);
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
				//现设备属于业务自己控制链路不需要掉线以后重新连接
				//String id = vrs.getId();
				//client.reStartConnect(id);
				
				for (VRSNotifyListener l : client.getAllListeners()) {
					l.onVRSOffine(vrs.getId(), vrs.getIp());
				}
			}
		}
	}

	/**
	 * VRS录像回放状态上报
	 *
	 * @param notify
	 */
	private void onPlayStatus(PlayStatusNotify notify) {
		log.error("======>VRS录像回放状态上报(PlayStatusNotify) ssid="+notify.getSsid()+"，sson="+notify.getSsno());

		int ssid = notify.getSsid();
		int playtaskid = notify.getPlaytaskid();
		int curplaystate = notify.getCurplaystate();
		int curplayrate = notify.getCurplayrate();
		int curplayprog = notify.getCurplayprog();
		VRSSession session = client.getSessionManager().removeSession(ssid);
		if (session != null) {
			session.setStatus(VRSSessionStatus.DISCONNECT);
			VRS vrs = session.getVrs();
			if(vrs != null){


				for (VRSNotifyListener l : client.getAllListeners()) {
					l.onPlayStatus(vrs.getId(), vrs.getIp(),playtaskid,curplaystate,curplayrate,curplayprog);
				}
			}
		}
	}
}
