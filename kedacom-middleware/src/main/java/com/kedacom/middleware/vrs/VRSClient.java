package com.kedacom.middleware.vrs;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import com.kedacom.middleware.vrs.domain.*;
import com.kedacom.middleware.vrs.request.*;
import com.kedacom.middleware.vrs.response.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * “录播服务器”接口访问。
 * @author LinChaoYu
 * alter by ycw 2021/5/10
 */
public class VRSClient {
	private static final Logger log = Logger.getLogger(VRSClient.class);
	private KM km;

	/**
	 * 会话管理。根据目前的设计，一个VRS最多一个会话，好比一个监控平台只有一个主链。
	 */
	
	private VRSSessionManager sessionManager;
	/**
	 * “VRS”信息集合。
	 * key：VRS标识， value:VRS详细信息
	 */
	private Hashtable<String, VRS> vrsCacheByID = new Hashtable<String, VRS>();
	/**
	 * 服务器数据和状态监听器
	 */
	private List<VRSNotifyListener> listeners = new ArrayList<VRSNotifyListener>();
	
	/**
	 * VRS连接监控线程
	 */
	private VRSConnMonitorThread vrsConnMonitorThread;

	
	public VRSClient(KM km){
		this.km = km;

		this.sessionManager = new VRSSessionManager(this);
		VRSClientListener listener = new VRSClientListener(this);
		
		/*
		 * TODO 如果当VRSClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
		 * 但由于实际应用中VRSClient只有一个，所以此处暂不处理
		 */
		TCPClient tcpClient = (TCPClient)km.getClient();
		tcpClient.addListener(listener);
		
	}

	/**
	 * 增加监听器
	 * @see #removeListener(VRSNotifyListener)
	 * @param listener
	 * @return
	 */
	public boolean addListener(VRSNotifyListener listener){
		return this.listeners.add(listener);
	}
	
	/**
	 * 删除监听器
	 * @param listener
	 * @see #addListener(VRSNotifyListener)
	 * @return
	 */
	public boolean removeListener(VRSNotifyListener listener){
		return this.listeners.remove(listener);
	}
	
	public List<VRSNotifyListener> getAllListeners(){
		List<VRSNotifyListener> list = new ArrayList<VRSNotifyListener>(this.listeners.size());
		list.addAll(listeners);
		return list;
	}
	
	/**
	 * 获取会话管理器
	 * @return
	 */
	public VRSSessionManager getSessionManager() {
		return sessionManager;
	}
	/**
	 * 增加VRS信息。
	 * @param vrs
	 */
	public void addVRS(VRS vrs){
		this.vrsCacheByID.put(vrs.getId(), vrs);
		this.reStartConnect(vrs.getId());
	}
	
	/**
	 * 更新VRS信息
	 */
	public void updateVRS(VRS vrs){
		VRS old = this.vrsCacheByID.get(vrs.getId());
		if( !(old.getIp().equals(vrs.getIp()))
				|| !(old.getVersion().equals(vrs.getVersion()))
				){
			//需要重新连接
			old.update(vrs);
			this.logout(vrs.getId());
			this.reStartConnect(vrs.getId());
		}else{
			old.update(vrs);
			
			//解决在vrsCacheByID中已经有该VRS的信息，但是VRS未连接，如果VRS信息不发生改变，那么VRS再也连接不上
			VRSSession session = sessionManager.getSessionByID(vrs.getId());
			if(session == null || !session.isLogin()){//当前VRS未连接
				this.reStartConnect(vrs.getId());
			}
		}
	}
	
	/**
	 * 删除VRS信息。
	 * @return
	 */
	private void removeVRS(VRS vrs){
		if(vrs == null){
			return;
		}
		String id = vrs.getId();
		
		if(vrsConnMonitorThread != null){
			vrsConnMonitorThread.removeVRSId(id);
		}
		
		VRSSession session = sessionManager.getSessionByID(id);
		if(session != null){
			this.logout(id);
		}
		
		this.vrsCacheByID.remove(id);
	}
	
	/**
	 * 设置（添加或修改）VRS信息
	 * @param vrs
	 */
	public void setVRS(VRS vrs){
		VRS old = this.vrsCacheByID.get(vrs.getId());
		if(old != null){
			updateVRS(vrs);
		}else{
			addVRS(vrs);
		}
	}
	
	/**
	 * 删除VRS信息。
	 * @param id
	 * @see #addVRS(VRS)
	 * @see #updateVRS(VRS)
	 * @return
	 */
	public VRS removeVRSByID(String id){
		VRS vrs = vrsCacheByID.get(id);
		this.removeVRS(vrs);
		return vrs;
	}

