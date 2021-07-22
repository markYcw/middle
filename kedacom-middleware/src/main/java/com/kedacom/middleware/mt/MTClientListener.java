package com.kedacom.middleware.mt;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.epro.EProSession;
import com.kedacom.middleware.mt.domain.MT;
import com.kedacom.middleware.mt.notify.*;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 会话终端事件监听器
 * 
 * @author TaoPeng
 * @alterby ycw 2021/7/15 17:17
 */
public class MTClientListener extends TCPClientListenerAdapter {

	private static final Logger log = Logger.getLogger(MTClientListener.class);
	
	private MTClient client;

	public MTClientListener(MTClient client) {
		this.client = client;
	}

	@Override
	public void onNotify(INotify notify) {

		int ssid = notify.getSsid();
		MTSession session = client.getSessionManager().getSessionBySsid(ssid);
		String id = null;
		if(session != null && session.getMt() != null){
			if(session.getMt().getVrsType() == 2){//刻录处理
				id = "0";
			}else{
				id = session.getMt().getId();
			}
		}else{
			log.warn("无效的会话,ssid=" + ssid);
			return;
		}
		
		if(id == null){
			log.warn("会话中无有效的终端信息,ssid=" + ssid);
			return;
		}
		
		
		if (notify instanceof LostCntNotify) {
			//终端掉线
			this.onMtOffine((LostCntNotify) notify);
			
		} else if (notify instanceof MTRobbedNotify) {
			//终端抢占通知
			this.onMTRobbed(id, (MTRobbedNotify) notify);
			
		} else if(notify instanceof BurnStatusNotify){
			//刻录状态通知
			this.onBurnStatus((BurnStatusNotify) notify);
			
		} else if(notify instanceof StopP2PNotify){
			//结束点对点通知
			this.onStopP2P((StopP2PNotify) notify);
			
		}else if(notify instanceof TransmitInfoNotify){
			//信息透传通知
			this.transmitInfo((TransmitInfoNotify) notify);
			
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
	 * 全部MT下线
	 */
	private void onAllOffine(){
		List<MTSession> sessions = client.getSessionManager().getAllSessions();
		for(MTSession session : sessions){
			int ssid = session.getSsid();
			client.getSessionManager().removeSession(ssid);
		}
	}

	/**
	 * 终端掉线通知
	 * @param notify
	 */
	private void onMtOffine(LostCntNotify notify) {
		int ssid = notify.getSsid();
		MTSession session = client.getSessionManager().removeSession(ssid);
		if (session != null) {
			session.setStatus(MTSessionStatus.disconnect);
			MT mt = session.getMt();
			if(mt != null){
				//现设备属于业务自己控制链路不需要掉线以后重新连接
				//client.reStartConnect(mt.getId());
				
				for (MTNotifyListener l : client.getAllListeners()) {
					l.onMtOffine(mt.getId(), mt.getIp());
				}
			}
		}
	}
	/**
	 * 终端抢占通知
	 *
	 * @param mcuId
	 * @param notify
	 */
	private void onMTRobbed(String mcuId, MTRobbedNotify notify) {
		int ssid = notify.getSsid();
		MTSession session = client.getSessionManager().removeSession(ssid);
		if (session != null) {
			MT mt = session.getMt();
			if(mt != null){
				client.removeMTByID(mt.getId());
				
				for (MTNotifyListener l : client.getAllListeners()) {
					l.onMTRobbed(mt.getId(), mt.getIp());
				}
			}
		}
	}

	/**
	 * 刻录进度上报
	 * @param notify
	 */
	private void onBurnStatus(BurnStatusNotify notify){
		int ssid = notify.getSsid();
		int status = notify.getBurnrate();
		MTSession session = client.getSessionManager().getSessionBySsid(ssid);
		if (session != null) {
			for (MTNotifyListener l : client.getAllListeners()) {
				l.onBurnStauts("",String.valueOf(status),ssid);
			}
		}
	}
	
	/**
	 * 停止点对点会议上报
	 * @param notify
	 */
	private void onStopP2P(StopP2PNotify notify){
		int ssid = notify.getSsid();
		MTSession session = client.getSessionManager().getSessionBySsid(ssid);
		if (session != null) {
			MT mt = session.getMt();
			if(mt != null){
				for (MTNotifyListener l : client.getAllListeners()) {
					l.onStopP2P(mt.getId(), mt.getIp(),ssid);
				}
			}
		}
	}
	
	/**
	 * 信令透传通知
	 * 
	 * @param notify
	 */
	private void transmitInfo(TransmitInfoNotify notify) {
		int ssid = notify.getSsid();
		String msg = notify.getMsg();
		MTSession session = client.getSessionManager().getSessionBySsid(ssid);
		if (session != null) {
			MT mt = session.getMt();
			if(mt != null){		
				for (MTNotifyListener l : client.getAllListeners()) {
					l.transmitInfo(mt.getId(), mt.getIp(), ssid, msg, client);
				}
			}
		}
	}
}
