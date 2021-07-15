package com.kedacom.middleware.svr;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.epro.EProSession;
import com.kedacom.middleware.svr.domain.Devinfo;
import com.kedacom.middleware.svr.domain.SVR;
import com.kedacom.middleware.svr.notify.*;
import org.apache.log4j.Logger;

import java.util.List;


/**
 * 会话终端事件监听器
 * 
 * @author TaoPeng
 * 
 */
public class SVRClientListener extends TCPClientListenerAdapter {

	private static final Logger log = Logger.getLogger(SVRClientListener.class);
	
	private SVRClient client;

	public SVRClientListener(SVRClient client) {
		this.client = client;
	}

	@Override
	public void onNotify(INotify notify) {

		int ssid = notify.getSsid();
		SVRSession session = client.getSessionManager().getSessionBySsid(ssid);
		String ip = null;
		if(session != null && session.getSvr() != null){
			ip = session.getSvr().getIp();
		}else{
			log.warn("无效的会话,ssid=" + ssid);
			return;
		}
		
		if (ip == null) {
			log.warn("会话中无有效的SVR信息,ssid=" + ssid);
			return;
		}
		if (notify instanceof LostCntNotify) {
			// 终端掉线
			this.onSVROffine((LostCntNotify) notify);

		} else if (notify instanceof BurnStatusNotify) {
			// 刻录状态通知
			this.onBurnStatus((BurnStatusNotify) notify);
		} else if (notify instanceof DownloadrecntyNotify) {
			this.onDownloadrec((DownloadrecntyNotify) notify);
		}else if(notify instanceof SearchEncoderAndDecoderNotify){
			this.searchEncoderAndDecoder((SearchEncoderAndDecoderNotify) notify);
		}else if(notify instanceof QueryRecNotify) {
			this.queryRec((QueryRecNotify) notify);
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
	 * 全部SVR下线
	 */
	private void onAllOffine(){
		List<SVRSession> sessions = client.getSessionManager().getAllSessions();
		for(SVRSession session : sessions){
			int ssid = session.getSsid();
			client.getSessionManager().removeSession(ssid);
		}
	}

	private void queryRec(QueryRecNotify notify) {
		log.info("---------查询SVR录像----------");
	}


	/**
	 * 终端掉线通知
	 * 
	 * @param
	 * @param notify
	 */
	private void onSVROffine(LostCntNotify notify) {
		int ssid = notify.getSsid();
		SVRSession session = client.getSessionManager().removeSession(ssid);
		if (session != null) {
			session.setStatus(SVRSessionStatus.disconnect);
			SVR svr = session.getSvr();
			if(svr != null){
				//现设备属于业务自己控制链路不需要掉线以后重新连接
				//client.reStartConnect(svr.getIp());
				
				for (SVRNotifyListener l : client.getAllListeners()) {
					l.onSVROffine( svr.getIp());
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
		SVRSession session = client.getSessionManager().getSessionBySsid(ssid);
		if (session != null) {
			for (SVRNotifyListener l : client.getAllListeners()) {
				l.onBurnStauts(String.valueOf(status),ssid);
			}
		}
	}
	
	/**
	 * 刻录进度上报
	 * @param notify
	 */
	private void onDownloadrec(DownloadrecntyNotify notify){
		int ssid = notify.getSsid();
		int downloadhandle = notify.getDownloadhandle();
		int type = notify.getType();
		int pro = notify.getPro();
		SVRSession session = client.getSessionManager().getSessionBySsid(ssid);
		if (session != null) {
			for (SVRNotifyListener l : client.getAllListeners()) {
				l.onDownloadrec(downloadhandle, type, pro);
			}
		}
	}

	/**
	* @Title: searchEncoderAndDecoder 
	* @Description: 搜索编码器和解码器上报 
	* @param @param notify
	* @return void 返回类型
	* @author lzs 
	* @throws
	* @date 2019-8-20 下午4:18:01 
	* @version V1.0
	 */
	private void searchEncoderAndDecoder(SearchEncoderAndDecoderNotify notify) {
		int ssid = notify.getSsid();
		List<Devinfo> list = notify.getList();
		SVRSession session = client.getSessionManager().getSessionBySsid(ssid);
		if (session != null) {
			for (SVRNotifyListener l : client.getAllListeners()) {
				l.searchEncoderAnDecoder(list);
			}
		}
	}
}