	private IClient getClient(){
		return km.getClient();
	}

	/**
	 * 停止
	 */
	public void stop(){
		this.stopReStartConnect();
		this.sessionManager.clear();
	}
	
	/**
	 * 获取会话标识
	 * @param id VRS的标识，{@link VRS#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByID(String id) throws ConnectException {
		VRSSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("VRS未连接");
		}
		if(!session.isLogin()){
			if(session.getStatus() == VRSSessionStatus.CONNECTING){
				throw new ConnectException("VRS正在登录中");
			}else if(session.getStatus() == VRSSessionStatus.FAILED){
				throw new ConnectException("VRS登录失败");
			}else if(session.getStatus() == VRSSessionStatus.DISCONNECT){
				throw new ConnectException("VRS连接中断");
			}else{
				throw new ConnectException("VRS未登录");
			}
		}
		int ssid = session.getSsid();
		return ssid;
	}
	
	/**
	 * 获取会话标识
	 * @param id VRS的标识，{@link VRS#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByIDForLogout(String id) throws ConnectException {
		VRSSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("VRS未连接");
		}
		
		return session.getSsid();
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
	 * 在单独的线程中重新连接VRS
	 * @param id VRS的标识
	 */
	public void reStartConnect(String id){
		
		if(this.isLogin(id)){
			this.logout(id);
		}
		
		boolean newThread = false;
		if(vrsConnMonitorThread == null || !vrsConnMonitorThread.isWork()){
			vrsConnMonitorThread = new VRSConnMonitorThread(this);
			vrsConnMonitorThread.setTimeout(15000);
			vrsConnMonitorThread.setName("VRS-ConnMonitor");
			vrsConnMonitorThread.setDaemon(true);
			newThread = true;
		}
		vrsConnMonitorThread.addVRSId(id);
		if(newThread){
			vrsConnMonitorThread.start();
		}
	}
	
	private void stopReStartConnect(){
		if(vrsConnMonitorThread != null){
			vrsConnMonitorThread.stop();
			vrsConnMonitorThread = null;
		}
	}
	
	/**
	 * 返回指定的VRS是否已经登录
	 * @param id
	 * @return
	 */
	public boolean isLogin(String id){
		VRSSession session = sessionManager.getSessionByID(id);
		boolean login = session != null && session.isLogin();
		return login;
	}
	
