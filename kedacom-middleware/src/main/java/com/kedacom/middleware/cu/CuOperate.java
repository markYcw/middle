package com.kedacom.middleware.cu;

import com.kedacom.middleware.cu.domain.*;
import com.kedacom.middleware.cu.request.*;
import com.kedacom.middleware.cu.response.*;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.KMException;
import keda.common.util.TimeUtil;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * 监控平台操作类。提供访问监控平台的绝大多数功能的调用。
 * @author TaoPeng
 *
 */
public class CuOperate {

	private CuClient client;
	public CuOperate(CuClient client){
		this.client = client;
	}

	protected CuResponse sendRequest(int cuId, CuRequest request) throws KMException {

		boolean needSSid = true;//标识是否需要ssid。大部分功能需要登录并获取ssid后才能使用；
		if(request instanceof LoginRequest
				|| request instanceof LogoutRequest){
			needSSid = false;
		}
		if(needSSid){
			int ssid = client.tryGetSSIDByID(cuId);
			request.setSsid(ssid);
		}
		
		return client.sendRequest(request);
	}
	
	/**
	 * 登录
	 * @param ip
	 * @param username
	 * @param password
	 * @return
	 * @throws KMException
	 */
	protected LoginResponse login(int version, String ip, int port, String username, String password) throws KMException {
		
		if(port <= 0){
			port = version == Cu.CU1 ? 1722 : 80; //1.0平台端口1722，2.0平台端口80
		}
		
		LoginRequest request = new LoginRequest();
//		request.setDevtype(version);
		if(port==1722){
			request.setDevtype(Cu.CU1);
		}else if(port==80){
			request.setDevtype(Cu.CU2);
		}	
		request.setIp(ip);
		request.setPort(port);
		request.setUser(username);
		request.setPwd(password);
		
		LoginResponse response = (LoginResponse)this.sendRequest(-1, request);
		return response;
	}
	
	/**
	 * 注销
	 * @param ssid
	 * @throws KMException
	 */
	protected void logout(int ssid) throws KMException {
		LogoutRequest request = new LogoutRequest();
		request.setSsid(ssid);

		this.sendRequest(-1, request);
	}
	
	/**
	 * 获取当前监控平台域信息。
	 * @param cuId
	 * @return
	 * @throws ConnectException
	 */
	public String getLocalDomain(int cuId) throws KMException {
		
		GetLocalDomainRequest request = new GetLocalDomainRequest();
		
		GetLocalDomainResponse response = (GetLocalDomainResponse)this.sendRequest(cuId, request);
		String domainId = response.getDomainid();
		return domainId;
		
	}
	
	/**
	 * 获取域列表，包括本级域、下级、下下级、下下下级....。
	 * @param cuId
	 * @return
	 * @throws KMException
	 */
	public List<Domain> listDomain(int cuId) throws KMException {

		ListDomainRequest request = new ListDomainRequest();

		ListDomainResponse response = (ListDomainResponse)this.sendRequest(cuId, request);
		return response.getDomains();
	}
	
	/**
	 * 获取监控平台时间
	 * @param cuId
	 * @return
	 * @throws KMException
	 */
	public long getTime(int cuId) throws KMException {
		return this.getTime(cuId, false);
	}
	
	/**
	 * 获取监控平台时间
	 * @param cuId
	 * @param syncLocal 是否让中间件立即同步本地时间。如果中间件和业务在同一服务器，可以让中间件更新本地服务器时间。
	 * @return
	 * @throws KMException
	 */
	public long getTime(int cuId, boolean syncLocal) throws KMException {
		GetTimeRequest request = new GetTimeRequest();

		GetTimeResponse response = (GetTimeResponse)this.sendRequest(cuId, request);
		return response.getTime();
	}
	
