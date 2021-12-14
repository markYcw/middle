package com.kedacom.middleware.cu.devicecache;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.cu.CuClient;
import com.kedacom.middleware.cu.CuNotifyListener;
import com.kedacom.middleware.cu.CuSession;
import com.kedacom.middleware.cu.domain.*;
import com.kedacom.middleware.cu.notify.*;
import com.kedacom.middleware.exception.KMException;
import keda.common.util.ToolsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * 监控设备加载线程
 * @author TaoPeng
 *
 */
public class CuDeviceLoadThread  extends TCPClientListenerAdapter{

	private static final Logger log = LogManager.getLogger(CuDeviceLoadThread.class);
	
	/**
	 * 会话标识
	 */
	private LinkedList<Integer> ssidCache =  new LinkedList<Integer>();
	
	/**
	 * 当前正在加载的ssid
	 */
	private int currSsid = CuSession.INVALID_SSID; //
	
	/**
	 * 未加载设备的分组集合
	 * key：ssid会话ID
	 * value : 分组id
	 */
	private Hashtable<Integer, LinkedList<String>> unKownDeviceGroup = new Hashtable<Integer, LinkedList<String>>(); 
	
	private static int serial = 0;
	private boolean work = true;
	private Object lock = new Object();
	private Thread loadThread;
	private CuClient client;

	private CuDeviceCache cuDeviceCache;
	
	public CuDeviceLoadThread(CuClient client){
		this.client = client;
	}

	/**
	 * 开始加载指定会话的设备
	 * @param ssid
	 * @see #removeSsid(int)
	 */
	public void addSsid(int ssid){
		synchronized (ssidCache) {
			ssidCache.addLast(ssid);
		}
		this.startThread();
	}
	
	/**
	 * 删除会话。
	 * @param ssid
	 * @see #addSsid(int)
	 */
	public void removeSsid(int ssid){
		synchronized (ssidCache) {
			ssidCache.remove(Integer.valueOf(ssid));//注意：这里必须用Integer包装，否则会当数组下标处理
		}
		if(currSsid == ssid){
			currSsid = CuSession.INVALID_SSID;
		}
	}
	
	/**
	 * 唤醒加载设备的线程
	 * @see #stop()
	 */
	private void startThread(){
		if(loadThread == null){
			loadThread = new Thread() {
				
				@Override
				public void run(){
					log.debug("thread start...");
					while(work){
						int cacheSize = 0;
						synchronized (ssidCache) {
							cacheSize = ssidCache.size();
						}
						if(currSsid == CuSession.INVALID_SSID && cacheSize > 0){
							CuDeviceLoadThread.this.doWork();
						}else{
							//无事可做
							synchronized (lock) {
								try {
									lock.wait();
								} catch (InterruptedException e) {
									log.error("lock.wait() failed" , e);
								}
							}
							
							if(!work){
								break;
							}
						}
					}
					log.debug("thread exit...");
				}
			};
			loadThread.setName("Cu-DeviceLoadThread-" + serial ++);
			loadThread.setDaemon(true);
			loadThread.start();
		}else{
			synchronized (lock) {
				lock.notifyAll();
			}
		}
	}
	
	/**
	 * 停止加载设备的线程
	 * @see #startThread()
	 */
	public void stop(){
		if(loadThread != null){
			this.work = false;
			synchronized (lock) {
				lock.notifyAll();
			}
		}
	}
	
	private void doWork(){
		this.loadNextCu();
	}

	/**
	 * 加载下一个监控平台的设备
	 */
	private void loadNextCu(){
		log.debug("curr ssid=" + currSsid);
		if(currSsid != CuSession.INVALID_SSID){
			//当前正有加载currSsid的分组
			log.debug("loadNextCu() currSsid != CuSession.INVALID_SSID return");
			return;
		}

		//加载下一个监控平台的分组
		int nextSsid = CuSession.INVALID_SSID;
		synchronized (ssidCache){
			if(ssidCache.size() > 0){
				nextSsid = ssidCache.pollFirst();
			}
		}
		log.debug("next ssid=" + nextSsid);
		if(nextSsid >= 0){
			this.currSsid = nextSsid;
			try {
				this.requestLoadDeviceGroup(nextSsid);
			} catch (KMException e) {
				log.error("加载指定平台的设备分组失败, ssid=" + nextSsid, e);
				this.loadNextCu(); //忽略失败的，继续加载下一个
			}
		}
	
	}
	
