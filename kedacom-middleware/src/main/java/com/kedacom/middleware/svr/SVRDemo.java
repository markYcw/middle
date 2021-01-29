package com.kedacom.middleware.svr;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.svr.domain.SVR;
import keda.common.util.ATaskThread;


/**
 * 会议平台访问控制示例程序。
 * @author TaoPeng
 *
 */
public class SVRDemo {
	public static void main(String[] args) {
		try {
			demo();
		} catch (KMException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 示例程序代码
	 * @throws KMException
	 */
	public static void demo() throws KMException {

		//开始连接中间件（进程的整个生命周期，此逻辑只需要被调用一次）
		//KM km = KM.getInstance();
		KM km = new KM();
		km.setServerIP("172.16.128.80");
		km.setServerPort(45670);
		km.start();
		
		//状态监听（相当于 PlatformEventHelper ）
		km.getSVRClient().addListener(new SVRNotifyListenerAdpater() {

			@Override
			public void onSVROffine(String svrIp) {
				// TODO Auto-generated method stub
				super.onSVROffine(svrIp);
			}
		});
		
		//应用示例
		demo1();
		
		//停止
//		km.stop();
		
	
	}

	/**
	 * 访问方式一（建议的方式）:先注册会议平台信息，再登录使用。
	 */
	static void demo1() throws KMException {
		//KM km = KM.getInstance();
		KM km = new KM();
		final SVRClient svrClient = km.getSVRClient();
		
		//第一步：注册会话平台信息
		//第一步：示例1：增加会议平台
		String ip = "172.16.237.171";
		int port = 60000;
		final SVR svr = new SVR();
		svr.setIp(ip);
		svr.setPort(port);
		svr.setUsername("admin");
		svr.setPassword("admin123");
		svrClient.addSVR(svr);//增加会议平台后，自动连接

		String ip2 = "172.16.132.103";
		int port2 = 60000;
		final SVR svr2 = new SVR();
		svr.setIp(ip2);
		svr.setPort(port2);
		svr.setUsername("admin");
		svr.setPassword("admin");
		svrClient.addSVR(svr2);//增加会议平台后，自动连接

		//第一步：示例2：更新会议平台
//		mt.setUsername("admin2");
//		mt.setPassword("admin2");
//		mtClient.updateMT(mt);//更新会议平台信息后，按需要重新连接
		
		//第一步：示例3：删除会议平台
//		mtClient.removeMTByID(id); //删除会议平台信息后，自动断开连接，清除会话


		ATaskThread thread = new ATaskThread() {
			
			@Override
			public void doWork() throws Exception {
				//第二步：操作
				svrClient.login("172.16.237.171");
			}
		};
		thread.setName("SVR-work");
		thread.setTimeout(20000);
		thread.start();
		
	}
	
}