	private void refreshTime(int ssid){
		VRSSession session = sessionManager.getSessionBySsid(ssid);
		if(session != null){
			session.refreshTime();
		}
	}
	private VRSResponse sendRequest(VRSRequest request) throws KMException{
		
		int ssid = request.getSsid();
		this.refreshTime(ssid);
		
		VRSResponse response = (VRSResponse)getClient().sendRequest(request);
		
		int err = response.getErrorcode();
		if(err > 0){
			//远端返回错误码
			RemoteException e = new RemoteException("错误码:" + err);
			e.setErrorcode(err);
			throw e;
		}
		
		return response;
	}
	/**
	 * 登录
	 * @param ip VRS的IP地址
	 * @param version 软件版本
	 * @return 登录成功返回会话ID，登录失败返回-1或抛出异常
	 * @see #login(String)
	 * @see #logout(String)
	 * @throws KMException 
	 */
	public int login(String ip, String user, String pwd,String version, Integer port) throws KMException{
		
		VRSSession session = sessionManager.getSessionByIP(ip);
		if(session != null){
			/*
			 * VRS已经登录过。
			 * 目前程序设计为，一个VRS只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			return session.getSsid();
		}
		
		int ssid = this.login0(ip, user, pwd, version ,port);
		if(ssid > 0){
			//登录成功
			session = new VRSSession();
			session.setSsid(ssid);
			sessionManager.putSession(session);
		}
		
		return ssid;
	}

	/**
	 * 根据ID登录VRS
	 * @see #login(String)
	 * @see #logout(String)
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public int login(String id) throws KMException{
		VRS vrs = vrsCacheByID.get(id);
		if(vrs == null){
			throw new DataException("VRS信息不存在,ID=" + id);
		}
		return this.loginByVRS(vrs);
	}
	
	//最终实现登录的方法
	private int loginByVRS(VRS vrs) throws KMException{
		
		String id = vrs.getId();
		String ip = vrs.getIp();
		String user = vrs.getUsername();
		String pwd = vrs.getPassword();
		String version = vrs.getVersion();
		Integer port = vrs.getPort();
		
		VRSSession session = sessionManager.getSessionByID(id);
		if(session != null){
			//已登录
			return session.getSsid();
		}
		
		int ssid = this.login(ip, user, pwd, version,port);
		if(ssid > 0){
			//登录成功
			session = new VRSSession();
			session.setSsid(ssid);
			session.setLastTime(System.currentTimeMillis());
			session.setStatus(VRSSessionStatus.CONNECTED);
			session.setVrs(vrs);
			sessionManager.putSession(session);
			
		}
		
		return ssid;
	}

	private int login0(String ip, String user, String pwd, String version,Integer port) throws KMException{
		LoginRequest request = new LoginRequest();
		request.setIp(ip);
		request.setUser(user);
		request.setPwd(pwd);
		request.setPort(port);
		/*if(VRS.VRS_VERSION_5_1.equals(version))
			request.setDevtype(DeviceType.VRS51.getValue());*/
		request.setDevtype(Integer.parseInt(version));
		
		LoginResponse response = (LoginResponse)this.sendRequest(request);
		int ssid = response.getSsid();
		return ssid;
	}

	/**
	 * 获取会话标识。
	 * @param vrsIP vrs的IP地址
	 * @return 如果VRS已连接，返回ssid，否则返回-1
	 */
	public int getSSIDByIp(String vrsIP){
		VRSSession session = sessionManager.getSessionByIP(vrsIP);
		return session != null ? session.getSsid() : - 1;
	}
	
	/**
	 * 获取会话标识。
	 * <pre>
	 *     如果VRS已经登录，直接返回会话标识;
	 *     如果VRS未登录，则先登录，然后返回会话标识。
	 * </pre>
	 * @param id VRS的标识，{@link VRS#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 * @throws KMException 
	 */
	private int tryLogin(String id) throws KMException{
		int ssid;
		VRSSession session = sessionManager.getSessionByID(id);
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
	 * 登出VRS
	 * @param id
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
			VRSSession session = sessionManager.removeSession(ssid);
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
	
	/**
	 * 分页查询录像列表
	 * @param id
	 * @param pagenum 查询的页码（从1开始）
	 * @param pagesize 每页的大小
	 * @param recname 模糊匹配的录像名字
	 * @return
	 * @throws KMException
	 */
	public VrsQueryRecResponse queryRecList(String id, int pagenum, int pagesize, String recname) throws KMException{
		int ssid = this.tryLogin(id);
		
		VrsQueryRecRequest request = new VrsQueryRecRequest();
		request.setSsid(ssid);
		request.setPagenum(pagenum);
		request.setPagesize(pagesize);
		request.setIncludename(recname);
		
		VrsQueryRecResponse response = (VrsQueryRecResponse)this.sendRequest(request);
		
		return response;
	}

	/**
	 * 分页查询http录像列表
	 * @author ycw
	 * @param id
	 * @param pagenum 查询的页码（从1开始）
	 * @param pagesize 每页的大小
	 * @param recname 模糊匹配的录像名字
	 * @return
	 * @throws KMException
	 */
	public VrsQueryHttpRecResponse vrsQueryHttpRec(String id, int pagenum, int pagesize, String recname) throws KMException{
		int ssid = this.tryLogin(id);

		VrsQueryHttpRecRequest request = new VrsQueryHttpRecRequest();
		request.setSsid(ssid);
		request.setPagenum(pagenum);
		request.setPagesize(pagesize);
		request.setIncludename(recname);

		VrsQueryHttpRecResponse response = (VrsQueryHttpRecResponse)this.sendRequest(request);

		return response;
	}

	/**
	 * 直播查询
	 * @author ycw
	 * @param id
	 * @param pagenum 查询的页码（从1开始）
	 * @param pagesize 每页的大小
	 * @param recname 开始录像时候填写的名称
	 * @return
	 * @throws KMException
	 */
	public VrsQueryLiveResponse vrsQueryLive(String id, int pagenum, int pagesize, String recname) throws KMException{
		int ssid = this.tryLogin(id);

		VrsQueryLiveRequest request = new VrsQueryLiveRequest();
		request.setSsid(ssid);
		request.setPagenum(pagenum);
		request.setPagesize(pagesize);
		request.setIncludename(recname);

		VrsQueryLiveResponse response = (VrsQueryLiveResponse)this.sendRequest(request);

		return response;
	}



	/**
	 * @author ycw
	 * 申请录像室
	 * @param id
	 * @param rectaskname 录像任务名称
	 * @param isburn 是否刻录
	 * @param conferencetype
	 * 0：单点会议
	 * 1：点对点会议
	 * 说明：
	 * 单点会议只录本地流
	 * 点对点会议时登录两个终端取本地流
	 * 点对点会议时不在会议中会主动组会
	 * @param streammode
	 * 码流方式
	 * 0：中间件申请
	 * 1：SDK申请
	 * 2：被动接收
	 * @param mtinfo 终端信息
	 * @param streamrtcpport
	 * @param ctrlmt 是否控制终端组会，启用双流等 0：否 1：是
	 * @return
	 * @throws KMException
	 */
	public int getRoom(String id, String rectaskname, int isburn, int conferencetype, int streammode, List<MtInfo> mtinfo, List<StreamTcpPort> streamrtcpport, int ctrlmt) throws KMException{
		int ssid = this.tryLogin(id);

		GetRoomRequest request = new GetRoomRequest();
		request.setSsid(ssid);
		request.setRectaskname(rectaskname);
		request.setIsburn(isburn);
		request.setConferencetype(conferencetype);
		request.setStreammode(streammode);
		request.setMtinfo(mtinfo);
		request.setStreamrtcpport(streamrtcpport);
		request.setCtrlmt(ctrlmt);

		GetRoomResponse response = (GetRoomResponse) this.sendRequest(request);
		return response.getRoomid();
	}

	/**
	 * 录像室控制
	 * @author ycw
	 * @param id
	 * @param ctrltype 控制类型
	 * 1：开始录像
	 * 2：暂停录像
	 * 3：恢复录像
	 * 4：停止录像
	 * 5：开启双流
	 * 6：停止双流
	 * @param roomid 录像室Id
	 * @param islocal 是否本地 0：本地 1：远端 备注： 当ctrltype 为5或6有效
	 * @throws KMException
	 */
	public void roomCtrl(String id , int ctrltype, int roomid, int islocal)throws KMException{
		int ssid = this.tryLogin(id);

		RoomCtrlRequest request = new RoomCtrlRequest();
		request.setSsid(ssid);
		request.setCtrltype(ctrltype);
		request.setRoomid(roomid);
		if(ctrltype==5||ctrltype==6){
			request.setIslocal(islocal);
		}

		this.sendRequest(request);
	}

	/**
	 * 补刻
	 * @author ycw
	 * @param id
	 * @param opertype 	刻录操作类型： 1：补刻 2：中断补刻
	 * @param rectaskid 录像任务ID
	 * @throws KMException
	 */
	public void patchBurn(String id, int opertype, int rectaskid)throws KMException{
		int ssid = this.tryLogin(id);

		PatchBurnRequest request = new PatchBurnRequest();
		request.setSsid(ssid);
		request.setOpertype(opertype);
		request.setRectaskid(rectaskid);

	    this.sendRequest(request);

	}

	/**
	 * 播放录像
	 * @author ycw
	 * @param id
	 * @param rectaskid 录像任务id
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param dstAddrs 放像视频信息
	 * @return
	 */
	public int recPlay(String id, int rectaskid, int starttime, int endtime, List<DstAddr> dstAddrs)throws KMException{
		int ssid = this.tryLogin(id);

		RecPlayRequest request = new RecPlayRequest();
		request.setSsid(ssid);
		request.setRectaskid(rectaskid);
		request.setStarttime(starttime);
		request.setEndtime(endtime);
		request.setDstAddrs(dstAddrs);

		RecPlayResponse response = (RecPlayResponse) this.sendRequest(request);
		return response.getPlaytaskid();
	}

	/**
	 * 查询录像任务
	 * @author ycw
	 * @param id
	 * @param querytype 查询类型 0:获取总条目数以及按照索引和返回数目获取对应的条目 1：获取总条目数 2：按照索引和返回数目获取对应的条目
	 * @param querymask 查询条件 0：无效 // 0x0001 : by 时间 // 0x0002 : by 关键字  // 0x0004 :by 组名
	 * @param starttimes 开始时间
	 * @param endtimes 结束时间
	 * @param content 关键字内容
	 * @param maxnum 最大查询数目
	 * @param startindex 查询起始下标
		 * @param sort 排序 (按时间) 0：降序 1：升序
	 * @return
	 * @throws KMException
	 */
	public QueryRecTaskResponse queryRecTask(String id, int querytype, int querymask, String starttimes, String endtimes, String content, int maxnum, int startindex, int sort)throws KMException{
		int ssid = this.tryLogin(id);

		QueryRecTaskRequest request = new QueryRecTaskRequest();
		request.setSsid(ssid);
		request.setQuerytype(querytype);
		request.setQuerymask(querymask);
		request.setStarttimes(starttimes);
		request.setEndtimes(endtimes);
		request.setContent(content);
		request.setMaxnum(maxnum);
		request.setStartindex(startindex);
		request.setSort(sort);

		QueryRecTaskResponse response = (QueryRecTaskResponse) this.sendRequest(request);
		return response;
	}

	/**
	 * 刻录附件
	 * @author ycw
	 * @param id
	 * @param recorddirname 笔录/附件目录名
	 * @throws KMException
	 */
	public void burnRecord(String id, String recorddirname)throws KMException{
		int ssid = this.tryLogin(id);

		BurnRecordRequest request = new BurnRecordRequest();
		request.setSsid(ssid);
		request.setRecorddirname(recorddirname);

		this.sendRequest(request);
	}

	/**
	 * 录像室状态获取
	 * @author ycw
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public List<RoomStatus> getRoomStatus(String id)throws KMException{
		int ssid = this.tryLogin(id);

		GetRoomStatusRequest request = new GetRoomStatusRequest();
		request.setSsid(ssid);

		GetRoomStatusResponse response = (GetRoomStatusResponse) this.sendRequest(request);
		return response.getRoomStatuses();
	}

	/**
	 * 播放录像控制
	 * @author ycw
	 * @param id
	 * @param playtaskid 放像任务id
	 * @param vcrcmd 放像控制命令 0： 无效命令 1： 停止放像 2： 暂停 3： 重新开始放像,对应暂定命令 4： 单帧播放 5： 关键帧播放 6： 倒放 7： 设置播放速率 8： 拖拉定位绝对时间 9： 前跳  相对时间  10： 后跳  相对时间
	 * @param dragtimes 拖拉时间
	 * @param jumptime 跳动时间
	 * @param playrate 播放速度 1：1/16速播放 2 ：1/8速播放 3：1/4速播放 4: 1/2速播放 5:正常速播放 6: 2倍速播放 7: 4倍速播放 8: 8倍速播放 9: 16倍速播放
	 * @throws KMException
	 */
	public void recPlayVcr(String id, int playtaskid, int vcrcmd, String dragtimes, int jumptime, int playrate)throws KMException{
		int ssid = this.tryLogin(id);

		RecPlayVcrRequest request = new RecPlayVcrRequest();
		request.setSsid(ssid);
		request.setPlaytaskid(playtaskid);
		request.setVcrcmd(vcrcmd);
		request.setDragtimes(dragtimes);
		request.setJumptime(jumptime);
		request.setPlayrate(playrate);

		this.sendRequest(request);
	}

	/**
	 * 系统状态获取
	 * @author ycw
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public SysStatus getSysStatus(String id)throws KMException{
		int ssid = this.tryLogin(id);

		GetSysStatusRequest request = new GetSysStatusRequest();
		request.setSsid(ssid);

		GetSysStatusResponse response = (GetSysStatusResponse) this.sendRequest(request);
		SysStatus sysStatus = new SysStatus();
		sysStatus.setDvdStatuses(response.getDvdStatuses());
        sysStatus.setBurnworkmode(response.getBurnworkmode());
        return sysStatus;
	}

	/**
	 * 中间件配置
	 * @author ycw
	 * @param id
	 * @param ip 中间件IP
	 * @param port 中间件端口号
	 * @throws KMException
	 */
	public void gateWayCfg(String id, String ip, int port)throws KMException{
		int ssid = this.tryLogin(id);

		GateWayCfgRequest request = new GateWayCfgRequest();
		request.setSsid(ssid);
		request.setIp(ip);
		request.setPort(port);

		this.sendRequest(request);
	}

	/**
	 * Dvd封盘配置
	 * @author ycw
	 * @param id
	 * @param dvdautolock DVD是否自动封盘 0：不封 1：封盘
	 * @throws KMException
	 */
	public void dvdLockCfg(String id,int dvdautolock)throws KMException{
		int ssid = this.tryLogin(id);

		DvdLockCfgRequest request = new DvdLockCfgRequest();
		request.setSsid(ssid);
		request.setDvdautolock(dvdautolock);

		this.sendRequest(request);
	}

	/**
	 * 刻录合成通道配置
	 * @author ycw
	 * @param id
	 * @param isburnmerge 是否刻录合成通道 0：不刻录 1：刻录
	 * @throws KMException
	 */
	public void mergeCfg(String id,int isburnmerge)throws KMException{
		int ssid = this.tryLogin(id);

		MergeCfgRequest request = new MergeCfgRequest();
		request.setSsid(ssid);
		request.setIsburnmerge(isburnmerge);

		this.sendRequest(request);
	}




}