	/**
	 * 加载分组。分组以“通知（Notify）”的方式上报。
	 * @param cuId
	 * @throws KMException
	 */
	public void startLoadDeviceGroup(int cuId) throws KMException {

		GetDeviceGroupRequest request = new GetDeviceGroupRequest();
		this.sendRequest(cuId, request);
		
	}

	/**
	 * 加载设备。设备以“通知（Notify）”的方式上报。
	 * @param cuId
	 * @param groupId 分组ID
	 * @throws KMException
	 */
	public void startLoadDevice(int cuId, String groupId) throws KMException {

		GetDeviceRequest request = new GetDeviceRequest();
		request.setGroupid(groupId);
		
		//2018-12-10 LinChaoYu 外部系统调用设置订阅参数
		DeviceStatusSubscribe deviceStatusSubscribe = client.getSessionManager().getDeviceStatusSubscribe();
		if(deviceStatusSubscribe != null)
			request.setDeviceStatusSubscribe(deviceStatusSubscribe);

		this.sendRequest(cuId, request);
		
	}
	
	/**
	 * 订阅指定设备的消息。订阅的消息将以“通知（Notify)”的方式上报。
	 * @param cuId
	 * @param deviceStatusSubscribe
	 * @throws KMException
	 */
	public void subscribeDeviceStatus(int cuId, String puid, DeviceStatusSubscribe deviceStatusSubscribe) throws KMException {

		Collection<String> puidSet = new HashSet<String>(1);
		puidSet.add(puid);
		this.subscribeDeviceStatus(cuId, puidSet, deviceStatusSubscribe);
		
	}

	/**
	 * 订阅多个设备的消息。订阅的消息将以“通知（Notify)”的方式上报。
	 * @param cuId
	 * @param puidSet
	 * @param deviceStatusSubscribe
	 * @throws KMException
	 */
	public void subscribeDeviceStatus(int cuId, Collection<String> puidSet, DeviceStatusSubscribe deviceStatusSubscribe) throws KMException {

		SubScribeStatusRequest request = new SubScribeStatusRequest();
		request.addPuids(puidSet);
		request.setDeviceStatusSubscribe(deviceStatusSubscribe);
		this.sendRequest(cuId, request);
	}
	
	/**
	 * 将监控平台2.0的设备编号转换为监控平台1.0的设备编号。<br/>
	 * 监控平台2.0的设备编号是<b>“数字+字母”</b>的16进制形式，监控平台1.0的设备编号是<b>“纯数字”</b>（550100000...）的形式。
	 * 
	 * @param puid
	 * @return
	 * @throws KMException
	 */
	public String getKdmno(String puid) throws KMException {
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if(ssid == CuSession.INVALID_SSID){
			throw new KMException("设备所在监控平台未连接");
		}
		int cuId = client.getSessionManager().getCuIDBySSID(ssid);
		GetkdmnoRequest request = new GetkdmnoRequest();
		request.setPuid(puid);

		GetkdmnoResponse response = (GetkdmnoResponse)this.sendRequest(cuId, request);
		return response.getKdmno();
	}
	
	/**
	 * 将监控平台2.0的设备编号转换为监控平台1.0的设备编号。<br/>
	 * 监控平台2.0的设备编号是<b>“数字+字母”</b>的16进制形式，监控平台1.0的设备编号是<b>“纯数字”</b>（550100000...）的形式。
	 * 
	 * @param puid
	 * @return
	 * @throws KMException
	 */
	public String getKdmno(int cuId,String puid) throws KMException {
		GetkdmnoRequest request = new GetkdmnoRequest();
		request.setPuid(puid);
		GetkdmnoResponse response = (GetkdmnoResponse)this.sendRequest(cuId, request);
		return response.getKdmno();
	}

