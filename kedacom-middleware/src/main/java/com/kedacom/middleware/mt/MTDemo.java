package com.kedacom.middleware.mt;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.mt.domain.MT;
import com.kedacom.middleware.mt.domain.MTType;
import keda.common.util.ATaskThread;

/**
 * 会议平台访问控制示例程序。
 * @author TaoPeng
 *
 */
public class MTDemo {
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
		km.getMTClient().addListener(new MTNotifyListenerAdpater() {

			@Override
			public void onMtOffine(String mtId, String mtIp) {
				// TODO Auto-generated method stub
				super.onMtOffine(mtId, mtIp);
			}

			@Override
			public void onMTRobbed(String mtId, String mtIp) {
				// TODO Auto-generated method stub
				super.onMTRobbed(mtId, mtIp);
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
		final MTClient mtClient = km.getMTClient();
		
		//第一步：注册会话平台信息
		//第一步：示例1：增加会议平台
		String id = "1";
		String ip = "172.16.132.102";
		int port = 60000;
		final MT mt1 = new MT();
		mt1.setId(id);
		mt1.setIp(ip);
		mt1.setPort(port);
		mt1.setUsername("admin");
		mt1.setPassword("admin");
		mtClient.addMT(mt1);//增加会议平台后，自动连接

		String id2 = "2";
		String ip2 = "172.16.132.103";
		int port2 = 60000;
		final MT mt2 = new MT();
		mt2.setId(id2);
		mt2.setIp(ip2);
		mt2.setPort(port2);
		mt2.setUsername("admin");
		mt2.setPassword("admin");
		mtClient.addMT(mt2);//增加会议平台后，自动连接

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
				MTType mtType = mtClient.getMTType(mt1.getId()); //操作时，如果会议平台未登录，则会自动登录，如果登录失败，抛出异常。
				System.out.println("终端类型：" + mtType);
				
				//开启点对点会议
				mtClient.startP2P(mt1.getId(), 1, mt2.getIp(), "TP测试会议");

				//终端是否在会
				boolean ret = mtClient.checkIsInConf(mt1.getId(), 3);
				boolean ret2 = mtClient.checkIsInConf(mt2.getId(), 3);
				System.out.println("终端" + mt1.getIp() + "是否在会:" + ret);
				System.out.println("终端" + mt2.getIp() + "是否在会:" + ret2);
				
				//停止点对点会议
				mtClient.stopP2P(mt1.getId());
				mtClient.stopP2P(mt2.getId());
				
				//终端是否在会
				ret = mtClient.checkIsInConf(mt1.getId(), 3);
				ret = mtClient.checkIsInConf(mt2.getId(), 3);
				System.out.println("终端" + mt1.getIp() + "是否在会:" + ret);
				System.out.println("终端" + mt2.getIp() + "是否在会:" + ret2);
				
			}
		};
		thread.setName("MT-work");
		thread.setTimeout(20000);
		thread.start();
		
	}
	
}
