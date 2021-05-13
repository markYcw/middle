package com.kedacom.middleware.vrs;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.vrs.domain.VRS;
import com.kedacom.middleware.vrs.domain.VRSRecInfo;
import com.kedacom.middleware.vrs.response.VrsQueryRecResponse;
import keda.common.util.ATaskThread;

import javax.xml.transform.Source;
import java.util.List;

/**
 * 录播服务器 访问控制示例程序。
 * @author LinChaoYu
 *
 */
public class VRSDemo {
	public static void main(String[] args) {
		try {
			demo1();
		} catch (KMException e) {
			e.printStackTrace();
		}
	}
	


	/**
	 * 访问方式一（建议的方式）:先注册会议平台信息，再登录使用。
	 */
	static void demo1() throws KMException{
		//开始连接中间件（进程的整个生命周期，此逻辑只需要被调用一次）
		//KM km = KM.getInstance();
		KM km = new KM();
		km.setServerIP("172.16.129.214");
		km.setServerPort(45670);
		km.start();

		//状态监听（相当于 PlatformEventHelper ）
		km.getVRSClient().addListener(new VRSNotifyListenerAdpater() {

			public void onVRSOffine(String vrsId, String vrsIp) {
				System.out.println("====> onVRSOffine vrsId="+vrsId+" ,vrsIp="+vrsIp);
			}
		});
		final VRSClient vrsClient = km.getVRSClient();
		
		//第一步：注册录播服务器信息
		//第一步：示例1：增加录播服务器
		String id = "1";
		String ip = "172.16.131.62";
		String version = VRS.VRS_2000B_SV5;
		Integer port = 8765;
		String user = "admin";
		String pass = "kedacom@123";
		
		final VRS vrs = new VRS();
		vrs.setId(id);
		vrs.setIp(ip);
		vrs.setVersion(version);
		vrs.setPort(port);
		vrs.setPassword(pass);
		vrs.setUsername(user);
		
		vrsClient.addVRS(vrs);//增加VRS后，自动连接
		ATaskThread thread = new ATaskThread() {

			@Override
			public void doWork() throws Exception {
				//第二步：操作
				Integer i = vrsClient.login("172.16.131.62","admin","kedacom@123","18",8765);
				System.out.println("===============VRS2000B登录成功： {}"+i);
			}
		};
		thread.setName("VRS-work");
		thread.setTimeout(20000);
		thread.start();
	}
}
