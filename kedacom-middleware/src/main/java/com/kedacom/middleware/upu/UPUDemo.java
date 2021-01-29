package com.kedacom.middleware.upu;


/**
 * UPU访问控制示例程序。
 * @author LinChaoYu
 *
 */
public class UPUDemo {
//	public static void main(String[] args) {
//		try {
//			demo();
//		} catch (KMException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 示例程序代码
//	 * @throws KMException
//	 */
//	public static void demo() throws KMException{
//		//开始连接中间件（进程的整个生命周期，此逻辑只需要被调用一次）
//		//KM km = KM.getInstance();
//		KM km = new KM();
//		km.setServerIP("172.16.128.250");
//		km.setServerPort(45670);
//		km.start();
//		
//		//状态监听（相当于 PlatformEventHelper ）
//		km.getUPUClient().addListener(new UPUNotifyListenerAdpater() {
//
//			public void onUPUOffine(String upuId, String upuIp) {
//				// TODO Auto-generated method stub
//				System.out.println("====> onUPUOffine upuId="+upuId+" ,upuIp="+upuIp);
//			}
//		});
//		
//		//应用示例
//		demo1();
//		
//		//停止
////		km.stop();
//	}
//
//	/**
//	 * 访问方式一（建议的方式）:先注册会议平台信息，再登录使用。
//	 */
//	static void demo1() throws KMException{
//		//KM km = KM.getInstance();
//		KM km = new KM();
//		final UPUClient upuClient = km.getUPUClient();
//		
//		//第一步：注册会话平台信息
//		//第一步：示例1：增加会议平台
//		String id = "1";
//		String ip = "172.16.231.38";
//		int port = 2200;
//		
//		final UPU upu = new UPU();
//		upu.setId(id);
//		upu.setIp(ip);
//		upu.setPort(port);
//		
//		upuClient.addUPU(upu);//增加GK后，自动连接
//
//		ATaskThread thread = new ATaskThread() {
//			
//			@Override
//			public void doWork() throws Exception {
//				//第二步：操作
//				List<String> e164s = new ArrayList<String>();
//				e164s.add("51000000001");
//				
//				List<UPUMt> mts = upuClient.findmtbye164(upu.getId(), e164s);
//				System.out.println("获取终端列表：" + (mts != null ? mts.size() : 0));
//				if(mts != null){
//					for(UPUMt mt : mts){
//						System.out.println("e164="+mt.getE164()+" state="+mt.getStatus());
//					}
//				}
//			}
//		};
//		thread.setName("UPU-work");
//		thread.setTimeout(20000);
//		thread.start();
//	}
}
