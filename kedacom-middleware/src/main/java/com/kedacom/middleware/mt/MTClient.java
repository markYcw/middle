package com.kedacom.middleware.mt;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import com.kedacom.middleware.mt.domain.*;
import com.kedacom.middleware.mt.request.*;
import com.kedacom.middleware.mt.response.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * “会议终端”接口访问。
 * @author TaoPeng
 *
 */
public class MTClient {
	private static final Logger log = Logger.getLogger(MTClient.class);
	private KM km;

	/**
	 * 会话管理。根据目前的设计，一个终端最多一个会话，好比一个监控平台只有一个主链。
	 */
	
	private MTSessionManager sessionManager;
	/**
	 * “终端”信息集合。
	 * key：终端标识， value:终端详细信息
	 */
	private Hashtable<String, MT> mtCacheByID = new Hashtable<String, MT>();
	/**
	 * 服务器数据和状态监听器
	 */
	private List<MTNotifyListener> listeners = new ArrayList<MTNotifyListener>();

	/**
	 * Mt连接监控线程
	 */
	private MtConnMonitorThread mtConnMonitorThread;
	
	public MTClient(KM km){
		this.km = km;

		this.sessionManager = new MTSessionManager(this);
		MTClientListener listener = new MTClientListener(this);
		
		/*
		 * TODO 如果当MTClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
		 * 但由于实际应用中MTClient只有一个，所以此处暂不处理
		 */
		TCPClient tcpClient = (TCPClient)km.getClient();
		tcpClient.addListener(listener);
		
	}

	/**
	 * 增加监听器
	 * @see #removeListener(MTNotifyListener)
	 * @param listener
	 * @return
	 */
	public boolean addListener(MTNotifyListener listener){
		return this.listeners.add(listener);
	}
	
	/**
	 * 删除监听器
	 * @param listener
	 * @see #addListener(MTNotifyListener)
	 * @return
	 */
	public boolean removeListener(MTNotifyListener listener){
		return this.listeners.remove(listener);
	}
	
	protected List<MTNotifyListener> getAllListeners(){
		List<MTNotifyListener> list = new ArrayList<MTNotifyListener>(this.listeners.size());
		list.addAll(listeners);
		return list;
	}
	
	/**
	 * 获取会话管理器
	 * @return
	 */
	public MTSessionManager getSessionManager() {
		return sessionManager;
	}
	
	/**
	 * 设置（添加或修改）终端信息
	 * @param mcu
	 */
	public void setMT(MT mt){
		MT old = this.mtCacheByID.get(mt.getId());
		if(old != null){
			updateMT(mt, true);
		}else{
			addMT(mt, true);
		}
	}
	
	/**
	 * 增加终端信息。
	 * 默认不开启终端连接（适配老接口）
	 * @param mt
	 */
	public void addMT(MT mt){
		addMT(mt, false);
	}
	
	/**
	 * 增加终端信息。
	 * @param mt
	 * @param isReStart 标识是否立即连接终端
	 */
	public void addMT(MT mt,boolean isReStart){
		this.mtCacheByID.put(mt.getId(), mt);
		if(isReStart)
			this.reStartConnect(mt.getId());
	}
	
	/**
	 * 更新终端信息
	 * 默认不开启终端连接（适配老接口）
	 */
	public void updateMT(MT mt){
		updateMT(mt, false);
	}
	
	/**
	 * 更新终端信息
	 */
	public void updateMT(MT mt, boolean isReStart){
		MT old = this.mtCacheByID.get(mt.getId());
		if(old != null){
			if( !(old.getIp().equals(mt.getIp()))
					|| !(old.getUsername().equals(mt.getUsername()))
					|| !(old.getPassword().equals(mt.getPassword()))
					|| (old.getType() != mt.getType())
					){
				//需要重新连接
				old.update(mt);
				if(isReStart)
					this.reStartConnect(mt.getId());
				else
					this.logout(mt.getId());
			}else{
				old.update(mt);
				
				//解决在mtCacheByID中已经有该MT的信息，但是终端未连接，如果MT信息不发送改变，那么MT再也连接不上
				if(isReStart){
					MTSession session = sessionManager.getSessionByID(mt.getId());
					if(session == null || !session.isLogin()){//MT未连接
						this.reStartConnect(mt.getId());
					}
				}
			}
		}
	}
	
	/**
	 * 删除终端信息。
	 * @return
	 */
	private void removeMT(MT mt){
		if(mt == null){
			return;
		}
		String id = mt.getId();
		
		if(mtConnMonitorThread != null){
			mtConnMonitorThread.removeMtId(id);
		}
		
		MTSession session = sessionManager.getSessionByID(id);
		if(session != null){
			this.logout(id);
		}
		
		this.mtCacheByID.remove(id);
	}
	
	/**
	 * 删除会话平台信息。
	 * @param id
	 * @see #addMT(MT)
	 * @see #updateMT(MT)
	 * @return
	 */
	public MT removeMTByID(String id){
		MT mt = mtCacheByID.get(id);
		this.removeMT(mt);
		return mt;
	}

	private IClient getClient(){
		return km.getClient();
	}

	/**
	 * 停止
	 */
	public void stop(){
		this.sessionManager.clear();
	}
	/**
	 * 返回指定的会议平台是否已经登录
	 * @param id
	 * @return
	 */
	public boolean isLogin(String id){
		MTSession session = sessionManager.getSessionByID(id);
		return session != null;
	}
	
	private void refreshTime(int ssid){
		MTSession session = sessionManager.getSessionBySsid(ssid);
		if(session != null){
			session.refreshTime();
		}
	}
	private MTResponse sendRequest(MTRequest request) throws KMException{
		
		int ssid = request.getSsid();
		this.refreshTime(ssid);
		
		MTResponse response = (MTResponse)getClient().sendRequest(request);
		
		int errCode = response.getErrorcode();
		if(errCode > 0){
			//远端返回错误码
			String msg = concat("错误：", errCode, " : ", MTErrorCode.getMessage(errCode));
			RemoteException e = new RemoteException(msg);
			e.setErrorcode(errCode);
			throw e;
		}
		
		return response;
	}
	