	/**
	 * 查询录像日历。查询的方案：如果在当前平台没有找到录像日历，则查询所有下级平台域，直到第一次找到录像日历。
	 * @param puid 设备号
	 * @param chnid 视频源号（通道号）
	 * @param startDay 开始日期，按天计算，时分秒无效
	 * @param endDay 结束日期，按天计算，时分秒无效
	 * @param type 录像类型 
	 * @return
	 * @throws KMException
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private int[] queryRecdays(String puid, int chnid, Date startDay, Date endDay,int type) throws KMException {
		/*
		 * TODO 此方法有点问题，待完善。主要问题：不管本级平台有没有录像，均会返回的录像日历数据，只不过是全0。
		 */
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if(ssid == CuSession.INVALID_SSID){
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		String localDomain = session.getCmuno();//默认为当前登录平台
		
		int[] flags = this.queryRecdays(localDomain, puid, chnid, startDay, endDay, type);
		if(flags == null || flags.length <= 0){
			//本级平台没找到，查找下级平台
			List<Domain> domains = this.listDomain(session.getCu().getId());
			if(domains != null && domains.size() > 0){
				for(Domain d : domains){
					String domainId = d.getDomainid();
					if(domainId.equalsIgnoreCase(localDomain)){
						//localDomain已经查过，不再重复查。注：listDomain()返回的列表包括localDomain;
						continue;
					}
					flags = this.queryRecdays(domainId, puid, chnid, startDay, endDay,type);
					if(flags != null && flags.length > 0){
						//在某个域已经查找录像日历
						break;
					}
				}
			}
		}
		
		return flags;
	}

