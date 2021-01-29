package com.kedacom.middleware.cu.domain;

/**
 * 设备状态订阅。通过此类设置需要订阅哪些状态。
 * @author TaoPeng
 *
 */
public class DeviceStatusSubscribe {

	private Boolean subscribeOnline = true; 	//是否订阅在线状态(2.0)； true订阅, false不订阅, null保持不变
	private Boolean subscribeAlarm = false; 	//是否订阅报警状态（2.0)； true订阅, false不订阅, null保持不变
	private Boolean subscribeChannel = true; 	//是否订阅通道状态（2.0)； true订阅, false不订阅, null保持不变
	private Boolean subscribeGps = false; 		//是否订阅GPS状态　（2.0)； true订阅, false不订阅, null保持不变
	private Boolean subscribeTvwall = false;	//是否订阅电视墙状态（2.0)； true订阅, false不订阅, null保持不变
	private Boolean subscribeRecord = true;		//是否订阅录像状态（2.0)； true订阅, false不订阅, null保持不变
	private Boolean subscribeTransdata = false;	//是否订阅透明通道数据状态（2.0)； true订阅, false不订阅, null保持不变
	
	/**
	 * 一次性设置所有消息的订阅状态
	 * @param isSubscribe
	 */
	public void setAll(Boolean isSubscribe){
		this.subscribeOnline = isSubscribe;
		this.subscribeAlarm = isSubscribe;
		this.subscribeChannel = isSubscribe;
		this.subscribeGps = isSubscribe;
		this.subscribeTvwall = isSubscribe;
		this.subscribeRecord = isSubscribe;
		this.subscribeTransdata = isSubscribe;
	}
	
	public Boolean getSubscribeOnline() {
		return subscribeOnline;
	}
	public void setSubscribeOnline(Boolean subscribeOnline) {
		this.subscribeOnline = subscribeOnline;
	}
	public Boolean getSubscribeAlarm() {
		return subscribeAlarm;
	}
	public void setSubscribeAlarm(Boolean subscribeAlarm) {
		this.subscribeAlarm = subscribeAlarm;
	}
	public Boolean getSubscribeChannel() {
		return subscribeChannel;
	}
	public void setSubscribeChannel(Boolean subscribeChannel) {
		this.subscribeChannel = subscribeChannel;
	}
	public Boolean getSubscribeGps() {
		return subscribeGps;
	}
	public void setSubscribeGps(Boolean subscribeGps) {
		this.subscribeGps = subscribeGps;
	}
	public Boolean getSubscribeTvwall() {
		return subscribeTvwall;
	}
	public void setSubscribeTvwall(Boolean subscribeTvwall) {
		this.subscribeTvwall = subscribeTvwall;
	}
	public Boolean getSubscribeRecord() {
		return subscribeRecord;
	}
	public void setSubscribeRecord(Boolean subscribeRecord) {
		this.subscribeRecord = subscribeRecord;
	}
	public Boolean getSubscribeTransdata() {
		return subscribeTransdata;
	}
	public void setSubscribeTransdata(Boolean subscribeTransdata) {
		this.subscribeTransdata = subscribeTransdata;
	}
	
	
	
}
