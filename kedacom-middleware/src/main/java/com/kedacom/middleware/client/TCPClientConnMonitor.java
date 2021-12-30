package com.kedacom.middleware.client;



import org.apache.log4j.Logger;

/**
 * TCP连接监控。
 * @author TaoPeng
 *
 */
public class TCPClientConnMonitor {

	private static final Logger log = Logger.getLogger(TCPClientConnMonitor.class);
	private TCPClient tcpClient;
	
	public TCPClientConnMonitor(TCPClient tcpClient){
		this.tcpClient = tcpClient;
	}
	
	/**
	 * 任务状态：true运行，false 停止。
	 */
	private boolean run = true;
	private Thread thread;
	private Object lock = new Object();
	
	/**
	 * 重连周期。单位：毫秒，默认15000。
	 */
	private long cycle = 15000;

	//主要任务
	private void doWork(){
		log.debug("thread start");
		while(run){
			try{
				tcpClient.connect();
				
			}catch(Exception e){
				log.error("连接失败", e);
			}
			
			try{
				synchronized (lock) {
					lock.wait(cycle);
				}
			}catch(Exception e){
				break;
			}
		}
		run = false;
		thread = null;
		log.debug("thread exit");
	}
	/**
	 * 开始
	 */
	public void start(){
		this.run = true;
		if(thread == null){
			thread = new Thread(){
				@Override
				public void run(){
					doWork();
				}
			};
			thread.setName("KM-ConnMonitor");
			thread.start();
		}
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		this.run = false;
		synchronized (lock) {
			lock.notifyAll();
		}
		thread = null;
	}
	public boolean isRun() {
		return run;
	}
}