	/**
	 * 查询录像日历（仅仅支持平台录像）。一般应用于查询某个月的录像日历。
	 * @param domain 指定查询录像的域
	 * @param puid 设备号
	 * @param chnid 视频源号（通道号）
	 * @param startDay 开始日期，按天计算，时分秒无效
	 * @param endDay 结束日期，按天计算，时分秒无效
	 * @param type 录像类型
	 * @return
	 * @throws KMException
	 */
	@SuppressWarnings("deprecation")
	public int[] queryRecdays(String domain, String puid, int chnid, Date startDay, Date endDay,int type) throws KMException {
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if(ssid == CuSession.INVALID_SSID){
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		
		if(domain == null){
			domain = session.getCmuno();//默认为当前登录平台
		}
		
		startDay = TimeUtil.getDayStart(startDay);
		endDay = TimeUtil.getDayEnd(endDay);
		
		QueryRecdaysRequest request = new QueryRecdaysRequest();
		request.setDomain(domain);
		request.setPuid(puid);
		request.setChnid(chnid);
		request.setStarttime(startDay);
		request.setEndtime(endDay);
		request.setType(type);
		
		QueryRecdaysResponse response = (QueryRecdaysResponse)this.sendRequest(session.getCu().getId(), request);
		int[] flags = response.getRecflags();
		return flags;
	}
	
	/**
	 * 获取指定时间段的所有录像时间段。
	 * <pre>
	 * 一般而言，一次查询的时间段不要超过24小时，如果时间间距太大，中间件只能返回前面部分录像时间。
	 * </pre>
	 * @param domain 指定查询录像的域
	 * @param puid 设备号
	 * @param chnid 视频源号（通道号）
	 * @param startime 开始时间，精确到时分秒
	 * @param endtime 结束时间，精确到时分秒
	 * @param type 录像类型
	 * @return
	 * @throws KMException
	 */
	@SuppressWarnings("deprecation")
	public List<Times>  queryRecTime(String domain, String puid, int chnid, Date startime, Date endtime, int type) throws KMException {
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if(ssid == CuSession.INVALID_SSID){
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		
		if(domain == null){
			domain = session.getCmuno();//默认为当前登录平台
		}

		//不需要全天时间
		/*startime = TimeUtil.getDayStart(startime);
		endtime = TimeUtil.getDayEnd(endtime);*/
		
		QueryRectimeRequest request = new QueryRectimeRequest();
		request.setDomain(domain);
		request.setPuid(puid);
		request.setChnid(chnid);
		request.setStarttime(startime);
		request.setEndtime(endtime);
		request.setType(type);
		
		QueryRectimeResponse response = (QueryRectimeResponse)this.sendRequest(session.getCu().getId(), request);
		List<Times> times =response.getTimes();
		return times;
		
	}
	
	/**
	 * PTZ控制
	 * @param puid 设备号
	 * @param chnid 视频源号（通道号）
	 * @param cmd  控制指令类型
	 * <pre>
	 * 0 向左,1 向右,2 向上, 3向下,4 左上,5 左下, 6右上,7 右下, 8停止移动,
	 * 9 视野拉近, 10视野拉远, 11视野缩放停止,12 归位, 
	 * 128 开始自动巡航, 129停止自动巡航, 
	 * 210 将焦距调远, 211 将焦距调近, 212 自动调焦, 213 调焦停止,
	 * 214 摄象头预存 预案保存, 215 调摄象头预存 调取预案, 
	 * 216 初始化摄像头, 217 画面调亮, 218 画面调暗, 219 停止调亮, 
	 * 220 新命令, 221 附加命令, 
	 * 222 中心定位, 223 局部放大, 224 艾立克水平移动速度设置命令,,225 艾立克垂直移动速度设置命令，226 sony亮度调节启动命令
	 * </pre>
	 * @return
	 * @throws KMException
	 */
	public boolean ptz(String puid, int chnid, int cmd) throws KMException {
		
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if(ssid == CuSession.INVALID_SSID){
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		int cuId = session.getCu().getId();
		
		PtzRequest request = new PtzRequest();
		request.setPuid(puid);
		request.setChnid(chnid);
		request.setPtzCmd(cmd);
		
		this.sendRequest(cuId, request);//PtzResponse
		return true;
	}
	
	/**
	 * 开启平台录像
	 * 
	 * @param domain
	 *            域id
	 * @param puid
	 *            设备号
	 * @param chnid
	 *            通道号
	 * @return errorcode 错误码
	 * @throws KMException
	 */
	public int startplatrec(String domain, String puid, int chnid)
			throws KMException {
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if (ssid == CuSession.INVALID_SSID) {
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		int cuId = session.getCu().getId();
		StartplatrecRequest request = new StartplatrecRequest();
		request.setDomain(domain);
		request.setPuid(puid);
		request.setChnid(chnid);
		StartplatrecResponse response = (StartplatrecResponse) this
				.sendRequest(cuId, request);
		return response.getErrorcode();
	}

	/**
	 * 停止平台录像
	 * 
	 * @param domain
	 *            域id
	 * @param puid
	 *            设备号
	 * @param chnid
	 *            通道号
	 * @return errorcode 错误码
	 * @throws KMException
	 */
	public int stopplatrec(String domain, String puid, int chnid)
			throws KMException {
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if (ssid == CuSession.INVALID_SSID) {
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		int cuId = session.getCu().getId();
		StopplatrecRequest request = new StopplatrecRequest();
		request.setDomain(domain);
		request.setPuid(puid);
		request.setChnid(chnid);
		StopplatrecResponse response = (StopplatrecResponse) this.sendRequest(
				cuId, request);
		return response.getErrorcode();
	}

	/**
	 * 开启前端录像
	 * 
	 * @param puid
	 *            设备号
	 * @param chnid
	 *            通道号
	 * @return errorcode 错误码
	 * @throws KMException
	 */
	public int startpurec(String puid, int chnid) throws KMException {
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if (ssid == CuSession.INVALID_SSID) {
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		int cuId = session.getCu().getId();
		StartpurecRequest request = new StartpurecRequest();
		request.setPuid(puid);
		request.setChnid(chnid);
		StartpurecResponse response = (StartpurecResponse) this.sendRequest(
				cuId, request);
		return response.getErrorcode();
	}

	/**
	 * 停止前端录像
	 *
	 *            域id
	 * @param puid
	 *            设备号
	 * @param chnid
	 *            通道号
	 * @return errorcode 错误码
	 * @throws KMException
	 */
	public int stoppurec(String puid, int chnid) throws KMException {
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if (ssid == CuSession.INVALID_SSID) {
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		int cuId = session.getCu().getId();
		StoppurecRequest request = new StoppurecRequest();
		request.setPuid(puid);
		request.setChnid(chnid);
		StoppurecResponse response = (StoppurecResponse) this.sendRequest(cuId,
				request);
		return response.getErrorcode();
	}
	
	/**
	 * 获取国标id
	 * 
	 * @param domain
	 *            域id
	 * @param puid
	 *            设备号
	 * @param chn
	 *            通道号
	 * @return errorcode 错误码
	 * @throws KMException
	 */
	public String getGbno(String domain, String puid, int chn)
			throws KMException {
		int ssid = client.getSessionManager().getSSIDByPuid(puid);
		if (ssid == CuSession.INVALID_SSID) {
			throw new KMException("设备所在监控平台未连接");
		}
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		int cuId = session.getCu().getId();
		GetGbnoRequest request = new GetGbnoRequest();
		request.setDomain(domain);
		request.setPuid(puid);
		request.setChn(chn);
		GetGbnoResponse response = (GetGbnoResponse) this.sendRequest(
				cuId, request);
		return response.getGbno();
	}

    /**
     * @Title: getTvWall
     * @Description: 获取电视墙
     * @return: boolean
     * @author: fanshaocong
     * @Date: 2017年12月26日 上午10:36:54
	 * @alter by ycw 2021年06月29日 上午10:36:54
     */
    public int getTvWall(int cuId) throws KMException {

        GetTvWallRequest request = new GetTvWallRequest();

		GetTvWallResponse response = (GetTvWallResponse) this.sendRequest(cuId, request);

		return response.getNum();
    }

    /**
     * @Title: putTvWall
     * @Description: iaAdd为ture时表示增加电视墙，为false表示修改电视墙
     * @return: boolean
     * @author: fanshaocong
     * @Date: 2017年12月26日 上午10:37:34
     */
    public String putTvWall(int cuId, TvWall tvwall, boolean iaAdd) throws KMException {

        PutTvWallRequest request = new PutTvWallRequest();
        request.setIsadd(iaAdd);
        request.setTvWall(tvwall);

        PutTvWallResponse response = (PutTvWallResponse)this.sendRequest(cuId, request);
        String tvwallid = response.getTvwallid();
        return tvwallid;
    }

	/**
	 * 添加/修改电视墙预案
	 * @param cuId
	 * @param tvWallScheme
	 * @param isAdd
	 * @return true
	 * @author: ycw
	 * @Date: 2021年2月20日 上午9:50:28
	 * @throws KMException
	 */
    public boolean putTvWallScheme(int cuId,TvWallScheme tvWallScheme,boolean isAdd)throws KMException{

		PutTvWallSchemeRequest request = new PutTvWallSchemeRequest();
		request.setIsadd(isAdd);
		request.setTvWallScheme(tvWallScheme);

		this.sendRequest(cuId, request);
		return true;
	}

	/**
	 * 删除电视墙预案
	 * @param cuId
	 * @param tvWallId
	 * @param name
	 * @return true
	 * @author ycw
	 * @Date 2021年2月20日 上午10:17:02
	 * @throws KMException
	 */
	public boolean delTvWallScheme(int cuId,String tvWallId,String name)throws KMException{

		DelTvWallSchemeRequest request = new DelTvWallSchemeRequest();
        request.setTvwallid(tvWallId);
        request.setName(name);

        this.sendRequest(cuId,request);
        return true;
	}



    /**
     * @Title: delTvWall
     * @Description: 删除电视墙
     * @return: boolean
     * @author: fanshaocong
     * @Date: 2017年12月26日 上午10:38:33
     */
    public boolean delTvWall(int cuId, String id, String name) throws KMException {

        DelTvWallRequest request = new DelTvWallRequest();
        request.setId(id);
        request.setName(name);

        this.sendRequest(cuId, request);

        return true;
    }

    /**
     * @Title: startLookTvWall
     * @Description: 选看电视墙
     * @return: boolean
     * @author: fanshaocong
     * @Date: 2017年12月26日 上午10:38:43
     */
    public boolean startLookTvWall(int cuId, String tvwallid, int tvid, int divid,
                                   String puid, int chnid) throws KMException {

        StartLookTvWallRequest request = new StartLookTvWallRequest();
        request.setTvwallid(tvwallid);
        request.setTvid(tvid);
        request.setDivid(divid);
        request.setPuid(puid);
        request.setChnid(chnid);

        this.sendRequest(cuId, request);

        return true;
    }

    /**
     * @Title: stopLookTvWall
     * @Description: 停止选看电视墙
     * @return: boolean
     * @author: fanshaocong
     * @Date: 2017年12月26日 上午10:38:54
     */
    public boolean stopLookTvWall(int cuId, String tvwallid, int tvid, int divid,
                                  String puid, int chnid) throws KMException {

        StopLookTvWallRequest request = new StopLookTvWallRequest();
        request.setTvwallid(tvwallid);
        request.setTvid(tvid);
        request.setDivid(divid);
        request.setPuid(puid);
        request.setChnid(chnid);

        this.sendRequest(cuId, request);

        return true;
    }

	public int getTvWallScheme(int cuId, String tvwallid) throws KMException {
		int num = 0;
		GetTvWallSchemeRequest request = new GetTvWallSchemeRequest();
		request.setTvwallid(tvwallid);
		GetTvWallSchemeResponse response = (GetTvWallSchemeResponse)sendRequest(cuId, (CuRequest)request);
		num = response.getNum();
		return num;
	}

	public boolean loadTvWallScheme(int cuId, String tvwallid, String name) throws KMException {
		LoadTvWallSchemeRequest request = new LoadTvWallSchemeRequest();
		request.setTvwallid(tvwallid);
		request.setName(name);
		LoadTvWallSchemeResponse response = (LoadTvWallSchemeResponse)sendRequest(cuId, (CuRequest)request);
		if (response.getErrorcode() == 0)
			return true;
		return false;
	}

    /**
	 * 	获取通道播放URL
	 * @param domain
	 * @param puid
	 * @param chnid
	 * @return
	 * @throws KMException
	 */
    public List<Urlinfo> getDeviceStreamurl(String domain, String puid, int chnid, int cuId)throws KMException {
    	GetDeviceStreamurlRequest request=new GetDeviceStreamurlRequest();
    	request.setDomain(domain);
    	request.setPuid(puid);
    	request.setChnid(chnid);
    	GetDeviceStreamurlResponse reponse=(GetDeviceStreamurlResponse)this.sendRequest(cuId, request);
    	List<Urlinfo> urlinfos = reponse.getUrlinfos();
    	return urlinfos;
    }
    /**
   	 * 	根据国标ID获取2.0 PUID
   	 * @param cuId
   	 * @return
   	 * @throws KMException
   	 */
    public GetDeviceIdResponse getdeviceid(String gbno, int cuId)throws KMException {
      GetDeviceIdRequest request=new GetDeviceIdRequest();
      request.setGbno(gbno);
      GetDeviceIdResponse reponse=(GetDeviceIdResponse)this.sendRequest(cuId, request);
      return reponse;
    }
    
    
    /**
	 * 获取当前监控平台磁阵信息。
	 * @param ssid
	 * @return
	 * @throws ConnectException
	 */
	public PDiskInfo getDiskInfo(int ssid) throws KMException {
		DiskInfoRequest request = new DiskInfoRequest();
		request.setSsid(ssid);
		DiskInfoResponse response = (DiskInfoResponse)client.sendRequest(request);
		PDiskInfo diskinfo = response.getDiskinfo();
		return diskinfo;
	}
}
