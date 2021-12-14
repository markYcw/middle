package keda.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 描述一个周期性的任务，此任务在单独的线程中运行。子类实现{@link #doWork()}方法，以实现目标任务的周期执行。
 * @author TaoPeng
 *
 */
public abstract class ATaskThread implements Runnable{

	private static final Logger log = LogManager.getLogger(ATaskThread.class);
	
	private static int threadIndex = 0;
	
	private boolean work = true;
	private final Object lock = new Object();
	
	/**
	 * 线程名字
	 */
	private String name;
	/**
	 * 延迟启动任务的时间, 单位：毫秒，默认：0
	 */
	private long delay = 0;
	
	private Thread thread = null;
	/**
	 * 任务执行间隔时间，单位：毫秒，默认值：30000
	 */
	private long waitTime = 30000;
	
	/**
	 * 标识是否暂停任务
	 */
	private boolean hold = false;
	
	/**
	 * 设置是否是守护线程
	 */
	private boolean daemon = false;
	
	public ATaskThread(){
		
	}
	
	
	@Override
	public void run() {
		
		if(delay > 0){
			//延迟启动
			log.debug("start for delay");
			synchronized (lock) {
				try {
					lock.wait(delay);
				} catch (InterruptedException e) {
					log.error("lock.wait－delay", e);
				}
			}
		}
		
		log.debug("start");
		while(work){
			
			if(!hold){
				try{
					
					doWork();
				}catch(Exception e){
					log.error("doWork()", e);
					//长轮询时waitTime为0
					if(waitTime == 0){
						synchronized (lock) {
							try {
								lock.wait(10000);
							} catch (InterruptedException ex) {
								log.error("lock.wait", ex);
								work = false;
							}
						}
					}
				}
			}

			if(!work){
				//有可能线程自己停止自己，例如在方法doWork()中调用super.stop()
				break;
			}
			
			//线程等候（长轮询时waitTime为0）
			if(waitTime > 0){
				synchronized (lock) {
					try {
						lock.wait(waitTime);
					} catch (InterruptedException e) {
						log.error("lock.wait", e);
						work = false;
					}
				}
			}
		}

		thread = null;
		log.info("thread [" + name + "] exit");
		
	}
	
	/**
	 * 周期执行的具体任务
	 */
	public abstract void doWork() throws Exception;
	
	/**
	 * 启动任务,如果任务已启动，则在停止前不会重复启动
	 */
	public void start(){
		start(0);
	}
	
	/**
	 * 启动任务,如果任务已启动，则在停止前不会重复启动
	 * @param delay 延时启动的时间：单位：毫秒
	 */
	public void start(long delay){
		this.work = true;
		this.hold = false;
		this.delay = delay;
		if(thread == null){
			thread = new Thread(this);
			name = name == null ? "AbstractTask" : name;
			thread.setName(name + "-" + threadIndex ++);
			thread.setDaemon(daemon);
			thread.start();
		}
	}
	
	/**
	 * 停止任务
	 */
	public void stop(){
		work = false;
		notifyTask();
	}
	
	/**
	 * 唤醒线程
	 */
	public void notifyTask(){
		synchronized (lock) {
			lock.notifyAll();
		}
	}
	
	/**
	 * 设备当前任务是否暂停运行
	 * @param hold true 暂停， false 继续
	 */
	public void setHold(boolean hold){
		this.hold = hold;
		notifyTask();
	}
	
	/**
	 * 任务执行周期
	 * @param timeout 单位：毫秒
	 */
	public void setTimeout(long timeout){
		setTimeout(timeout, true);
	}
	/**
	 * 
	 * @param timeout waitTime
	 * @param wakeup 如果设置为true,将自动唤醒线程，否则，新的周期值与当前值不相等时，才唤醒线程
	 * @see #setTimeout(int)
	 */
	public void setTimeout(long timeout, boolean wakeup){
		if(!wakeup){
			wakeup = this.waitTime != timeout;
		}
		
		this.waitTime = timeout;
		if(wakeup){
			synchronized (lock) {
				lock.notifyAll();
			}
		}
	}

	/**
	 * 设备任务的名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 当前线程是否在工作
	 * @return
	 */
	public boolean isWork() {
		return work;
	}
	/**
	 * 设置是否是守护线程
	 * @param daemon
	 */
	public void setDaemon(boolean daemon){
		this.daemon = daemon;
	}
}
