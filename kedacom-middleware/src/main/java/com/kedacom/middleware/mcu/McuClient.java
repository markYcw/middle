package com.kedacom.middleware.mcu;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListener;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import com.kedacom.middleware.mcu.domain.*;
import com.kedacom.middleware.mcu.request.*;
import com.kedacom.middleware.mcu.response.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * “会议平台”接口访问。
 * @author TaoPeng
 *
 */
public class McuClient {

	private static final Logger log = Logger.getLogger(McuClient.class);
	
	/**
	 * 会话管理。根据目前的设计，一个终端最多一个会话，好比一个监控平台只有一个主链。
	 */
	private McuSessionManager sessionManager;
	
	/**
	 * “会话平台”集合。
	 * key：会议平台标识， value:会议平台详细信息
	 */
	private Hashtable<String, Mcu> mcuCacheByID = new Hashtable<String, Mcu>();
	/**
	 * 服务器数据和状态监听器
	 */
	private List<McuNotifyListener> listeners = new ArrayList<McuNotifyListener>();

	/**
	 * Mcu连接监控线程
	 */
	private McuConnMonitorThread mcuConnMonitorThread;
	
	private KM km;
	
	
	public McuClient(KM km){
		
		this.km = km;
		this.sessionManager = new McuSessionManager(this);

		McuClientListener listener = new McuClientListener(this);
		
		/*
		 * TODO 如果当McuClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
		 * 但由于实际应用中McuClient只有一个，所以此处暂不处理
		 */
		TCPClient tcpClient = (TCPClient)km.getClient();
		tcpClient.addListener(listener);
	}
	
	private IClient getClient(){
		return km.getClient();
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		this.stopReStartConnect();
		this.sessionManager.stop();
	}
	
	/**
	 * 增加监听器
	 * @see #removeListener(TCPClientListener)
	 * @param listener
	 * @return
	 */
	public boolean addListener(McuNotifyListener listener){
		return this.listeners.add(listener);
	}
	
	/**
	 * 删除监听器
	 * @param listener
	 * @see #addListener(TCPClientListener)
	 * @return
	 */
	public boolean removeListener(McuNotifyListener listener){
		return this.listeners.remove(listener);
	}
	
	protected List<McuNotifyListener> getAllListeners(){
		List<McuNotifyListener> list = new ArrayList<McuNotifyListener>(this.listeners.size());
		list.addAll(listeners);
		return list;
	}
	
	/**
	 * 获取会话管理器
	 * @return
	 */
	public McuSessionManager getSessionManager() {
		return sessionManager;
	}
	
	/**
	 * 增加会议平台信息。
	 * @see #addMcu(Mcu)
	 * @see #updateMcu(Mcu)
	 * @see #removeMcuByID(int)
	 * @see #removeMcuByIP(int)
	 * @param mcu
	 */
	public void addMcu(Mcu mcu){
		this.mcuCacheByID.put(mcu.getId(), mcu);
		this.reStartConnect(mcu.getId());
	}
	
	/**
	 * 更新会议平台信息
	 * @param mcu
	 */
	public void updateMcu(Mcu mcu){
		Mcu old = this.mcuCacheByID.get(mcu.getId());
		if( !(old.getIp().equals(mcu.getIp()))
				|| !(old.getUser().equals(mcu.getUser()))
				|| (old.getPort() != mcu.getPort())
				|| !(old.getPassword().equals(mcu.getPassword()))
				|| !(old.getVersion().equals(mcu.getVersion()))
				|| (old.getSdkKey() != null && !old.getSdkKey().equals(mcu.getSdkKey()))
				|| (mcu.getSdkKey() != null && !mcu.getSdkKey().equals(old.getSdkKey()))
				|| (old.getSdkSecret() != null && !old.getSdkSecret().equals(mcu.getSdkSecret()))
				|| (mcu.getSdkSecret() != null && !mcu.getSdkSecret().equals(old.getSdkSecret()))
				){
			//需要重新连接
			old.update(mcu);
			this.reStartConnect(mcu.getId());
		}else{
			old.update(mcu);
			
			//解决在mcuCacheByID中已经有该MCU的信息，但是MCU未连接，如果MCU信息不发生改变，那么MCU再也连接不上
			McuSession session = sessionManager.getSessionByID(mcu.getId());
			if(session == null || !session.isLogin()){//当前MCU未连接
				this.reStartConnect(mcu.getId());
			}
		}
	}
	
	/**
	 * 设置（添加或修改）会议平台信息
	 * @param mcu
	 */
	public void setMcu(Mcu mcu){
		Mcu old = this.mcuCacheByID.get(mcu.getId());
		if(old != null){
			updateMcu(mcu);
		}else{
			addMcu(mcu);
		}
	}
	
	/**
	 * 删除会话平台信息。
	 * @param mcu
	 * @see #addMcu(Mcu)
	 * @see #removeMcuByID(int)
	 * @see #removeMcuByIP(int)
	 * @return
	 */
	private void removeMcu(Mcu mcu){
		if(mcu == null){
			return;
		}
		String id = mcu.getId();
		
		if(mcuConnMonitorThread != null){
			mcuConnMonitorThread.removeMcuId(id);
		}
		
		McuSession session = sessionManager.getSessionByID(id);
		if(session != null){
			this.logout(id);
		}
		
		this.mcuCacheByID.remove(id);
	}
	
	/**
	 * 删除会话平台信息。
	 * @param id
	 * @see #addMcu(Mcu)
	 * @see #updateMcu(Mcu)
	 * @return
	 */
	public Mcu removeMcuByID(String id){
		Mcu mcu = mcuCacheByID.get(id);
		this.removeMcu(mcu);
		return mcu;
	}