	/**
	 * 指定监控平台设备加载完成
	 * @param ssid
	 * @param success
	 */
	private void onLoadComplete(int ssid, boolean success){
		log.debug("onLoadComplete ssid=" + ssid + ", success=" + success);
		
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		if(success){
			//成功
			unKownDeviceGroup.remove(ssid);
			session.getDeviceCache().setLoadComplete(true);
			this.onDeviceLoadCompate(session.getCu().getId());
		}else{
			//失败。将ssid加进去，等待重新加载
			synchronized (ssidCache){
				ssidCache.addLast(ssid);
			}
			session.getDeviceCache().setLoadComplete(false);
		}

		if(this.currSsid == ssid){
			this.currSsid = CuSession.INVALID_SSID;
		}
		
		//继续加载下一个平台的设备
		this.loadNextCu();
	}
	
	/**
	 * 加载指定监控平台下一个分组的设备
	 * @param ssid
	 */
	private void loadNextDeviceGroup(int ssid){
		
		String groupId = null;
		LinkedList<String> list = unKownDeviceGroup.get(ssid);
		if(list != null){
			synchronized (list) {
				if(list.size() > 0){
					groupId = list.poll();
				}
			}
		}
		
		if(groupId != null){
			try {
				this.requestLoadDevice(ssid, groupId);
			} catch (KMException e) {
				log.debug("获取设备失败 ssid=" + ssid + ", groupId=" + groupId, e);
				loadNextDeviceGroup(ssid);//忽略失败的分组，继续加载下一个分组
			}
		}else{
			//已加载完成
			this.onLoadComplete(ssid, true);
		}
	}
	
	/**
	 * 加载分组。分组以“通知（Notify）”的方式上报。
	 * @throws KMException 
	 */
	private void requestLoadDeviceGroup(int ssid) throws KMException{

		int cuId = client.getSessionManager().getCuIDBySSID(ssid);
		client.getCuOperate().startLoadDeviceGroup(cuId);
	}
	
	/**
	 * 加载指定分组的设备。设备以“通知（Notify）”的方式上报。
	 * @param ssid
	 * @param groupId 分组ID
	 * @throws KMException
	 */
	private void requestLoadDevice(int ssid, String groupId) throws KMException{

		int cuId = client.getSessionManager().getCuIDBySSID(ssid);
		client.getCuOperate().startLoadDevice(cuId, groupId);
	}

	//==========================以上是业务逻辑=============================================
	//==========================以下是数据处理=============================================
	
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
		
