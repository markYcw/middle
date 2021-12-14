package com.kedacom.middleware.mcu;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.cu.CuNotifyListener;
import com.kedacom.middleware.cu.CuSession;
import com.kedacom.middleware.cu.CuSessionStatus;
import com.kedacom.middleware.cu.domain.Cu;
import com.kedacom.middleware.cu.notify.LostCnntNotify;
import com.kedacom.middleware.mcu.domain.ConfStatus;
import com.kedacom.middleware.mcu.domain.MTStatus;
import com.kedacom.middleware.mcu.domain.Mcu;
import com.kedacom.middleware.mcu.domain.VcrStatus;
import com.kedacom.middleware.mcu.notify.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * 会话平台事件监听器
 * 
 * @author TaoPeng
 * @alter by ycw 2021/7/15 16:38
 * 
 */
public class McuClientListener extends TCPClientListenerAdapter {

	private static final Logger log = LogManager.getLogger(McuClientListener.class);
	
	private McuClient client;

	public McuClientListener(McuClient client) {
		this.client = client;
	}

	@Override
	public void onNotify(INotify notify) {

		int ssid = notify.getSsid();
		McuSession session = client.getSessionManager().getSessionBySsid(ssid);
		String id = null;
		if(session != null){
			id = session.getMcu().getId();
		}else{
			log.warn("无效的会话,ssid=" + ssid);
			return;
		}
		
		if(id == null){
			log.warn("会话中无有效的会议平台信息,ssid=" + ssid);
			return;
		}
		
		
		if (notify instanceof LostCntNotify) {
			//会议平台掉线
			this.onMcuOffine((LostCntNotify) notify);
			
		} else if (notify instanceof MTStatusNotify) {
			//会议终端状态
			this.onMTStatus(id, (MTStatusNotify) notify);
			
		} else if (notify instanceof ConfStatusNotify) {
			//会议基本状态
			this.onConfStatus(id, (ConfStatusNotify) notify);
			
		} else if (notify instanceof VcrStatusNotify) {
			//会议录像机状态
			this.onVcrStatus(id, (VcrStatusNotify) notify);
			
		} else if (notify instanceof ConfListNotify) {
			//会议列表通知（会议列表发生变更时）
			this.onConfList(id, (ConfListNotify) notify);
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
	 * 全部视讯平台下线
	 */
	private void onAllOffine(){
		List<McuSession> sessions = client.getSessionManager().getAllSessions();
		for(McuSession session : sessions){
			this.onMCuOffline(session.getSsid());
		}
	}

	private void onMCuOffline(int ssid){
		McuSession session = client.getSessionManager().getSessionBySsid(ssid);
		if (session != null) {
			session.setStatus(McuSessionStatus.disconnect);
			Mcu cu = session.getMcu();
			if (cu != null) {
				String id = cu.getId();
				String ip = cu.getIp();
				client.reStartConnect(id);

				for (McuNotifyListener l : client.getAllListeners()) {
					try{
						l.onMcuOffine(id,ip);
					}catch(Exception e){
						log.warn(e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * 平台掉线通知
	 * 
	 * @param notify
	 * @param notify
	 */
	private void onMcuOffine(LostCntNotify notify) {
		log.error("======>平台掉线通知(onMcuOffine) ssid="+notify.getSsid()+"，sson="+notify.getSsno());
		
		int ssid = notify.getSsid();
		McuSession session = client.getSessionManager().getSessionBySsid(ssid);
		if (session != null) {
			session.setStatus(McuSessionStatus.disconnect);
			Mcu mcu = session.getMcu();
			if (mcu != null) {
				
				log.error("======>平台掉线通知(onMcuOffine) ssid="+notify.getSsid()+"，sson="+notify.getSsno()+"，ip="+mcu.getIp()+" 开始重连！");
				
				String id = mcu.getId();
				client.reStartConnect(id);
				
				for (McuNotifyListener l : client.getAllListeners()) {
					l.onMcuOffine(id, mcu.getIp());
				}
			}
		}
	}
	
	/**
	 * 终端状态通知
	 * 
	 * @param mcuId
	 * @param notify
	 */
	private void onMTStatus(String mcuId, MTStatusNotify notify) {
		MTStatus status = notify.getMtStatus();
		for (McuNotifyListener l : client.getAllListeners()) {
			l.onMTStatus(mcuId, status);
		}
	}

	/**
	 * 会议基本状态通知
	 * 
	 * @param mcuId
	 * @param notify
	 */
	private void onConfStatus(String mcuId, ConfStatusNotify notify) {
		ConfStatus status = notify.getConfStatus();
		for (McuNotifyListener l : client.getAllListeners()) {
			l.onConfStatus(mcuId, status);
		}
	}

	/**
	 * 录像机状态通知
	 * 
	 * @param mcuId
	 * @param notify
	 */
	private void onVcrStatus(String mcuId, VcrStatusNotify notify) {
		VcrStatus status = notify.getVcrStatus();
		for (McuNotifyListener l : client.getAllListeners()) {
			l.onVcrStatus(mcuId, status);
		}
	}
	
	/**
	 * 会议列表通知（创会或结会）
	 * @param mcuId
	 * @param notify
	 */
	private void onConfList(String mcuId, ConfListNotify notify) {
		List<String> e164s = notify.getConfe164s();
		for (McuNotifyListener l : client.getAllListeners()) {
			l.onConfList(mcuId, e164s);
		}
	}
}
