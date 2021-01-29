package com.kedacom.middleware.cu;

import com.kedacom.middleware.cu.domain.*;

import java.util.List;

/**
 * 监控平台Notify监听器
 * @author TaoPeng
 *
 */
public interface CuNotifyListener{
	
	/**
	 * 收到通知：监控平台连接状态
	 * @param cuId
	 * @param status
	 */
	public void onCuConnectStatus(int cuId, CuSessionStatus status);
	
	/**
	 * 收到通知：监控平台掉线
	 * @param cuId
	 */
	public void onCuOffine(int cuId);
	
	/**
	 * 收到通知：设备状态
	 * @param notify
	 */
	public void onDeviceStatus(String puid, boolean online);
	
	/**
	 * 收到通知：通道状态
	 * @param puid
	 * @param status
	 */
	public void onChannelStatus(String puid, List<PChannelStatus> status);
	
	/**
	 * 收到通知：设备退网
	 * @deprecated 此方法已被{@link #onDeviceOut(int, String)}代替。原因，业务需要感知从呢个平台上退网
	 * @param puid
	 */
	public void onDeviceOut(String puid);
	
	/**
	 * 收到通知：设备退网
	 * @param cuId
	 * @param puid
	 */
	public void onDeviceOut(int cuId, String puid);
	
	/**
	 * 收到通知：设备入网
	 * @param cuId
	 * @param device
	 */
	public void onDeviceIn(int cuId, PDevice device);
	
	/**
	 * 收到通知：GPS信息
	 * @param notify
	 */
	public void onGps(String puid, List<Gps> gpsList);
	
	/**
	 * 收到通知：设备加载完成
	 * @param cuId
	 */
	public void onDeviceLoadComplate(int cuId);

	void onGetTvWall(int paramInt, TvWall paramTvWall);

	void onGetTvWallScheme(int paramInt, TvWallScheme paramTvWallScheme);
	
}