		if (notify instanceof GetGroupNotify) {
			//获取分组
			log.info("加载分组信息通知");
			this.onDeviceGroupNotify((GetGroupNotify)notify);
		} else if (notify instanceof GetDeviceNotify) {
			//获取设备
			log.info("加载设备通知，本次加载"+((GetDeviceNotify) notify).getDevices().size()+"个设备");
			this.onDeviceNotify((GetDeviceNotify)notify);
		}else if (notify instanceof DeviceStatusNotify) {
			//设备状态
			log.info("加载设备ID为"+((DeviceStatusNotify) notify).getPuid()+"的设备状态");
			this.onDeviceStatus((DeviceStatusNotify)notify);
		}else if(notify instanceof GetTvWallNotify){
			log.info("加载电视墙");
			this.onTvWallNotify((GetTvWallNotify)notify);
		}else if(notify instanceof GetTvWallSchemeNotify){
			log.info("加载电视墙预案");
			this.onTvWallSchemeNotify((GetTvWallSchemeNotify)notify);
		}
	}

	private LinkedList<String> touchGroupList(int ssid){
		LinkedList<String> list = this.unKownDeviceGroup.get(ssid);
		if(list == null){
			list = new LinkedList<String>();
			this.unKownDeviceGroup.put(ssid, list);
		}
		return list;
	}
	private void addDeviceGroups(int ssid, Collection<PGroup> groups){
		LinkedList<String> list = this.touchGroupList(ssid);
		for(PGroup g : groups){
			synchronized (list) {
				list.push(g.getId());
			}
		}
	}

	/**
	 * 收到通知：分组
	 * @param notify
	 */
	private void onDeviceGroupNotify(GetGroupNotify notify){
		
		int ssid = notify.getSsid();
		boolean isSend = notify.isIsend();
		List<PGroup> groups = notify.getGroup();
		
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		session.getDeviceCache().addDeviceGroups(groups);
		
		this.addDeviceGroups(ssid, groups);
		
		if(isSend){
			//分组完成，开始获取设备
			this.loadNextDeviceGroup(ssid);
		}
	}
	
	/**
	 * 收到通知：设备
	 * @param notify
	 */
	private void onDeviceNotify(GetDeviceNotify notify){
		int ssid = notify.getSsid();
		boolean isSend = notify.isIsend();
		List<PDevice> devices = notify.getDevices();
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		session.getDeviceCache().addDevices(devices);
		
		if(isSend){
			//一个分组下的设备获取完成，获取下一个分组的设备
			loadNextDeviceGroup(ssid);
		}
	}

	private void onTvWallNotify(GetTvWallNotify notify){
		log.info("========加载电视墙========");

	}

	private void onTvWallSchemeNotify(GetTvWallSchemeNotify notify){
		log.info("========加载电视墙预案========");
	}

	/**
	 * 收到设备状态
	 * @param notify
	 */
	private void onDeviceStatus(DeviceStatusNotify notify){
		int ssid = notify.getSsid();
		String puid = notify.getPuid();
		int type = notify.getType();
		switch (type) {
		case DeviceStatusNotify.TYPE_DEVICE_STATUS:
			//设备上下线
			Boolean online = notify.getOnline();
			if(online != null){
				this.onDeviceStatus(ssid, puid, online);
			}
			break;

		case DeviceStatusNotify.TYPE_Channel:
			//视频源（通道）上下线
			List<PChannelStatus> status = notify.getChannelStatusList();
			this.onChannelStatus(ssid, puid, status);
			break;
			
		case DeviceStatusNotify.TYPE_ALARM:
			//报警（告警）
			//TODO 以前从来没用过，暂时不支持
			break;
		case DeviceStatusNotify.TYPE_REC:
			//录像状态
//			Map<String, PChannelStatus> status_map = null;//notify.getStatus_map();
//
//			if(status_map != null && status_map.size() > 0) {
//				this.onRec(ssid,status_map);
//			}

			Map<Integer, PChannelStatus> pChannelStatusMap = notify.getPChannelStatusMap();
			String deviceId = notify.getPuid();
			if (pChannelStatusMap != null && pChannelStatusMap.size() > 0) {
				this.onRec(ssid,deviceId, pChannelStatusMap);
			}

			break;

		case DeviceStatusNotify.TYPE_GPS:
			//GPS
			List<Gps> gpsList = notify.getGpsList();
			if(gpsList != null && gpsList.size() > 0){
				this.onGps(ssid, puid, gpsList);
			}
			break;

		case DeviceStatusNotify.TYPE_DEVICE_IN:
			//设备入网
			PDevice device = notify.getDevice();
			this.onDeviceIN(ssid, device);
			break;

		case DeviceStatusNotify.TYPE_DEVICE_OUT:
			//设备退网
			this.onDeviceOut(ssid, puid);
			break;

		case DeviceStatusNotify.TYPE_DEVICE_UPDATE:
			//设备更新
			this.onDeviceUpdate(ssid, puid);
			break;

		default:
			break;
		}
	}


	//设备状态
	private void onDeviceStatus(int ssid, String puid, Boolean online){
		if(online == null){
			return;
		}
		
		CuDeviceCache deviceCache = client.getSessionManager().getSessionBySSID(ssid).getDeviceCache();
		deviceCache.updateDeviceStatus(puid, online);
		
		for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onDeviceStatus(puid, online);
			}catch(Exception e){
				log.warn("onGps() error", e);
			}
		}
	}
	
	//设备通道状态
	private void onChannelStatus(int ssid, String puid, List<PChannelStatus> status){
		if(status == null || status.size() <= 0){
			return;
		}
		CuDeviceCache deviceCache = client.getSessionManager().getSessionBySSID(ssid).getDeviceCache();
		deviceCache.updateChannelStatus(puid, status);
		
		for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onChannelStatus(puid, status);
			}catch(Exception e){
				log.warn("onGps() error", e);
			}
		}
	}
	
	//GPS
	private void onGps(int ssid, String puid, List<Gps> gpsList){
		if(gpsList == null || gpsList.size() <= 0){
			return;
		}
		
		for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onGps(puid, gpsList);
			}catch(Exception e){
				log.warn("onGps() error", e);
			}
		}
	}
	
	//设备入网
	@SuppressWarnings("deprecation")
	private void onDeviceIN(int ssid, PDevice device){
		log.info("===> onDeviceIN ssid="+ssid+" device="+(device != null ? device.getName():null));
		
		if(device == null)
			return;

		CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
		CuDeviceCache deviceCache = cuSession.getDeviceCache();
		PDevice oldDevice = deviceCache.getDevice(device.getPuid());
		
		// 先删除老的设备信息
		if(oldDevice != null){
			deviceCache.removeDevice(oldDevice.getPuid());
			
			for (CuNotifyListener l : client.getAllListeners()) {
				try{
					l.onDeviceOut(oldDevice.getPuid());// 兼容老的业务系统
					l.onDeviceOut(cuSession.getCu().getId(), oldDevice.getPuid());
				}catch(Exception e){
					log.warn("onDeviceOut() error", e);
				}
			}
		}
		
		// 添加新入会设备信息
		deviceCache.addDevice(device);
		for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onDeviceIn(cuSession.getCu().getId(),device);
			}catch(Exception e){
				log.warn("onDeviceIn() error", e);
			}
		}
	}

	//设备退网
	@SuppressWarnings("deprecation")
	private void onDeviceOut(int ssid ,String puid){
		log.info("===> onDeviceOut ssid="+ssid+" puid="+puid);
		
		if(ToolsUtil.isEmpty(puid))
			return;
		
		CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
		CuDeviceCache deviceCache = cuSession.getDeviceCache();
		PDevice device = deviceCache.getDevice(puid);
		if(device != null){
			deviceCache.removeDevice(puid);
			
			for (CuNotifyListener l : client.getAllListeners()) {
				try{
					l.onDeviceOut(puid);// 兼容老的业务系统
					l.onDeviceOut(cuSession.getCu().getId(), puid);
				}catch(Exception e){
					log.warn("onDeviceOut() error", e);
				}
			}
			
		}
	}
	
	//设备更新
	private void onDeviceUpdate(int ssid ,String puid){
		//TODO 设备更新只能根据设备所在分组加载分组下全部设备，但现在无法确定设备所在分组，所以暂时无法处理。
//		CuDeviceCache deviceCache = client.getSessionManager().getSessionBySsid(ssid).getDeviceCache();
//		PDevice device = deviceCache.getDevice(puid);
//		if(device != null){
//			
//		}
	}
	
	//设备加载完成
	private void onDeviceLoadCompate(int cuId){
		for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onDeviceLoadComplate(cuId);
			}catch(Exception e){
				log.warn("onDeviceLoadCompate() error", e);
			}
		}
	}
	
	//rec（平台录像状态）
