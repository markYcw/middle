package com.kedacom.middleware.mcu;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.mcu.domain.*;
import keda.common.util.ATaskThread;

import java.util.List;

/**
 * 会议平台访问控制示例程序。
 * @author TaoPeng
 *
 */
public class McuDemo {
	
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
	public static void demo() throws KMException{

		//开始连接中间件（进程的整个生命周期，此逻辑只需要被调用一次）
		//KM km = KM.getInstance();
		KM km = new KM();
		km.setServerIP("172.16.128.80");
		km.setServerPort(45670);
		km.start();
		
		//状态监听（相当于 PlatformEventHelper ）
		km.getMcuClient().addListener(new McuNotifyListenerAdpater() {

			@Override
			public void onMcuOffine(String mcuId, String mcuIp) {
				// TODO Auto-generated method stub
				super.onMcuOffine(mcuId, mcuIp);
			}

			@Override
			public void onMTStatus(String mcuId, MTStatus status) {
				// TODO Auto-generated method stub
				super.onMTStatus(mcuId, status);
			}

			@Override
			public void onConfStatus(String mcuId, ConfStatus status) {
				// TODO Auto-generated method stub
				super.onConfStatus(mcuId, status);
			}

			@Override
			public void onVcrStatus(String mcuId, VcrStatus status) {
				// TODO Auto-generated method stub
				super.onVcrStatus(mcuId, status);
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
	static void demo1() throws KMException{
		//KM km = KM.getInstance();
		KM km = new KM();
		final McuClient mcuClient = km.getMcuClient();
		
		//第一步：注册会话平台信息
		//第一步：示例1：增加会议平台
		final String id = "1";
		String ip = "172.16.132.100";
		Mcu mcu = new Mcu();
		mcu.setId(id);
		mcu.setIp(ip);
		mcu.setUser("admin");
		mcu.setPassword("admin");
		mcuClient.addMcu(mcu);//增加会议平台后，自动连接

		//第一步：示例2：更新会议平台
		mcu.setUser("admin");
		mcu.setPassword("admin");
		mcuClient.updateMcu(mcu);//更新会议平台信息后，按需要重新连接
		
		//第一步：示例3：删除会议平台
//		mcuClient.removeMcuByID(id); //删除会议平台信息后，自动断开连接，清除会话

		ATaskThread thread = new ATaskThread() {
			
			@Override
			public void doWork() throws Exception {
				//第二步：操作
				List<Confe164> list = mcuClient.getConfTem(id, 2);
				for(Confe164 l : list){
					System.out.println("e164=" + l.getE164() + ", \t name=" + l.getName());
				}
			}
		};
		thread.setName("MCU-work");
		thread.setTimeout(20000);
		thread.start();
		
	}
	
}
