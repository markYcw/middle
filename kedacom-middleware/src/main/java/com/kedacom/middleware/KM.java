package com.kedacom.middleware;


import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.common.CommonClient;
import com.kedacom.middleware.cu.CuClient;
import com.kedacom.middleware.epro.EProClient;
import com.kedacom.middleware.gk.GKClient;
import com.kedacom.middleware.mcu.McuClient;
import com.kedacom.middleware.mt.MTClient;
import com.kedacom.middleware.rk100.RkClient;
import com.kedacom.middleware.svr.SVRClient;
import com.kedacom.middleware.upu.UPUClient;
import com.kedacom.middleware.vrs.VRSClient;

/**
 * 中间件(kedaom midllware)
 * @author TaoPeng
 *
 */
public class KM {

	/*
	 * 如果后期需要支持连接多个中间件，可以取消单例模式，通过new KM()的方式来实现。
	 */
	private static KM instance;
	public static KM getInstance(){
		if(instance == null){
			instance = new KM();
		}
		return instance;
	}
	
	/**
	 * 中间件服务IP
	 */
	private String serverIP = "127.0.0.1";
	
	/**
	 * 中间件服务端口
	 */
	private int serverPort = 45670;
	
	
	/**
	 * tcp数据通信客户端
	 */
	private IClient iclient;
	
	/**
	 * 中间件“公共部分”接口访问
	 */
	private CommonClient commonClient;
	
	/**
	 * “会议平台”接口访问
	 */
	private McuClient mcuClient;
	
	/**
	 * “会议终端”接口访问
	 */
	private MTClient mtClient;
	
	/**
	 * “监控平台”接口访问
	 */
	private CuClient cuClient;
	
	/**
	 * “GK服务”接口访问
	 */
	private GKClient gkClient;
	
	/**
	 * “VRS服务”接口访问
	 */
	private VRSClient vrsClient;
	
	/**
	 * SVR接口访问
	 */
	private SVRClient svrClient;
	
	/**
	 * upu接口访问
	 */
	private UPUClient upuClient;

	/**
	 * RK100接口访问
	 */
	private RkClient rkClient;

	/**
	 * E10Pro接口访问
	 */
	private EProClient eProClient;

	
	public IClient getClient(){
		return iclient;
	}
	
	public CommonClient getCommonClient(){
		if(commonClient == null){
			commonClient = new CommonClient(this);
		}
		return commonClient;
	}
	
	public McuClient getMcuClient(){
		if(mcuClient == null){
			mcuClient = new McuClient(this);
		}
		return mcuClient;
	}
	
	public MTClient getMTClient(){
		if(mtClient == null){
			mtClient = new MTClient(this);
		}
		return mtClient;
	}

	public CuClient getCuClient(){
		if(cuClient == null){
			cuClient = new CuClient(this);
		}
		return cuClient;
	}

	public GKClient getGKClient(){
		if(gkClient == null){
			gkClient = new GKClient(this);
		}
		return gkClient;
	}
	
	public VRSClient getVRSClient(){
		if(vrsClient == null){
			vrsClient = new VRSClient(this);
		}
		return vrsClient;
	}
	
	public SVRClient getSVRClient(){
		if(svrClient == null){
			svrClient = new SVRClient(this);
		}
		return svrClient;
	}
	
	public UPUClient getUPUClient(){
		if(upuClient == null){
			upuClient = new UPUClient(this);
		}
		return upuClient;
	}

	public RkClient getRkClient(){
		if(rkClient == null){
			rkClient = new RkClient(this);
		}
		return rkClient;
	}

	public EProClient getEProClient(){
		if(eProClient == null){
			eProClient = new EProClient(this);
		}
		return eProClient;
	}
	
	/**
	 * 开始
	 * @see #stop()
	 */
	public void start(){

		this.init();
		
		TCPClient client = new TCPClient();
		client.setServerIP(serverIP);
		client.setServerPort(serverPort);
		client.startConnect();
		
		this.iclient = client;
		
	}
	
	/**
	 * 初始化参数
	 */
	private void init(){
		//TODO
	}
	
	/**
	 * 停止。
	 * @see #start()
	 */
	public void stop(){
		if(iclient != null){
			if(iclient instanceof TCPClient){
				TCPClient c = (TCPClient)iclient;
				c.close();
			}
		}
		
		if(this.cuClient != null){
			this.cuClient.stop();
		}
		
		if(this.mcuClient != null){
			this.mcuClient.stop();
		}
		
		if(this.mtClient != null){
			this.mtClient.stop();
		}
		
		if(this.gkClient != null){
			this.gkClient.stop();
		}
		
		if(this.vrsClient != null){
			this.vrsClient.stop();
		}
		
		if(this.svrClient != null){
			this.svrClient.stop();
		}
		
		if(this.upuClient != null){
			this.upuClient.stop();
		}

		if(this.rkClient != null){
			this.rkClient.stop();
		}

		if(this.eProClient != null){
			this.eProClient.stop();
		}
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	
}