//	private void onRec(int ssid,Map<String, PChannelStatus> status_map){
//		log.info("更新设备录像状态"+JSON.toJSONString(status_map));//示例：{"3e4b436b78b3442093d329ac66084685@kedacom115":{"platRecord":false,"puRecord":false,"sn":0}}
//		CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
//		CuDeviceCache deviceCache = cuSession.getDeviceCache();
//		Map<String,PChannelStatus> map = deviceCache.getStatus_map();//存放各puid设备录像状态的缓存map
//		for (Map.Entry<String, PChannelStatus> entry : status_map.entrySet()) {
//			 log.info("更新key= " + entry.getKey() + " and PChannelStatus={sn=" + entry.getValue().getSn()+",plat="+entry.getValue().getPlatRecord()+",pu="+entry.getValue().getPuRecord()+"}");
//			 map.put(entry.getKey(),entry.getValue());//更新map
//	    }
//		log.info("CuDeviceCache中缓存的各设备合成通道录像状态为："+JSON.toJSONString(map));
//	}

	private void onRec(int ssid, String deviceId, Map<Integer, PChannelStatus> pChannelStatusMap) {
		log.info("设备通道状态变更通知:ssid=" + ssid + ",puId=" + deviceId + ",pChannelStatusMap=" + pChannelStatusMap);
		CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
		CuDeviceCache deviceCache = cuSession.getDeviceCache();
		PDevice device = deviceCache.getDevice(deviceId);
		if (device == null) {
			return;
		}
		for (Map.Entry<Integer, PChannelStatus> entry : pChannelStatusMap.entrySet()) {
			Integer snno = entry.getKey();
			PChannelStatus channelStatus = entry.getValue();
			PChannel channel = device.getChannel(snno);
			if (channel == null || channelStatus == null) {
				continue;
			}
			if (channelStatus.getPlatRecord() != null) {
				channel.setPlatRecord(channelStatus.getPlatRecord());
			}

			if (channelStatus.getPuRecord() != null) {
				channel.setPuRecord(channelStatus.getPuRecord());
			}
		}

	}

}