	/**
	 * 获取会话标识
	 * @param id 会议平台ID，{@link Mcu#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByID(String id) throws ConnectException {
		McuSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("会议平台未连接");
		}
		if(!session.isLogin()){
			if(session.getStatus() == McuSessionStatus.CONNECTING){
				throw new ConnectException("会议平台正在登录中");
			}else if(session.getStatus() == McuSessionStatus.FAILED){
				throw new ConnectException("会议平台登录失败");
			}else if(session.getStatus() == McuSessionStatus.disconnect){
				throw new ConnectException("会议平台连接中断");
			}else{
				throw new ConnectException("会议平台未登录");
			}
		}
		int ssid = session.getSsid();
		return ssid;
	}
	
	/**
	 * 获取会议平台信息
	 * @param id 会议平台ID，{@link Mcu#getId()}
	 * @return 会议平台信息
	 * @throws ConnectException
	 */
	private Mcu getMcuByID(String id) throws ConnectException {
		McuSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("会议平台未连接");
		}

		return session.getMcu();
	}
	
	/**
	 * 获取会话标识
	 * @param id 会议平台ID，{@link Mcu#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByIDForLogout(String id) throws ConnectException {
		McuSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("会议平台未连接");
		}
		
		return session.getSsid();
	}
	
	
	/**
	 * 根据ID登录会议平台
	 * @see #addMcu(Mcu)
	 * @see #login(String, String, String, String)
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public int login(String id) throws KMException {
		Mcu mcu = mcuCacheByID.get(id);
		if(mcu == null){
			throw new DataException("会议平台信息不存在,ID=" + id);
		}
		return this.loginByMcu(mcu);
	}
	
	//最终实现登录的方法
	private int loginByMcu(Mcu mcu) throws KMException {
		
		String id = mcu.getId();
		String ip = mcu.getIp();
		int port = mcu.getPort();
		String user = mcu.getUser();
		String pwd = mcu.getPassword();
		String version = mcu.getVersion();
		String sdkKey = mcu.getSdkKey();
		String sdkSecret = mcu.getSdkSecret();
		
		McuSession session = sessionManager.getSessionByID(id);
		if(session != null){
			/*
			 * “会议平台”已经登录。
			 * 目前程序设计为，一个会议平台只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			if(session.isLogin()){
				return session.getSsid();
			}else{
				//注销无效的会话
				this.logout(id); //此处注销登录，在中间件上可能已经不存在此ssid，如果中间件直接忽略并返回响应。
			}
		}
		
		int ssid = this.login(ip, port, user, pwd, version, sdkKey, sdkSecret);
		if(ssid > 0){
			//登录成功
			session = new McuSession();
			session.setSsid(ssid);
			session.setLastTime(System.currentTimeMillis());
			session.setMcu(mcu);
			session.setStatus(McuSessionStatus.CONNECTED);
			sessionManager.putSession(session);
			
		}
		
		log.debug("已登录MCU：ip=" + ip + "name=" + mcu.getName());
		return ssid;
	}
	
	private int login( String ip, int port, String username, String password, String version, String sdkKey, String sdkSecret) throws KMException {
		
		LoginRequest request = new LoginRequest();
		request.setIp(ip);
		request.setPort(port);
		request.setUser(username);
		request.setPwd(password);
		
		if(Mcu.MCU_VERSION_5.equals(version)){
			request.setKey(sdkKey);//5.0接口有效
			request.setSecret(sdkSecret);//5.0接口有效
			request.setDevtype(DeviceType.MCU5.getValue());//登录5.0会议平台
		}
		
		LoginResponse response = (LoginResponse)this.sendRequest(request);
		int ssid = response.getSsid();
		return ssid;
	}

	/**
	 * 返回指定的会议平台是否已经登录
	 * @param id
	 * @return
	 */
	public boolean isLogin(String id){
		McuSession session = sessionManager.getSessionByID(id);
		boolean login = session != null && session.isLogin();
		return login;
	}
	
	private void refreshTime(int ssid){
		McuSession session = sessionManager.getSessionBySsid(ssid);
		if(session != null){
			session.refreshTime();
		}
	}

	private McuResponse sendRequest(McuRequest request) throws KMException {
		
		int ssid = request.getSsid();
		this.refreshTime(ssid);
		
//		if( !(request instanceof LoginRequest)){
//			McuSession session = sessionManager.getSessionBySsid(ssid);
//			boolean login = session != null && session.isLogin();
//			if(!login){
//				//未登录会议平台
//				throw new DataException("未登录会议平台");
//			}
//		}
		
		McuResponse response = (McuResponse)getClient().sendRequest(request);
		
		int errCode = response.getErrorcode();
		if(errCode > 0){
			//远端返回错误码
			String msg = concat("错误：", errCode, " : ", McuErrorCode.getMessage(errCode));
			RemoteException exception = new RemoteException(msg);
			exception.setErrorcode(errCode);
			throw exception;
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
	 * 登出会议平台。<br/>
	 * 一般而言，用户不需要调用此方法，因为一旦登出会议平台，可能会影响到其他使用此会议平台的用户。
	 * @param ssid
	 * @return
	 */
	public void logout(String id){
		int ssid = -1;
		try {
			ssid = this.tryGetSSIDByIDForLogout(id);
		} catch (ConnectException e) {
			log.debug("logout:not login", e);
		}
		
		if(ssid >= 0){
			McuSession session = sessionManager.removeSession(ssid);
			if(session != null && session.isLogin()){
				LogoutRequest request = new LogoutRequest();
				request.setSsid(ssid);
				try {
					this.sendRequest(request);
				} catch (KMException e) {
					log.warn("logout faild", e);
				}
			}
		}
	}
	
	public void onDisConnect(String id){
		try {
			int ssid = this.tryGetSSIDByID(id);
			sessionManager.removeSession(ssid);
		} catch (ConnectException e) {
			log.debug("onDisConnect: not login", e);
		}
		this.reStartConnect(id);
	}
	
	/**
	 * 在单独的线程中重新连接会议平台
	 * @param id 会议平台ID
	 */
	public void reStartConnect(String id){
		
		if(this.isLogin(id)){
			this.logout(id);
		}
		
		boolean newThread = false;
		if(mcuConnMonitorThread == null || !mcuConnMonitorThread.isWork()){
			mcuConnMonitorThread = new McuConnMonitorThread(this);
			mcuConnMonitorThread.setTimeout(15000);
			mcuConnMonitorThread.setName("Mcu-ConnMonitor");
			mcuConnMonitorThread.setDaemon(true);
			newThread = true;
		}
		mcuConnMonitorThread.addMcuId(id);
		if(newThread){
			mcuConnMonitorThread.start();
		}
	}
	
	private void stopReStartConnect(){
		if(mcuConnMonitorThread != null){
			mcuConnMonitorThread.stop();
			mcuConnMonitorThread = null;
		}
	}
	
	//会议操作
	
	/**
	 * 获取会议/模板列表
	 * @param id 会议平台标识
	 * @param mode １：即时会议； 2会议模板
	 * @return response 
	 * @throws KMException
	 */
	public List<Confe164> getConfTem(String id, int mode) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetConfTemRequest request = new GetConfTemRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		
		GetConfTemResponse response = (GetConfTemResponse)this.sendRequest(request);
		return response.getConfe164s();
	}
	
	/**
	 * 获取会议信息
	 * @param id 会议平台标识
	 * @param e164 会议e164串号
	 * @param mode １：即时会议； 2会议模板
	 * @return response
	 * @throws KMException
	 */
	public GetConfInfoResponse getConfInfo(String id, String e164, int mode) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetConfInfoRequest request = new GetConfInfoRequest();
		request.setSsid(ssid);
		request.setE164(e164);
		request.setMode(mode);
		
		GetConfInfoResponse response = (GetConfInfoResponse)this.sendRequest(request);
		return response;
	}
	
	/**
	 * 创建会议/模板
	 * @param id 会议平台标识
	 * @param mode １：即时会议； 2会议模板
	 * @param confinfo 会议信息
	 * @return 创建成功后返回的唯一标识（5.0接口有效）
	 * @throws KMException
	 */
	public String createConf(String id, int mode, Confinfo confinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		CreateConfRequest request = new CreateConfRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		request.setConfinfo(confinfo);
		
		CreateConfResponse response = (CreateConfResponse)this.sendRequest(request);
		return response.getConfe164();
	}
	
	/**
	 * 结束会议、删除模板
	 * @param id 会议平台标识
	 * @param e164 创建时填写的
	 * @param mode 1：即时会议 2：会议模板
	 * @throws KMException
	 */
	public void releaseConfResponse(String id, String e164, int mode) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		ReleaseConfRequest request = new ReleaseConfRequest();
		request.setSsid(ssid);
		request.setMode(mode);
		request.setE164(e164);
		
		this.sendRequest(request);//ReleaseConfResponse
	}
	
	/**
	 * 开启模板会议
	 * @param id 会议平台标识
	 * @param e164
	 * @throws KMException
	 */
	public void StartTemConfResponse(String id, String e164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartTemConfRequest request = new StartTemConfRequest();
		request.setSsid(ssid);
		request.setE164(e164);
		
		this.sendRequest(request);//StartTemConfResponse
	}
	
	//终端操作
	
	/**
	 * 获取与会成员
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @return mtinfos 
	 * @throws KMException
	 */
	public Set<String> getJoinEdmt(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetJoinEdmtRequest request = new GetJoinEdmtRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		GetJoinEdmtResponse response = (GetJoinEdmtResponse)this.sendRequest(request);
		return response.getMtinfos();
	}
	
	/**
	 * 添加终端
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param callrate 终端呼叫码率
	 * @param callmode 呼叫模式，0：手动，2：定时
	 * @throws KMException
	 */
	public void addMT(String id, String confe164, String mtinfo, int callrate, int callmode) throws KMException {
		addMT(id, confe164, mtinfo, callrate, callmode, 0,-1);
	}
	
	/**
	 * 添加终端（兼容5.0接口）
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param callrate 终端呼叫码率
	 * @param callmode 呼叫模式，0：手动，2：定时
	 * @param type (5.0接口有效)终端类型，5-e164号码; 6-电话; 7-ip地址
	 * @param forced_call （5.0接口有效）是否强拆：0-否、1-是
	 * @throws KMException
	 */
	public void addMT(String id, String confe164, String mtinfo, int callrate, int callmode,int type,int forced_call) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		AddMTRequest request = new AddMTRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setCallrate(callrate);
		request.setCallmode(callmode);
		request.setType(type);
		request.setForced_call(forced_call);
		
		this.sendRequest(request);//AddMTResponse
	}
	
	/**
	 * 删除终端
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @throws KMException
	 */
	public void delMT(String id, String confe164, String mtinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		DelMTRequest request = new DelMTRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		
		this.sendRequest(request);//DelMTResponse
	}
	
	/**
	 * 获取终端状态(会议平台4.7版本特有)
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @return mtstatus 终端状态
	 * @throws KMException
	 */
	public MTStatus getMTStatus(String id, String confe164, String mtinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetMTStatusRequest request = new GetMTStatusRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		
		GetMTStatusResponse response = (GetMTStatusResponse)this.sendRequest(request);
		return response.getMtstatus();
	}
	
	/**
	 * 呼叫终端
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @throws KMException
	 */
	public void callMT(String id, String confe164, String mtinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		CallMTRequest request = new CallMTRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		
		this.sendRequest(request);//CallMTResponse
	}
	
	/**
	 * 挂断终端
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @throws KMException
	 */
	public void dropMT(String id, String confe164, String mtinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		DropMTRequest request = new DropMTRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		
		this.sendRequest(request);//DropMTResponse
	}
	
	/**
	 * 设置发言人
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @throws KMException
	 */
	public void setSpeaker(String id, String confe164, String mtinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		SetSpeakerRequest request = new SetSpeakerRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		
		this.sendRequest(request);//SetSpeakerResponse
	}
	
	/**
	 * 取消发言人
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @throws KMException
	 */
	public void cancelSpeaker(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		CancelSpeakerRequest request = new CancelSpeakerRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		this.sendRequest(request);//CancelSpeakerResponse
	}

    /**
     * 获取发言人
     * @param id
     * @param conFe164 会议e164号
     * @return
     * @throws KMException
     */
	public String getSpeaker(String id, String conFe164) throws KMException{
        int ssid = this.tryGetSSIDByID(id);
        GetSpeakerRequest request = new GetSpeakerRequest();
        request.setSsid(ssid);
        request.setConFe164(conFe164);

        GetSpeakerResponse response = (GetSpeakerResponse) this.sendRequest(request);
        return response.getMtInfo();
    }
	
	/**
	 * 设置主席
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @throws KMException
	 */
	public void setChairman(String id, String confe164, String mtinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		SetChairmanRequest request = new SetChairmanRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		
		this.sendRequest(request);//SetChairmanResponse
	}
	
	/**
	 * 取消主席
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @throws KMException
	 */
	public void cancelChairman(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		CancelChairmanRequest request = new CancelChairmanRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		this.sendRequest(request);//CancelChairmanResponse
	}

	/**
	 * 获取主席
	 * @param id
	 * @param conFe164 会议e164号
	 * @return
	 * @throws KMException
	 */
	public String getChairMan(String id, String conFe164) throws KMException{
        int ssid = this.tryGetSSIDByID(id);
        GetChairManRequest request = new GetChairManRequest();
        request.setSsid(ssid);
        request.setConFe164(conFe164);

        GetChairManResponse response = (GetChairManResponse) this.sendRequest(request);
        return response.getMtInfo();
    }
	
	/**
	 * 全场终端静音/哑音设置
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param ismute True:静音 False:哑音
	 * @param isopen 静音/哑音是否开启
	 * @throws KMException
	 */
	public void allMTQuiet(String id, String confe164, boolean ismute, boolean isopen) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		AllMTQuietRequest request = new AllMTQuietRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setIsmute(ismute);
		request.setIsopen(isopen);
		
		this.sendRequest(request);//AllMTQuietResponse
	}
	
	/**
	 * 单个终端静音/哑音设置
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param ismute True:静音 False:哑音
	 * @param isopen 静音/哑音是否开启
	 * @throws KMException
	 */
	public void mtQuiet(String id, String confe164, String mtinfo, boolean ismute, boolean isopen) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		MTQuietRequest request = new MTQuietRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setIsmute(ismute);
		request.setIsopen(isopen);
		
		this.sendRequest(request);//MTQuietResponse
	}
	
	/**
	 * 单个终端静音/哑音设置
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param volumetype 音量类型 0：输出音量1：输入
	 * @param volume 音量值 Pcmt 0~255, 普通keda终端 0~32 private int volume;
	 * @throws KMException
	 */
	public void setMTVolume(String id, String confe164, String mtinfo, int volumetype, int volume) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		SetMTVolumeRequest request = new SetMTVolumeRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setVolumetype(volumetype);
		request.setVolume(volume);
		
		this.sendRequest(request);//SetMTVolumeResponse
	}
	
	/**
	 * 终端双流发送控制
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param issend 开始停止发送
	 * @throws KMException
	 */
	public void mtDualStreamCtrl(String id, String confe164, String mtinfo, boolean issend) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		MTDualStreamCtrlRequest request = new MTDualStreamCtrlRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setIssend(issend);
		
		this.sendRequest(request);//MTDualStreamCtrlResponse
	}
	
	/**
	 * 开始浏览终端码流
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param mode 1-视频2-音频3-音视频
	 * @param dstaddr 接受码流地址。  查看码流是由中间件转发的，平台不支持将码流发向指定地址。
	 * @throws KMException
	 */
	public void startMTMonitor(String id, String confe164, String mtinfo, int mode, IPAddr dstaddr) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartMTMonitorRequest request = new StartMTMonitorRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setMode(mode);
		request.setDstaddr(dstaddr);
		
		this.sendRequest(request);//StartMTMonitorResponse
	}
	
	/**
	 * 停止浏览终端码流
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param dstaddr 接受码流地址
	 * @throws KMException
	 */
	public void stopMTMonitor(String id, String confe164, String mtinfo, IPAddr dstaddr) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StopMTMonitorRequest request = new StopMTMonitorRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setDstaddr(dstaddr);
		
		this.sendRequest(request);//StopMTMonitorResponse
	}


    /**
     * 请求浏览码流关键帧
     * @param id 会议平台标识
     * @param conFe164 会议e164号
     * @param dstAddr 接受码流地址
     * @throws KMException
     */
	public void getMonitorKey(String id ,String conFe164 ,IPAddr dstAddr) throws KMException{
        int ssid = this.tryGetSSIDByID(id);
        GetMonitorKeyRequest request = new GetMonitorKeyRequest();
        request.setSsid(ssid);
        request.setConFe164(conFe164);
        request.setDstaddr(dstAddr);

        this.sendRequest(request);
    }
	
	//外设操作
	
	/**
	 * 开始画面合成
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param vmpparam 画面合成参数
	 * @throws KMException
	 */
	public void startVmp(String id, String confe164, VmpParam vmpparam) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartVmpRequest request = new StartVmpRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setVmpparam(vmpparam);
		
		this.sendRequest(request);//StartVmpResponse
	}
	
	/**
	 * 停止画面合成
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @throws KMException
	 */
	public void stopVmp(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StopVmpRequest request = new StopVmpRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		this.sendRequest(request);//StopVmpResponse
	}
	
	/**
	 * 智能混音
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @throws KMException
	 */
	public void startTentiremix(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartTentiremixRequest request = new StartTentiremixRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		this.sendRequest(request);//StartTentiremixResponse
	}
	
	/**
	 * 定制混音
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfos 终端e164号或者IP  数组
	 * @throws KMException
	 */
	public void startPartmix(String id, String confe164, Set<String> mtinfos) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartPartmixRequest request = new StartPartmixRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfos(mtinfos);
		
		this.sendRequest(request);//StartPartmixResponse
	}
	
	/**
	 * 停止混音
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @throws KMException
	 */
	public void stopMix(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StopMixRequest request = new StopMixRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		this.sendRequest(request);//StopMixResponse
	}
	
	/**
	 * 添加混音成员
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @throws KMException
	 */
	public void addMixMember(String id, String confe164, String mtinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		AddMixMemberRequest request = new AddMixMemberRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		
		this.sendRequest(request);//AddMixMemberResponse
	}
	
	/**
	 * 删除混音成员
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @throws KMException
	 */
	public void delMixMember(String id, String confe164, String mtinfo) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		DelMixMemberRequest request = new DelMixMemberRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		
		this.sendRequest(request);//DelMixMemberResponse
	}
	
	/**
	 * 获取录像机状态列表（会议平台4.7版本特有接口）
	 * @param id 会议平台标识
	 * @return vcrstatus 录像机状态列表
	 * @throws KMException
	 */
	public List<VcrStatus> getVcrStatus(String id) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetVcrStatusRequest request = new GetVcrStatusRequest();
		request.setSsid(ssid);
		
		GetVcrStatusResponse response = (GetVcrStatusResponse)this.sendRequest(request);
		return response.getVcrstatus();
	}
	
	/**
	 * 根据录像ID获取录像录制状态（会议平台5.0及以上版本特有接口）
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param recid 录像ID
	 * @return
	 * @throws KMException
	 */
	public VcrRecorderStatus getVcrRecorderStatusByRecid(String id, String confe164, String recid) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetRecorderStatusRequest request = new GetRecorderStatusRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setRecid(recid);
		
		GetRecorderStatusResponse response = (GetRecorderStatusResponse)this.sendRequest(request);
		return response.getRecStatus();
	}
	
	/**
	 * 获取录像录制状态列表（会议平台5.0及以上版本特有接口）
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @return
	 * @throws KMException
	 */
	public List<VcrRecorderStatus> getVcrRecorderStatusByConf(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetRecorderStatusListRequest request = new GetRecorderStatusListRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		GetRecorderStatusListResponse response = (GetRecorderStatusListResponse)this.sendRequest(request);
		
		return response.getRecStatusList();
	}
	
	/**
	 * 开始录像（适配会议平台4.7版本）
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param vcrname 录像机名称
	 * @param filename 录像文件名称
	 * @param isconf 是否是会议录像 False: 终端录像
	 * @throws KMException
	 */
	public String startRec(String id, String confe164, String mtinfo, String vcrname, String filename, boolean isconf) throws KMException {
		return startRec( id, confe164, mtinfo, vcrname, filename,  isconf, 0, 0, 1, 1, 1);
	}
	
	/**
	 * 
	 * 开始录像
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param vcrname 录像机名称
	 * @param filename 录像文件名称
	 * @param isconf 是否是会议录像 False: 终端录像
	 * @param publishmode 发布模式（5.0）0-不发布；1-发布
	 * @param anonymous 是否支持免登陆观看直播（5.0）0-不支持；1-支持；
	 * @param recordermode 录像模式 （5.0）1-录像；2-直播；3-录像+直播；
	 * @param mainstream 是否录主格式码流（视频+音频） （5.0）0-否；1-是；
	 * @param dualstream 是否录双流（仅双流） （5.0）0-否；1-是；
	 * @throws KMException
	 */
	public String startRec(String id, String confe164, String mtinfo, String vcrname, String filename, 
			boolean isconf, int publishmode, int anonymous, int recordermode, int mainstream, int dualstream) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartRecRequest request = new StartRecRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setVcrname(vcrname);
		request.setFilename(filename);
		request.setIsconf(isconf);
		
		request.setPublishmode(publishmode);
		request.setAnonymous(anonymous);
		request.setRecordermode(recordermode);
		request.setMainstream(mainstream);
		request.setDualstream(dualstream);
		
		StartRecResponse response = (StartRecResponse)this.sendRequest(request);
		return response.getRecid();
	}
	
	/**
	 * 暂停录像
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param isconf 是否是会议录像 False: 终端录像
	 * @throws KMException
	 */
	public void pauseRec(String id, String confe164, String mtinfo, boolean isconf) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		PauseRecRequest request = new PauseRecRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setIsconf(isconf);
		
		this.sendRequest(request);//PauseRecResponse
	}
	
	/**
	 * 恢复录像
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param isconf 是否是会议录像 False: 终端录像
	 * @throws KMException
	 */
	public void resumeRec(String id, String confe164, String mtinfo, boolean isconf) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		ResumeRecRequest request = new ResumeRecRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtinfo(mtinfo);
		request.setIsconf(isconf);
		
		this.sendRequest(request);//ResumeRecResponse
	}
	
	/**
	 * 停止录像（适配会议平台4.7版本）
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号
	 * @param vcrname 录像机名称
	 * @param filename 录像文件名称
	 * @param isconf 是否是会议录像 False: 终端录像
	 * @throws KMException
	 */
	public void stoptRec(String id, String confe164, String mtinfo, String vcrname, String filename, boolean isconf) throws KMException {
		stoptRec(id, confe164, mtinfo, vcrname, filename, isconf, "", 1);
	}
	
	/**
	 * 停止录像
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param mtinfo 终端IP或者e164号（会议平台4.7版本有效）
	 * @param vcrname 录像机名称（会议平台4.7版本有效）
	 * @param filename 录像文件名称（会议平台4.7版本有效）
	 * @param isconf 是否是会议录像 False: 终端录像（会议平台4.7版本有效）
	 * @param recid 录像ID（会议平台5.0及以上版本有效）
	 * @param recordermode 录像模式：1-录像；2-直播；3-录像+直播（会议平台5.0及以上版本有效）
	 * @throws KMException
	 */
	public void stoptRec(String id, String confe164, String mtinfo, String vcrname, String filename, boolean isconf, String recid, int recordermode) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StopRecRequest request = new StopRecRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		//以下四个会议平台4.7版本有效
		request.setMtinfo(mtinfo);
		request.setVcrname(vcrname);
		request.setFilename(filename);
		request.setIsconf(isconf);
		
		//以下会议平台5.0及以上版本有效
		request.setRecid(recid);
		request.setRecordermode(recordermode);
		
		this.sendRequest(request);//StopRecResponse
	}
	
	/**
	 * 开始广播
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param src 广播源 0-发言人 1-画面合成 2-混音
	 * @throws KMException
	 */
	public void startBrst(String id, String confe164, int src) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartBrstRequest request = new StartBrstRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setSrc(src);
		
		this.sendRequest(request);//StartBrstResponse
	}
	
	/**
	 * 停止广播
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param src 广播源 3-发言人 4-画面合成 5-混音
	 * @throws KMException
	 */
	public void stopBrst(String id, String confe164, int src) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StopBrstRequest request = new StopBrstRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setSrc(src);
		
		this.sendRequest(request);//StopBrstResponse
	}
	
	/**
	 * 获取电视墙列表
	 * @param id 会议平台标识
	 * @return tvwalls 电视墙列表
	 * @throws KMException
	 */
	public List<TVWalls> getTVWall(String id) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetTVWallRequest request = new GetTVWallRequest();
		request.setSsid(ssid);
		
		GetTVWallResponse response = (GetTVWallResponse)this.sendRequest(request);
		return response.getTvwalls();
	}

	/**
	 * 获取平台电视墙列表
	 * @author ycw
	 * @param id 会议平台标识
	 * @return Hdu 电视墙相关信息
	 * @throws KMException
	 */
	public List<Hdu> getPlatTvWall(String id) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetPlatTvWallRequest request = new GetPlatTvWallRequest();
		request.setSsid(ssid);

		GetPlatTvWallResponse response = (GetPlatTvWallResponse) this.sendRequest(request);
		return response.getHdus();
	}
	/**
	 * 获取会议电视墙列表
	 * @author ycw
	 * @param id 会议平台标识
	 * @return HduId 电视墙相关信息
	 * @throws KMException
	 */
	public List<HduId> getConfTvWall(String id, String confe164) throws KMException{
		int ssid = this.tryGetSSIDByID(id);
		GetConfTvWallRequest request = new GetConfTvWallRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);

		GetConfTvWallResponse response = (GetConfTvWallResponse) this.sendRequest(request);
		return response.getHduIds();
	}

	/**
	 * 获取会议电视墙通道信息
	 * @author ycw
	 * @param id
	 * @param confe164 会议ID
	 * @param eqpid 电视墙ID
	 * @return
	 * @throws KMException
	 */
	public HduInfo getTvWallChnInfo(String id, String confe164, int eqpid)throws KMException{
		int ssid = this.tryGetSSIDByID(id);
		GetTvWallChnInfoRequest request = new GetTvWallChnInfoRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setEqpid(eqpid);

		GetTvWallChnInfoResponse response = (GetTvWallChnInfoResponse) this.sendRequest(request);
		return response.getHduInfo();
	}



	
	/**
	 * 终端开始上墙（会议平台4.7版本调用）
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param eqpid Eqpid电视墙列表里有
	 * @param mtinfo 终端IP或者e164号（会议平台4.7有效）
	 * @param chn 电视墙通道(0~1)（会议平台4.7有效）
	 * @throws KMException
	 */
	public void startLookTVWall(String id, String confe164, int eqpid, String mtinfo, int chn) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartLookTVWallRequest request = new StartLookTVWallRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setEqpid(String.valueOf(eqpid));
		request.setMtinfo(mtinfo);
		request.setChn(chn);
		
		this.sendRequest(request);//StartLookTVWallResponse
	}
	
	/**
	 * 终端开始上墙
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param eqpid 电视墙ID
	 * @param mtinfo 终端IP或者e164号（会议平台4.7有效）
	 * @param chn 电视墙通道(0~1)（会议平台4.7有效）
	 * @param mode 电视墙模式:1-选看；2-四分屏(仅传统会议有效)；3-单通道轮询 
	 * @param specificMembertype 选看类型:1-指定；2-发言人跟随；3-主席跟随；4-会议轮询跟随；6-选看画面合成；10-选看双流
	 * @param specificVmpid 选看画面合成id（仅membertype为 6-选看画面合成 时生效）最大字符长度：48个字节
	 * @param pollNum 轮询次数
	 * @param pollMode 轮询方式 1-仅图像；3-音视频轮询
	 * @param pollKeeptime 轮询间隔（秒）
	 * @param chnss 通道对应的终端信息
	 * @throws KMException
	 */
	public void startLookTVWall(String id, String confe164, String eqpid, String mtinfo, int chn, 
			int mode, int specificMembertype, String specificVmpid, int pollNum, int pollMode, int pollKeeptime, List<Chns> chnss) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartLookTVWallRequest request = new StartLookTVWallRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setEqpid(eqpid);
		request.setMtinfo(mtinfo);
		request.setChn(chn);
		
		request.setMode(mode);
		request.setSpecificMembertype(specificMembertype);
		request.setSpecificVmpid(specificVmpid);
		request.setPollNum(pollNum);
		request.setPollMode(pollMode);
		request.setPollKeeptime(pollKeeptime);
		request.setChnss(chnss);
		
		this.sendRequest(request);//StartLookTVWallResponse
	}
	
	public void stopLookTVWall(String id, String confe164, int eqpid, int chn) throws KMException {
		stopLookTVWall(id, confe164, String.valueOf(eqpid), chn);
	}
	
	/**
	 * 终端停止上墙
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param eqpid Eqpid电视墙列表里有
	 * @param chn 录像文件名称 电视墙通道(0~1)
	 * @throws KMException
	 */
	public void stopLookTVWall(String id, String confe164, String eqpid, int chn) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StopLookTVWallRequest request = new StopLookTVWallRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setEqpid(eqpid);
		request.setChn(chn);
		
		this.sendRequest(request);//StopLookTVWallResponse
	}
	
	/**
	 * 建立码流交换(选看终端)
	 * @param id 会议平台标识
	 * @param confe164 会议E164号
	 * @param mtsrcinfo 源终端e164号
	 * @param mtdstinfo 目标终端e164号
	 * @param mode 选看模式 1：视频 2：音频 3：音视频
	 * @throws KMException
	 */
	public void startStreamexChange(String id, String confe164, String mtsrcinfo, String mtdstinfo,int mode) throws KMException {
		startStreamexChange(id, confe164, mtsrcinfo, mtdstinfo, mode, 0);
	}
	
	/**
	 * 建立码流交换(选看终端)
	 * @param id 会议平台标识
	 * @param confe164 会议E164号
	 * @param mtsrcinfo 源终端e164号
	 * @param mtdstinfo 目标终端e164号
	 * @param mode 选看模式 1：视频 2：音频 3：音视频
	 * @param type 选看源类型：1-终端， 2-画面合成（5.0 接口有效）
	 * @throws KMException
	 */
	public void startStreamexChange(String id, String confe164, String mtsrcinfo, String mtdstinfo,int mode,int type) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StartStreamexChangeRequest request = new StartStreamexChangeRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtsrcinfo(mtsrcinfo);
		request.setMtdstinfo(mtdstinfo);
		request.setMode(mode);
		request.setType(type);
		
		this.sendRequest(request);//StartStreamexChangeResponse
	}
	
	/**
	 * 停止码流交换(停止选看终端)
	 * @param id 会议平台标识
	 * @param confe164 会议E164号
	 * @param mtdstinfo 目标终端e164号
	 * @param mode 选看模式 1：视频 2：音频 3：音视频
	 * @throws KMException
	 */
	public void stopStreamexChange(String id, String confe164, String mtdstinfo,int mode) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StopStreamexChangeRequest request = new StopStreamexChangeRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtdstinfo(mtdstinfo);
		request.setMode(mode);
		
		this.sendRequest(request);//StopStreamexChangeResponse
	}
	
	/**
	 * 发送短消息（字幕）
	 * @param id 会议平台标识
	 * @param confe164 会议E164号
	 * @param mtnum 收到消息的终端数
	 * @param content 消息内容
	 * @param type 消息类型 0：短消息 1：翻页字幕 2：滚动字幕 3：静态文本
	 * @param rate 滚动速率 1：最慢 2：稍慢 3：中速 4：稍快 5：最快
	 * @param times 滚动次数
	 * @param mtinfos 滚动次数
	 * @throws KMException
	 */
	public void sendMsg(String id, String confe164, int mtnum, String content, int type, int rate, int times, Set<String> mtinfos) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		SendMsgRequest request = new SendMsgRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtnum(mtnum);
		request.setContent(content);
		request.setRate(rate);
		request.setTimes(times);
		request.setType(type);
		request.setMtinfos(mtinfos);
		
		this.sendRequest(request);//SendMsgResponse
	}
	
	/**
	 * 停止短消息（停止字幕）
	 * @param id 会议平台标识
	 * @param confe164 会议E164号
	 * @param mtnum 收到消息的终端数
	 * @param mtinfos 滚动次数
	 * @throws KMException
	 */
	public void stopMsg(String id, String confe164, int mtnum, Set<String> mtinfos) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		StopMsgRequest request = new StopMsgRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setMtnum(mtnum);
		request.setMtinfos(mtinfos);
		
		this.sendRequest(request);//StopMsgResponse
	}
	
	/**
	 * 获取本级会议终端列表信息
	 * @param id
	 * @param confe164
	 * @throws KMException
	 */
	public List<Mts> getLocalMtList(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetLocalMtListRequest request = new GetLocalMtListRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		GetLocalMtListResponse response = (GetLocalMtListResponse)this.sendRequest(request);
		return response.getMtsList();
	}
	
	/**
	 * 获取视频会议列表（中间件5.0接口）
	 * 2019-06-03 增加请求参数，这里适配已调用当前接口的系统
	 * @param id
	 * @param count 最大个数
	 * @return
	 * @throws KMException
	 */
	public List<Confinfo> getConfList(String id, int count) throws KMException {
		return getConfList(id, count, 0);
	}
	
	/**
	 * 获取视频会议列表（中间件5.0接口）
	 * @param id
	 * @param count 最大个数
	 * @param start 起始序号
	 * @return
	 * @throws KMException
	 */
	public List<Confinfo> getConfList(String id, int count, int start) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetConfListRequest request = new GetConfListRequest();
		request.setSsid(ssid);
		
		request.setStart(start);
		request.setCount(count);
		
		GetConfListResponse response = (GetConfListResponse)this.sendRequest(request);
		return response.getConfs();
	}
	
	/**
	 * 获取终端选看列表(5.0接口)
	 * @param id
	 * @param confe164
	 * @return
	 * @throws KMException
	 */
	public List<Inspection> getInspectionList(String id, String confe164) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		GetInspectionListRequest request = new GetInspectionListRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		
		GetInspectionListResponse response = (GetInspectionListResponse)this.sendRequest(request);
		return response.getInsList();
	}
	
	/**
	 * 画面合成状态查询
	 * @param mcuId
	 * @param e164
	 * @return
	 * @throws KMException
	 */
	public GetconfvmpstatusResponse getconfvmpstatus(String mcuId, String e164) throws KMException {
		GetconfvmpstatusRequest request = new GetconfvmpstatusRequest();
		int ssid = this.tryGetSSIDByID(mcuId);
		request.setSsid(ssid);
		request.setE164(e164);
		
		GetconfvmpstatusResponse response = (GetconfvmpstatusResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 查询混音器状态
	 * @param mcuId
	 * @param e164
	 * @return
	 * @throws KMException
	 */
	public GetconfmaxstatusResponse getconfmaxstatus(String mcuId, String e164) throws KMException {
		GetconfmaxstatusRequest request = new GetconfmaxstatusRequest();
		int ssid = this.tryGetSSIDByID(mcuId);
		request.setSsid(ssid);
		request.setE164(e164);
		
		GetconfmaxstatusResponse response = (GetconfmaxstatusResponse) this.sendRequest(request);
		return response;
	}
	
	/**
	 * 延长会议时长
	 * @param id 会议平台标识
	 * @param confe164 会议e164号
	 * @param delaytime 延长时间（分钟）
	 * @throws KMException
	 */
	public void delayConf(String id, String confe164, int delaytime) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		DelayConfRequest request = new DelayConfRequest();
		request.setSsid(ssid);
		request.setConfe164(confe164);
		request.setDelaytime(delaytime);
		
		this.sendRequest(request);//DelayConfResponse
	}
	
	/**
	 * 发布直播
	 * @param id 会议平台标识
	 * @param confe164 会议E164
	 * @param mtid 终端入会标识
	 * @param type 码流类型
	 * @throws KMException
	 */
	public String getRtspUrl(String id, String confe164, String mtid, int type) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		Mcu mcu = this.getMcuByID(id);
		
		GetRtspUrlRequest request = new GetRtspUrlRequest();
		request.setSsid(ssid);
		
		request.setConfe164(confe164);
		request.setMtid(mtid);
		request.setType(type);
		if(mcu != null){
			request.setMcuIp(mcu.getIp());
			request.setMcuPort(mcu.getPort());
			request.setMcuUsername(mcu.getUser());
			request.setMcuPassword(mcu.getPassword());
			request.setMcuKey(mcu.getSdkKey());
			request.setMcuSecret(mcu.getSdkSecret());
			if(Mcu.MCU_VERSION_5.equals(mcu.getVersion())){
				request.setDevtype(DeviceType.MCU5.getValue());
			}else{
				request.setDevtype(DeviceType.MCU.getValue());
			}
		}
		
		GetRtspUrlResponse response = (GetRtspUrlResponse)this.sendRequest(request);
		return response.getUrl();
	}
	
	/**
	 * 取消直播
	 * @param id
	 * @param url 直播路径
	 * @throws KMException
	 */
	public void delRtspUrl(String id, String url) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		Mcu mcu = this.getMcuByID(id);
		
		DelRtspUrlRequest request = new DelRtspUrlRequest();
		request.setSsid(ssid);
		
		request.setUrl(url);
		if(mcu != null){
			if(Mcu.MCU_VERSION_5.equals(mcu.getVersion())){
				request.setDevtype(DeviceType.MCU5.getValue());
			}else{
				request.setDevtype(DeviceType.MCU.getValue());
			}
		}
		
		this.sendRequest(request);//DelRtspUrlResponse
	}
	
	/**
	 * 获取平台域
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public List<McuDomain> getDomains(String id) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		
		GetDomainsRequest request = new GetDomainsRequest();
		request.setSsid(ssid);
		
		GetDomainsResponse response = (GetDomainsResponse)this.sendRequest(request);
		return response.getDomains();
	}
	
	/**
	 * 获取5.0终端列表
	 * @param id
	 * @param domainMoid 用户域ID
	 * @return
	 * @throws KMException
	 */
	public List<Terminal> getTerminals(String id, String domainMoid) throws KMException {
		List<Terminal> list = new ArrayList<Terminal>();
		
		int start = 0;//获取终端列表的起始位置, 0表示第一个终端, 默认为0
		int count = 10;//取的终端的个数, 即包括start在内的后count个终端
		while(true){
			
			GetTerminalsResponse response =  getTerminals(id, domainMoid, start, count);
			
			int curSize = 0;
			List<Terminal> curTerminals = response.getTerminals();
			int total = response.getTotal();
			if(response.getTerminals() != null){
				list.addAll(curTerminals);
				curSize = curTerminals.size();
			}
			
			if(curSize < count || total == list.size()){
				break;
			}else{
				start += count;
			}
		}
		
		return list;
	}
	
	/**
	 * 获取5.0终端列表
	 * @param id
	 * @param domainMoid 用户域ID
	 * @param start 获取终端列表的起始位置, 0表示第一个终端, 默认为0
	 * @param count 取的终端的个数, 即包括start在内的后count个终端
	 * @return
	 * @throws KMException
	 */
	public GetTerminalsResponse getTerminals(String id, String domainMoid, int start, int count) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		
		GetTerminalsRequest request = new GetTerminalsRequest();
		request.setSsid(ssid);
		
		request.setMoid(domainMoid);
		request.setStart(start);
		request.setCount(count);
		
		GetTerminalsResponse response = (GetTerminalsResponse)this.sendRequest(request);
		return response;
	}
	
	/**
	 * 获取三代高清终端列表(非受管)
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public List<Terminal> getOldTerminals(String id) throws KMException {
		List<Terminal> list = new ArrayList<Terminal>();
		
		int start = 0;
		int count = 10;//取的终端的个数, 即包括start在内的后count个终端
		while(true){
			
			GetOldTerminalsResponse response =  getOldTerminals(id, start, count);
			
			int curSize = 0;
			List<Terminal> curTerminals = response.getTerminals();
			int total = response.getTotal();
			if(response.getTerminals() != null){
				list.addAll(curTerminals);
				curSize = curTerminals.size();
			}
			
			if(curSize < count || total == list.size()){
				break;
			}else{
				start += count;
			}
		}
		
		return list;
	}
	
	/**
	 * 获取三代高清终端列表(非受管)
	 * @param id
	 * @param start 获取终端列表的起始位置, 0表示第一个终端, 默认为0
	 * @param count 取的终端的个数, 即包括start在内的后count个终端
	 * @return
	 * @throws KMException
	 */
	public GetOldTerminalsResponse getOldTerminals(String id, int start, int count) throws KMException {
		int ssid = this.tryGetSSIDByID(id);
		
		GetOldTerminalsRequest request = new GetOldTerminalsRequest();
		request.setSsid(ssid);
		
		request.setStart(start);
		request.setCount(count);
		
		GetOldTerminalsResponse response = (GetOldTerminalsResponse)this.sendRequest(request);
		return response;
	}
}
