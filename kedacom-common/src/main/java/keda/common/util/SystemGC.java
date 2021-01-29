package keda.common.util;

import org.apache.log4j.Logger;

/**
 * 	定时执行垃圾回收线程
 * @author wjs
 *
 */
public class SystemGC extends Thread {
	private static Logger log = Logger.getLogger(SystemGC.class);
	//休眠5分钟
	private int sleepTime = 300000;
	//是否停止
	private boolean isStop = false;
	/**
	 * 
	 * @param sleepTime	休眠时间
	 */
	public SystemGC(Integer sleepTime){
		super("system gc thread.");
		//设备为守护线程
		setDaemon(true);
		if(sleepTime != null)
			this.sleepTime = sleepTime;
	}
	/**
	 * 默认构造函数，休眠时间为5分钟
	 */
	public SystemGC(){
		this(null);
	}
	/**
	 * 线程接口
	 */
	@Override
	public void run(){
		while(!isStop){
			System.gc();
			log.info("SystemGC do gc complete.");
			try{
				sleep(sleepTime);
			}catch(Exception ex){}
		}
		log.info("SystemGC had stoped.");
	}
	/**
	 * 停止垃圾回收线程
	 */
	public void stopGC(){
		log.info("SystemGC begin to stop.");
		isStop = true;
	}
	/**
	 * 开始垃圾回收
	 */
	public void startGC(){
		log.info("SystemGC begin to start.");
		isStop = false;
		start();
		log.info("SystemGC had started.");
	}
	
}