	/**
	 * 拼接一串字符串
	 * @param strings
	 * @return
	 */
	protected static String concat(Object ...strings){
		StringBuffer sb = new StringBuffer();
		for(Object s : strings){
			sb.append(s);
		}
		return sb.toString();
	}
	
	/**
	 * 登录
	 * @param ip 终端IP
	 * @param port 终端端口
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录成功返回会话ID，登录失败返回-1或抛出异常
	 * @see #login(String)
	 * @see #logout(String)
	 * @throws KMException 
	 */
	public int login(String ip, int port, String user, String pwd) throws KMException{
		return login(ip, port, user, pwd, 0);
	}
	
	/**
	 * 登录
	 * @param ip 终端IP
	 * @param port 终端端口
	 * @param username 用户名
	 * @param password 密码
	 * @param type 类型：0-4.7版本、1-5.0版本、2-科达天行
	 * @return 登录成功返回会话ID，登录失败返回-1或抛出异常
	 * @see #login(String)
	 * @see #logout(String)
	 * @throws KMException 
	 */
	public int login(String ip, int port, String user, String pwd, int type) throws KMException{
		
		MTSession session = sessionManager.getSessionByIP(ip);
		if(session != null){
			/*
			 * 终端已经登录过。
			 * 目前程序设计为，一个终端只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			return session.getSsid();
		}
		
		int ssid = this.login0(ip, port, user, pwd,type);
		if(ssid > 0){
			//登录成功
			session = new MTSession();
			session.setSsid(ssid);
			sessionManager.putSession(session);
		}
		
		return ssid;
	}

	/**
	 * 根据ID登录终端
	 * @see #login(String, String, String, String)
	 * @see #logout(String)
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public int login(String id) throws KMException{
		MT mt = mtCacheByID.get(id);
		if(mt == null){
			throw new DataException("终端信息不存在,ID=" + id);
		}
		return this.loginByMT(mt);
	}
	
	//最终实现登录的方法
	private int loginByMT(MT mt) throws KMException{
		
		String id = mt.getId();
		String ip = mt.getIp();
		int port = mt.getPort();
		String user = mt.getUsername();
		String pwd = mt.getPassword();
		int type = mt.getType();
		
		MTSession session = sessionManager.getSessionByID(id);
		if(session != null){
			//已登录
			return session.getSsid();
		}
		
		int ssid = this.login(ip, port, user, pwd, type);
		if(ssid > 0){
			//登录成功
			session = new MTSession();
			session.setSsid(ssid);
			session.setLastTime(System.currentTimeMillis());
			session.setMt(mt);
			session.setStatus(MTSessionStatus.CONNECTED);
			sessionManager.putSession(session);
			
		}
		
		log.debug("已登录MT：ip=" + ip + "name=" + mt.getName());
		return ssid;
	}
	
	private int login0(String ip, int port, String username, String password, int type) throws KMException{
		
		LoginRequest request = new LoginRequest();
		request.setIp(ip);
		request.setPort(port);
		request.setUsername(username);
		request.setPassword(password);
		if(type == 1)//终端5.0
			request.setDevtype(DeviceType.MT5.getValue());
		else if(type == 2)//科达天行
			request.setDevtype(DeviceType.SKY.getValue());
		else//终端4.7
			request.setDevtype(DeviceType.MT.getValue());
		
		LoginResponse response = (LoginResponse)this.sendRequest(request);
		int ssid = response.getSsid();
		return ssid;
	}

	/**
	 * 获取会话标识。
	 * @param mtIP 终端IP地址
	 * @return 如果终端已连接，返回ssid，否则返回-1
	 */
	public int getSSIDByIp(String mtIP){
		MTSession session = sessionManager.getSessionByIP(mtIP);
		return session != null ? session.getSsid() : - 1;
	}
	
	/**
	 * 获取会话标识。
	 * <pre>
	 *     如果终端已经登录，直接返回会话标识;
	 *     如果终端未登录，则先登录，然后返回会话标识。
	 * </pre>
	 * @param id 终端ID，{@link MT#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 * @throws KMException 
	 */
	private int tryLogin(String id) throws KMException{
		int ssid;
		MTSession session = sessionManager.getSessionByID(id);
		if(session == null){
			//未登录
			ssid = this.login(id);
		}else{
			//已登录
			ssid = session.getSsid();
		}
		return ssid;
	}
	
	
	/**
	 * 在单独的线程中重新连接终端
	 * @param id 终端ID
	 */
	public void reStartConnect(String id){
		
		if(this.isLogin(id)){
			this.logout(id);
		}
		
		boolean newThread = false;
		if(mtConnMonitorThread == null || !mtConnMonitorThread.isWork()){
			mtConnMonitorThread = new MtConnMonitorThread(this);
			mtConnMonitorThread.setTimeout(15000);
			mtConnMonitorThread.setName("Mt-ConnMonitor");
			mtConnMonitorThread.setDaemon(true);
			newThread = true;
		}
		mtConnMonitorThread.addMtId(id);
		if(newThread){
			mtConnMonitorThread.start();
		}
	}
	
	/**
	 * 登出终端
	 * @param ssid
	 * @return
	 */
	public void logout(String id){
		
		if(!this.isLogin(id)){
			//未登录
			return;
		}

		MTSession session = sessionManager.getSessionByID(id);
		if(session == null){
			//未来登录
			return;
		}
		int ssid = session.getSsid();
		sessionManager.removeSession(ssid);
		LogoutRequest request = new LogoutRequest();
		request.setSsid(ssid);
		try {
			this.sendRequest(request);
		} catch (KMException e) {
			log.warn("logout faild", e);
		}
	}
	
	/**
	 * 向中间件获取终端连接索引SSid
	 * @param mt 终端
	 * @return 如果连接成功，返回ssid，否则返回-1
	 * @throws KMException 
	 */
	public int getMtSSid(MT mt) throws KMException{
		
		log.debug("=====> 获取终端连接索引（getMtSSid），mt："+mt);

		String ip = mt.getIp();
		int port = mt.getPort();
		String user = mt.getUsername();
		String pwd = mt.getPassword();
		int type = mt.getType();
		
		log.debug("=====> 获取终端连接索引（getMtSSid），ip："+ip+"，port："+port+"，user："+user+"，pwd："+pwd+"，type："+type);
		
		int ssid = this.login(ip, port, user, pwd, type);
		
		log.debug("=====> 获取终端连接索引（getMtSSid），ip："+ip+"，port："+port+"，user："+user+"，pwd："+pwd+"，type："+type+" 成功，返回ssid："+ssid);
		
		return ssid;
	}
	
	/**
	 * 向中间件注销终端连接索引SSid
	 * @param ssid 终端连接索引
	 */
	public void logoutMtSSid(int ssid){
		
		log.debug("=====> 注销终端连接索引（logoutMtSSid），ssid："+ssid);
		
		if(ssid == -1){
			log.error("=====> 注销终端连接索引（logoutMtSSid），ssid："+ssid+" 失败，无效索引！");
			return;
		}

		LogoutRequest request = new LogoutRequest();
		request.setSsid(ssid);
		try {
			this.sendRequest(request);
		} catch (KMException e) {
			log.error("=====> 注销终端连接索引（logoutMtSSid），ssid："+ssid+" 异常：", e);
		}
	}
	
	/**
	 * 获取当前会话中登录的终端的类型
	 * @param id
	 * @return
	 * @throws KMException 
	 */
	public MTType getMTType(String id) throws KMException{
		int ssid = this.tryLogin(id);
		if(ssid == -1)
			throw new KMException("终端连接索引无效");
		
		return getMTType(ssid);
	}
	
	public MTType getMTType(int ssid) throws KMException{
		GetMtTypeRequest request = new GetMtTypeRequest();
		request.setSsid(ssid);
		GetMtTypeResponse response = (GetMtTypeResponse)this.sendRequest(request);
		MTType type = response.getType();
		
		return type;
	}
	
	/**
	 * 开启点对点会议
	 * @param id 终端标识 {@link MT#getId()} 
	 * @param addrType 地址类型{@link StartP2PRequest#ADDRTYPE_IP} / {@link StartP2PRequest#ADDRTYPE_e164}
	 *  / {@link StartP2PRequest#ADDRTYPE_h323id} / {@link StartP2PRequest#ADDRTYPE_dialnum} / {@link StartP2PRequest#ADDRTYPE_sipaddr}
	 * @param ip
	 * @param alias 呼叫码率
	 * @throws KMException
	 */
	public void startP2P(String id, int addrType, String ip, String alias) throws KMException{
		startP2P(id, addrType, ip, alias, 1024);
	}
	
	/**
	 * 开启点对点会议
	 * @param id 终端标识 {@link MT#getId()} 
	 * @param addrType 地址类型{@link StartP2PRequest#ADDRTYPE_IP} / {@link StartP2PRequest#ADDRTYPE_e164}
	 *  / {@link StartP2PRequest#ADDRTYPE_h323id} / {@link StartP2PRequest#ADDRTYPE_dialnum} / {@link StartP2PRequest#ADDRTYPE_sipaddr}
	 * @param ip 终端IP地址
	 * @param alias 别名
	 * @param bitrate 呼叫码率
	 * @throws KMException
	 */
	public void startP2P(String id, int addrType, String ip, String alias, int bitrate) throws KMException{
		int ssid = this.tryLogin(id);
		if(ssid == -1)
			throw new KMException("终端连接索引无效");
		
		startP2P(ssid, addrType, ip, alias, bitrate, StartP2PRequest.TYPE_h323);
	}

	/**
	 * 开启点对点会议
	 * @param ssid 终端连接标识
	 * @param addrType 地址类型（终端4.7有效）
	 * @param ip 终端IP地址
	 * @param alias 别名（终端4.7有效）
	 * @param bitrate 呼叫码率
	 * @param type 主呼协议类型（终端5.0有效）
	 * @throws KMException
	 */
	public void startP2P(int ssid, int addrType, String ip, String alias, int bitrate, int type) throws KMException{
		StartP2PRequest request = new StartP2PRequest();
		request.setSsid(ssid);
		request.setAddrType(addrType);
		request.setIp(ip);
		request.setAlias(alias);
		request.setBitrate(bitrate);
		request.setType(type);
		
		this.sendRequest(request);//StartP2PResponse
	}
	
	/**
	 * 停止点对点会议
	 * @param id 终端标识 {@link MT#getId()}
	 * @throws KMException 
	 */
	public void stopP2P(String id) throws KMException{
		int ssid = this.tryLogin(id);
		if(ssid == -1)
			throw new KMException("终端连接索引无效");
		
		stopP2P(ssid);
	}
	
	public void stopP2P(int ssid) throws KMException{
		StopP2PRequest request = new StopP2PRequest();
		request.setSsid(ssid);
		
		this.sendRequest(request);//StopP2PResponse
	}
	
	/**
	 * 退出多点会议
	 * @param id 终端标识 {@link MT#getId()}
	 * @throws KMException 
	 */
	public void endMultiConf(String id) throws KMException{
		int ssid = this.tryLogin(id);
		if(ssid == -1)
			throw new KMException("终端连接索引无效");
		
		endMultiConf(ssid);
	}
	
	public void endMultiConf(int ssid) throws KMException{
		EndMultiConfRequest request = new EndMultiConfRequest();
		request.setSsid(ssid);
		
		this.sendRequest(request);//EndMultiConfResponse
	}
	
	/**
	 * 终端是否在会议中
	 * @param id 终端标识 {@link MT#getId()}
	 * @param type 点对点/多点。1.是否在点对点会议;2.是否在多点会议; 3.是否在会议中（不区分点对点还是多点。是1或者2都算） 
	 */
	public boolean checkIsInConf(String id, int type){
		try {
			int ssid = this.tryLogin(id);
			if(ssid == -1)
				throw new KMException("终端连接索引无效");
			
			return checkIsInConf(ssid, type);
		} catch (KMException e) {
			log.error("checkIsInConf()", e);
		}
		
		return false;
	}
	
	public boolean checkIsInConf(int ssid, int type){
		CheckisinconfResponse response = checkIsInConf2(ssid,type);
		
		return  response != null ? response.isInconf():false;
	}
	
	/**
	 * 终端是否在会议中
	 * @param id 终端标识 {@link MT#getId()}
	 * @param type 点对点/多点。1.是否在点对点会议;2.是否在多点会议; 3.是否在会议中（不区分点对点还是多点。是1或者2都算） 
	 */
	public CheckisinconfResponse checkMtInConf(String id, int type){
		try {
			int ssid = this.tryLogin(id);
			if(ssid == -1)
				throw new KMException("终端连接索引无效");
			
			return checkMtInConf(ssid, type);
		} catch (KMException e) {
			log.error("checkIsInConf()", e);
		}
		
		return null;
	}
	
	/**
	 * 终端是否在会议中
	 * @param id 终端标识 {@link MT#getId()}
	 * @param type 点对点/多点。1.是否在点对点会议;2.是否在多点会议; 3.是否在会议中（不区分点对点还是多点。是1或者2都算） 
	 */
	public CheckisinconfResponse checkMtInConf(int ssid, int type){
		return checkIsInConf2(ssid, type);
	}
	
	/**
	 * 终端是否在会议中
	 * @param id 终端标识 {@link MT#getId()}
	 * @param type 点对点/多点。1.是否在点对点会议;2.是否在多点会议; 3.是否在会议中（不区分点对点还是多点。是1或者2都算） 
	 */
	private CheckisinconfResponse checkIsInConf2(int ssid, int type){
		try {
			CheckisinconfRequest request = new CheckisinconfRequest();
			request.setSsid(ssid);
			request.setType(type);
			CheckisinconfResponse response = (CheckisinconfResponse)this.sendRequest(request);
			
			return response;
		} catch (KMException e) {
			log.error("checkIsInConf()", e);
		}
		
		return null;
	}
	
	/**
	 * 终端发布直播
	 * @param id 终端标识 {@link MT#getId()}
	 * @param devtype 终端类型
	 * @param ip 终端IP
	 * @param port 终端端口
	 * @param user 终端帐号
	 * @param pwd 终端密码
	 * @param type 码流类型
	 * @return
	 */
	public String getRtspUrl(String id, int devtype, String ip, int port, String user, String pwd, int type)throws KMException{
		int ssid = this.tryLogin(id);
		if(ssid == -1)
			throw new KMException("终端连接索引无效");
		
		return getRtspUrl(ssid, devtype, ip, port, user, pwd, type);
	}
	
	public String getRtspUrl(int ssid, int devtype, String ip, int port, String user, String pwd, int type){
		try {
			GetRtspUrlRequest request = new GetRtspUrlRequest();
			request.setSsid(ssid);
			request.setDevtype(devtype);
			request.setIp(ip);
			request.setPort(port);
			request.setUser(user);
			request.setPwd(pwd);
			request.setType(type);
			GetRtspUrlResponse response = (GetRtspUrlResponse)this.sendRequest(request);
			return response.getUrl();
		} catch (KMException e) {
			log.error("getRtspUrl()", e);
		}
		
		return null;
	}
	
	/**
	 * 取消直播
	 * @param id
	 * @param devtype
	 * @param url
	 * @throws KMException
	 */
	public void delRtspUrl(String id, int devtype, String url)throws KMException{
		int ssid = this.tryLogin(id);
		if(ssid == -1)
			throw new KMException("终端连接索引无效");
		
		delRtspUrl(ssid, devtype, url);
	}
	public void delRtspUrl(int ssid, int devtype, String url){
		try {
			DelRtspUrlRequest request = new DelRtspUrlRequest();
			request.setSsid(ssid);
			request.setDevtype(devtype);
			request.setUrl(url);
			this.sendRequest(request);//DelRtspUrlResponse
		} catch (KMException e) {
			log.error("delRtspUrl()", e);
		}
	}
	
	/**
	 * 获取终端视频源
	 * @param id 终端标识 {@link MT#getId()}
	 * @param videotype 视频源类型 {@link GetVideoSrcRequest#VIDEOTYPE_MAIN} {@link GetVideoSrcRequest#VIDEOTYPE_AUXILIARY}
	 * @param local 本地终端  true 本地终端, false:远端终端（远端只能获取主视频源）
	 * @return videoSrcs 终端视频源  参数：{@link VideoSrcs#port} {@link VideoSrcs#name}
	 * @throws KMException
	 */
	public List<VideoSrcs> getVideoSrc(String id, int videotype, boolean local) throws KMException{
		int ssid = this.tryLogin(id);
		GetVideoSrcRequest request = new GetVideoSrcRequest();
		request.setSsid(ssid);
		request.setVideotype(videotype);
		request.setLocal(local);
		
		GetvideosrcResponse response = (GetvideosrcResponse)this.sendRequest(request);
		return response.getVideoSrcs();
		
	}
	
	/**
	 * 选择视频源
	 * @param id 终端标识 {@link MT#getId()}
	 * @param videotype 视频源类型 {@link SelectVideoSrcRequest#VIDEOTYPE_MAIN} {@link SelectVideoSrcRequest#VIDEOTYPE_AUXILIARY}
	 * @param videoport 终端视频端口
	 * @throws KMException
	 */
	public void selectVideoSrc(String id, int videotype, int videoport) throws KMException{
		int ssid = this.tryLogin(id);
		SelectVideoSrcRequest request = new SelectVideoSrcRequest();
		request.setSsid(ssid);
		request.setVideotype(videotype);
		request.setVideoport(videoport);
		
		this.sendRequest(request);//SelectVideoSrcResponse
	}
	
	/**
	 * 获取当前视频源
	 * @param id 终端标识 {@link MT#getId()}
	 * @param local true:本地终端，false：远端终端.
	 * @return videoport 终端视频端口
	 * @throws KMException
	 */
	public int getcurVideoSrc(String id, boolean local) throws KMException{
		int ssid = this.tryLogin(id);
		GetcurVideoSrcRequest request = new GetcurVideoSrcRequest();
		request.setSsid(ssid);
		request.setLocal(local);
		
		GetcurVideoSrcResponse response = (GetcurVideoSrcResponse)this.sendRequest(request);
		return response.getVideoport();
	}
	
	/**
	 * 开始浏览码流
	 * @param id 终端标识 {@link MT#getId()}
	 * @param type 码流类型 {@link StartMtStreamRequest#TYPE_LOCAL} / {@link StartMtStreamRequest#TYPE_REMOTE}
	 * / {@link StartMtStreamRequest#TYPE_LOCAL_VIDEO} / {@link StartMtStreamRequest#TYPE_REMOTE_VIDEO}
	 * / {@link StartMtStreamRequest#TYPE_LOCAL_AUDIO} / {@link StartMtStreamRequest#TYPE_REMOTE_AUDIO}
	 * / {@link StartMtStreamRequest#TYPE_LOCAL_DOUBLE} / {@link StartMtStreamRequest#TYPE_REMOTE_DOUBLE}
	 * @param ip 接受码流IP地址
	 * @param port 接受码流端口
	 * @param usevtdu 是否通过中间件转发
	 * @return response {@link StartMtStreamResponse#audiorTcp} {@link StartMtStreamResponse#videorTcp}
	 * @throws KMException
	 */
	public StartMtStreamResponse startMtStream(String id, int type, String ip, int port, boolean usevtdu) throws KMException{
		int ssid = this.tryLogin(id);
		StartMtStreamRequest request = new StartMtStreamRequest();
		request.setSsid(ssid);
		request.setType(type);
		request.setIp(ip);
		request.setPort(port);
		request.setIsusevtdu(usevtdu);
		
		StartMtStreamResponse response = (StartMtStreamResponse)this.sendRequest(request);
		return response;
	}
	
	/**
	 * 停止浏览码流
	 * @param id 终端标识 {@link MT#getId()}
	 * @param type 码流类型 {@link StopMtStreamRequest#TYPE_LOCAL} / {@link StopMtStreamRequest#TYPE_REMOTE}
	 * / {@link StopMtStreamRequest#TYPE_LOCAL_video} / {@link StopMtStreamRequest#TYPE_REMOTE_video}
	 * / {@link StopMtStreamRequest#TYPE_LOCAL_audio} / {@link StopMtStreamRequest#TYPE_REMOTE_audio}
	 * / {@link StopMtStreamRequest#TYPE_LOCAL_DOUBLE} / {@link StopMtStreamRequest#TYPE_REMOTE_DOUBLE}
	 * @param ip 接受码流IP地址
	 * @param port 接受码流端口
	 * @throws KMException
	 */
	public void stopMtStream(String id, int type, String ip, int port) throws KMException{
		int ssid = this.tryLogin(id);
		StopMtStreamRequest request = new StopMtStreamRequest();
		request.setSsid(ssid);
		request.setType(type);
		request.setIp(ip);
		request.setPort(port);
		
		this.sendRequest(request);//StopMtStreamResponse
	}
	
	/**
	 * 请求关键帧
	 * @param id 终端标识 {@link MT#getId()}
	 * @param type 码流类型 {@link GetKeyFrameRequest#TYPE_LOCAL} / {@link GetKeyFrameRequest#TYPE_REMOTE} 
	 * / {@link GetKeyFrameRequest#TYPE_LOCAL_video} / {@link GetKeyFrameRequest#TYPE_REMOTE_video} 
	 * / {@link GetKeyFrameRequest#TYPE_LOCAL_audio} / {@link GetKeyFrameRequest#TYPE_REMOTE_audio} 
	 * / {@link GetKeyFrameRequest#TYPE_LOCAL_DOUBLE} / {@link GetKeyFrameRequest#TYPE_REMOTE_DOUBLE}
	 * @throws KMException
	 */
	public void getKeyFrame(String id, int type) throws KMException{
		int ssid = this.tryLogin(id);
		GetKeyFrameRequest request= new GetKeyFrameRequest();
		request.setSsid(ssid);
		request.setType(type);
		
		this.sendRequest(request);//GetKeyFrameResponse
	}
	
	/**
	 * 关键帧间隔设置
	 * @param id 终端标识 {@link MT#getId()}
	 * @param videotype 视频类型  0：主流，1：辅流
	 * @param h264ikeyrate H264最大间隔
	 * @param ikeyrate 非h264最大间隔
	 * @throws KMException
	 */
	public void mtKeyFrameInterVal(String id, int videotype, int h264ikeyrate, int ikeyrate) throws KMException{
		int ssid = this.tryLogin(id);
		MtKeyFrameInterValRequest request = new MtKeyFrameInterValRequest();
		request.setSsid(ssid);
		request.setVideotype(videotype);
		request.setH264ikeyrate(h264ikeyrate);
		request.setIkeyrate(ikeyrate);
		
		this.sendRequest(request);//MtKeyFrameInterValResponse
	}
	
	/**
	 * 双流发送控制
	 * @param id 终端标识 {@link MT#getId()}
	 * @param start 开始发送还是停止
	 * @param local 是否是本地终端
	 * @throws KMException
	 */
	public void mtStarteDual(String id, boolean start, boolean local) throws KMException{
		int ssid = this.tryLogin(id);
		if(ssid == -1)
			throw new KMException("终端连接索引无效");
		
		mtStarteDual(ssid, start, local);
	}

	public void mtStarteDual(int ssid, boolean start, boolean local) throws KMException{
		MtStarteDualRequest request= new MtStarteDualRequest();
		request.setSsid(ssid);
		request.setStart(start);
		request.setLocal(local);
		
		this.sendRequest(request);//MtStarteDualResponse
	}
	
	/**
	 * Ptz控制
	 * @param id 终端标识 {@link MT#getId()}
	 * @param mode 模式 {@link PtzCtrlRequest#MODE_SINGLE_DOUBLE} 
	 * / {@link PtzCtrlRequest#MODE_DOUBLE_DOUBLE} / {@link PtzCtrlRequest#MODE_SINGLE_THRED}
	 * @return response {@link PtzCtrlResponse#ptzcmd} {@link PtzCtrlResponse#start} 
	 * @throws KMException
	 */
	public PtzCtrlResponse ptzCtrl(String id, int mode) throws KMException{
		int ssid = this.tryLogin(id);
		PtzCtrlRequest request= new PtzCtrlRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		
		PtzCtrlResponse response = (PtzCtrlResponse)this.sendRequest(request);
		return response;
	}
	
	/**
	 * 设置画面显示模式
	 * @param id 终端标识 {@link MT#getId()}
	 * @param mode 模式 {@link SetPipModeRequest#MODE_SINGLE_DOUBLE} 
	 * / {@link SetPipModeRequest#MODE_DOUBLE_DOUBLE} / {@link SetPipModeRequest#MODE_SINGLE_THRED}
	 * @throws KMException 
	 */
	public void setPipMode(String id, int mode) throws KMException{
		int ssid = this.tryLogin(id);
		SetPipModeRequest request= new SetPipModeRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		
		this.sendRequest(request);//SetPipModeResponse
	}
	
	/**
	 * 画面切换
	 * @param id 终端标识 {@link MT#getId()}
	 * @param mode 模式 {@link PipSwitchRequest#MODE_SINGLE_DOUBLE} 
	 * / {@link PipSwitchRequest#MODE_DOUBLE_DOUBLE} / {@link PipSwitchRequest#MODE_SINGLE_THRED}
	 * @throws KMException
	 */
	public void pipSwitch(String id, int mode) throws KMException{
		int ssid = this.tryLogin(id);
		PipSwitchRequest request= new PipSwitchRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		
		this.sendRequest(request);//PipSwitchResponse
	}
	
	/**
	 * 静音哑音设置
	 * @param id 终端标识 {@link MT#getId()}
	 * @param mute True:静音控制,Fase:哑音控制
	 * @param open 静音/哑音 开关
	 * @throws KMException 
	 */
	public void setDumbMute(String id, String mute, String open) throws KMException{
		int ssid = this.tryLogin(id);
		SetDumbMuteRequest request= new SetDumbMuteRequest();
		request.setSsid(ssid);
		request.setMute(mute);
		request.setOpen(open);
		
		this.sendRequest(request);//SetDumbMuteResponse
	}
	
	/**
	 * 静音哑音状态获取
	 * @param id 终端标识 {@link MT#getId()}
	 * @param mute True:静音控制,Fase:哑音控制
	 * @return 
	 * @throws KMException
	 */
	public GetDumbMuteResponse getDumbMute(String id, String mute) throws KMException{
		int ssid = this.tryLogin(id);
		GetDumbMuteRequest request= new GetDumbMuteRequest();
		request.setSsid(ssid);
		request.setMute(mute);
		
		GetDumbMuteResponse response = (GetDumbMuteResponse)this.sendRequest(request);
		return response;
	}
	
	/**
	 * 音量控制
	 * @param id 终端标识 {@link MT#getId()}
	 * @param type 类型{@link VolumeCtrlRequest#PTZCMD_TOP} / {@link VolumeCtrlRequest#PTZCMD_BOTTOM}
	 * @param volume 音量大小，最大31
	 * @throws KMException 
	 */
	public void volumeCtrl(String id, int type, int volume) throws KMException{
		int ssid = this.tryLogin(id);
		VolumeCtrlRequest request= new VolumeCtrlRequest();
		request.setSsid(ssid);
		request.setType(type);
		request.setVolume(volume);
		
		this.sendRequest(request);//VolumeCtrlResponse
	}
	
	/**
	 * 登录硬盘录像机
	 * @param devType 录像机类型：VRS2KB（3）
	 * @param recIp
	 * @param mode
	 * @throws KMException
	 */
	public int loginVcr(int devType,String recIp,int mode) throws KMException{
		LoginVCRRequest request = new LoginVCRRequest();
		request.setDevtype(devType);
		request.setIp(recIp);
		request.setMode(mode);
		request.setSubdevtype(1);
		
		MT mt = new MT();
		MTSession session = sessionManager.getSessionByIP(recIp,1);
		if(session != null){
			/*
			 * 终端已经登录过。
			 * 目前程序设计为，一个终端只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			return session.getSsid();
		}
		
		LoginVCRResponse response = (LoginVCRResponse) this.sendRequest(request);
		int ssid = response.getSsid();
		
		if(ssid > 0){
			//登录成功
			session = new MTSession();
			mt.setSsid(ssid);
			mt.setIp(recIp);
			mt.setVrsType(1);
			session.setSsid(ssid);
			session.setMt(mt);
			sessionManager.putSession(session);
		}
		return ssid;
	}
	
	/**
	 * 开启硬盘录像机录像
	 * @param ip
	 * @param labletext
	 * @param recfilename
	 * @param streaminfos
	 * @throws KMException
	 */
	public StartRecVCRResponse startVcr(String ip,String labletext,String recfilename,List<Streaminfos> streaminfos) throws KMException{
		int ssid = loginVcr(3, ip, 2);
		StartRecVCRRequest request = new StartRecVCRRequest();
		request.setSsid(ssid);
		request.setIp(ip);
		request.setLabletext(labletext);
		request.setRecfilename(recfilename);
		request.setStreaminfos(streaminfos);
		
		StartRecVCRResponse response = (StartRecVCRResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 暂停录像
	 * @param ip
	 * @param mode
	 * @throws KMException
	 */
	public void recpause(String ip,int mode) throws KMException{
		int ssid = loginVcr(3, ip, 2);
		RecPauseRequest request = new RecPauseRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		
		this.sendRequest(request);//RecPauseResponse
	}
	
	/**
	 * 查询硬盘录像机状态
	 * @param ip
	 * @param mode
	 * @return
	 * @throws KMException
	 */
	public RecStatusResponse recstatus(String ip,int mode) throws KMException{
		int ssid = loginVcr(3, ip, mode);
		RecStatusRequest request = new RecStatusRequest();
		request.setSsid(ssid);
		request.setDevtype(3);
		request.setMode(mode);
		
		RecStatusResponse response = (RecStatusResponse) this.sendRequest(request);
		return response;
	}
	
	
	/**
	 * 释放录像机
	 * @param ip
	 * @param mode
	 * @throws KMException
	 */
	public void recRelease(String ip,int mode) throws KMException{
		int ssid = loginVcr(3, ip, mode);
		RecReleaseRequest request = new RecReleaseRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		
		this.sendRequest(request);//RecReleaseResponse
	}
	
	/**
	 * 继续录像
	 * @param ip
	 * @param mode
	 * @throws KMException
	 */
	public void recResume(String ip,int mode) throws KMException{
		int ssid = loginVcr(3, ip, mode);
		RecResumeRequest request = new RecResumeRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		
		this.sendRequest(request);//RecResumeResponse
	}
	
	/**
	 * 注销录像机
	 * @param ip
	 * @param mode
	 * @throws KMException
	 */
	public void unregister(String ip,int mode) throws KMException{
		int ssid = loginVcr(3, ip, mode);
		RecUnregisterRequest request = new RecUnregisterRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		request.setDevtype(3);
		
		this.sendRequest(request);//RecUnregisterResponse
	}
	
	
	//登录刻录机获取ssid
	public int loginBurn(String ip,int serverId) throws KMException{
		LoginBurnRequest request = new LoginBurnRequest();
		request.setDevtype(4);
		request.setIp(ip);
		request.setMode(2);
		request.setSubdevtype(2);
		request.setServerid(serverId);
		
		MTSession session = sessionManager.getSessionByIP(ip,2,serverId);
		if(session != null){
			/*
			 * 终端已经登录过。
			 * 目前程序设计为，一个终端只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			return session.getSsid();
		}
		
		LoginBurnResponse response = (LoginBurnResponse) this.sendRequest(request);
		int ssid = response.getSsid();
		
		if(ssid > 0){
			//登录成功
			session = new MTSession();
			MT mt = new MT();
			session.setSsid(ssid);
			mt.setIp(ip);
			mt.setSsid(ssid);
			mt.setVrsType(2);
			mt.setServerId(serverId);
			session.setMt(mt);
			sessionManager.putSession(session);
		}
		return ssid;
	}
	
	/**
	 * 开启刻录
	 * @param ip 刻录机IP
	 * @param mode 刻录机类型1：kdv2kb;2:vrs2KB
	 * @param filepath 文件路径
	 * @param filetype 文件类型
	 * @param lock 是否封盘
	 * @return
	 * @throws KMException
	 */
	public BurnStartResponse startBurn(String ip,int mode,String filepath,int filetype,int lock ,int serverId) throws KMException{
		int ssid = loginBurn(ip,serverId);
		BurnStartRequest request = new BurnStartRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		request.setFilepath(filepath);
		request.setFiletype(filetype);
		request.setLock(lock);
		
		BurnStartResponse response = (BurnStartResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 查询刻录机的状态
	 * @param ip
	 * @param mode
	 * @return
	 * @throws KMException
	 */
	public BurnStatusResponse burnStatus(String ip, int mode, int serverId) throws KMException{
		int ssid = loginBurn(ip,serverId);
		BurnStatusRequest request = new BurnStatusRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		
		BurnStatusResponse response = (BurnStatusResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 释放刻录机
	 * @param ip
	 * @param mode
	 * @throws KMException
	 */
	public void burnRelease(String ip,int mode,int serverId) throws KMException{
		int ssid = loginBurn(ip,serverId);
		BurnReleaseRequest request = new BurnReleaseRequest();
		request.setMode(mode);
		request.setSsid(ssid);
		
		this.sendRequest(request);//BurnReleaseResponse
	}
	
	/**
	 * 注销录像机
	 * @param ip
	 * @param mode
	 * @throws KMException
	 */
	public void burnUnregister(String ip, int mode,int serverId) throws KMException{
		int ssid = loginBurn(ip,serverId);
		BurnUnregisterRequest request = new BurnUnregisterRequest();
		request.setMode(mode);
		request.setSsid(ssid);
		
		this.sendRequest(request);//BurnUnregisterResponse
	}
	
	/**
	 * 登录混音器获取ssid
	 * @param ip
	 * @param serverId
	 * @return
	 * @throws KMException
	 */
	public int loginMixer(String ip,int serverId) throws KMException{
		LoginMixerRequest request = new LoginMixerRequest();
		request.setDevtype(3);
		request.setIp(ip);
		request.setMode(2);
		request.setSubdevtype(3);
		request.setServerid(serverId);
		
		MTSession session = sessionManager.getSessionByIP(ip,3);
		if(session != null){
			/*
			 * 终端已经登录过。
			 * 目前程序设计为，一个终端只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			return session.getSsid();
		}
		LoginMixerResponse response = (LoginMixerResponse) this.sendRequest(request);
		int ssid = response.getSsid();
		
		if(ssid > 0){
			//登录成功
			session = new MTSession();
			MT mt = new MT();
			session.setSsid(ssid);
			mt.setIp(ip);
			mt.setSsid(ssid);
			mt.setVrsType(3);
			session.setMt(mt);
			sessionManager.putSession(session);
		}
		return ssid;
	}
	
	/**
	 * 开始混音
	 * @param ip
	 * @param mode
	 * @param serverId
	 * @param mixerstart
	 * @param capsupportex
	 * @param doublepayload
	 * @return
	 * @throws KMException
	 */
	public MixerStartResponse startMixer(String ip, int mode, int serverId, Mixstart mixerstart, Capsupportex capsupportex, Doublepayload doublepayload) throws KMException{
		int ssid = loginMixer(ip, serverId);
		MixerStartRequest request = new MixerStartRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		request.setMixerstart(mixerstart);
		request.setCapsupportex(capsupportex);
		request.setDoublepayload(doublepayload);
		
		MixerStartResponse response = (MixerStartResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 混音器参数通知请求
	 * @param ip
	 * @param serverId
	 * @return
	 * @throws KMException
	 */
	public MixerparamResponse mixerparam(String ip,int serverId,int port) throws KMException{
		int ssid = loginMixer(ip, serverId);
		MixerparamRequest request = new MixerparamRequest();
		request.setMode(2);
		request.setSsid(ssid);
		request.setIp(ip);
		request.setPort(port);
		
		MixerparamResponse response = (MixerparamResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 释放混音器
	 * @param ip
	 * @param serverId
	 * @return
	 * @throws KMException
	 */
	public MixerreleaseResponse mixerrelease(String ip, int serverId) throws KMException{
		int ssid = loginMixer(ip, serverId);
		MixerreleaseRequest request = new MixerreleaseRequest();
		request.setMode(2);
		request.setSsid(ssid);
		
		MixerreleaseResponse response = (MixerreleaseResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 注销混音器
	 * @param ip
	 * @param serverId
	 * @return
	 * @throws KMException
	 */
	public MixerunregisterResponse mixerUnregister(String ip, int serverId) throws KMException{
		int ssid = loginMixer(ip, serverId); 
		MixerunregisterRequest request = new MixerunregisterRequest();
		request.setMode(2);
		request.setSsid(ssid);
		
		MixerunregisterResponse response = (MixerunregisterResponse) this.sendRequest(request);
		return response;
		
	}
	
	/**
	 * 获取MT某一路码流转发地址列表
	 * @param id
	 * @param type
	 * @return
	 * @throws KMException
	 */
	public GetpoststreamaddrexResponse getpoststreamaddrex(String id, int type) throws KMException{
		int ssid = this.tryLogin(id);
		GetpoststreamaddrexRequest request = new GetpoststreamaddrexRequest();
		request.setSsid(ssid);
		request.setType(type);
		
		GetpoststreamaddrexResponse response = (GetpoststreamaddrexResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 删除MT某一路码流单个转发地址
	 * @param id
	 * @param type
	 * @param ip
	 * @param port
	 * @return
	 * @throws KMException
	 */
	public DelsinglestreamResponse delsinglestream(String id, int type, String ip, int port) throws KMException{
		int ssid = this.tryLogin(id);
		DelsinglestreamRequest request = new DelsinglestreamRequest();
		request.setSsid(ssid);
		request.setIp(ip);
		request.setPort(port);
		request.setType(type);		
		DelsinglestreamResponse response = (DelsinglestreamResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 登录卓兰设备
	 * @param mt
	 * @return ssid
	 * @throws KMException
	 */
	public LoginZLResponse loginZL(String ip,int subdevtype) throws KMException {
		LoginZLRequest request = new LoginZLRequest();
		request.setIp(ip);
		request.setPort(4196);
		request.setSubdevtype(subdevtype);
		MTSession session = sessionManager.getSessionByIP(ip);
		LoginZLResponse response = (LoginZLResponse)this.sendRequest(request);
		int ssid = response.getSsid();
		if(ssid > 0){
			//登录成功
			session = new MTSession();
			MT mt = new MT();
			mt.setId(ip);
			session.setSsid(ssid);
			mt.setIp(ip);
			mt.setSsid(ssid);
			session.setMt(mt);
			sessionManager.putSession(session);
		}
		return response;
	}
	
	/**
	 * 信令透传
	 * @param ip
	 * @param msg
	 */
	public TransmitInfoResponse transmitInfo(String ip,String msg){
		TransmitInfoResponse response = null;
		try {
			int ssid = this.getSSIDByIp(ip);//.loginZL(ip,3).getSsid();					
			TransmitInfoRequest request = new TransmitInfoRequest();
			request.setSsid(ssid);		
			request.setMsg(msg);
			response = (TransmitInfoResponse) this.sendRequest(request);
		} catch (KMException e) {
			log.warn("transmitInfo faild", e);
		}
		return response;
	}
	
}
