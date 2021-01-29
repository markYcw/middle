package com.kedacom.middleware.gk;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.gk.domain.GK;
import com.kedacom.middleware.gk.domain.Point;
import keda.common.util.ATaskThread;

import java.util.List;

/**
 * GK访问控制示例程序。
 * @author LinChaoYu
 *
 */
public class GKDemo {
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
		km.setServerIP("172.16.128.149");
		km.setServerPort(45670);
		km.start();
		
		//状态监听（相当于 PlatformEventHelper ）
		km.getGKClient().addListener(new GKNotifyListenerAdpater() {

			public void onGKOffine(String gkId, String gkIp) {
				// TODO Auto-generated method stub
				System.out.println("====> onGKOffine gkId="+gkId+" ,gkIp="+gkIp);
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
		final GKClient gkClient = km.getGKClient();
		
		//第一步：注册会话平台信息
		//第一步：示例1：增加会议平台
		String id = "1";
		String ip = "172.16.187.13";
		int port = 60001;
		
		final GK gk = new GK();
		gk.setId(id);
		gk.setIp(ip);
		gk.setPort(port);
		gk.setUsername("admin");
		gk.setPassword("admin");
		gk.setIsadmin(1);
		
		gkClient.addGK(gk);//增加GK后，自动连接

		ATaskThread thread = new ATaskThread() {
			
			@Override
			public void doWork() throws Exception {
				//第二步：操作
				List<Point> points = gkClient.getRegentityList(gk.getId());
				System.out.println("已注册GK实体：" + (points != null ? points.size() : 0));
				if(points != null){
					for(Point p : points){
						System.out.println("type="+p.getType()+" ip="+p.getIp()+ "e164s=");
						if(p.getE164s() != null && p.getE164s().size() > 0){
							for(String e164 : p.getE164s()){
								System.out.print(" "+e164);
							}
						}
					}
				}
			}
		};
		thread.setName("GK-work");
		thread.setTimeout(20000);
		thread.start();
	}
}
