package com.kedacom.middleware.cu;

import com.kedacom.middleware.cu.domain.*;

import java.util.List;

//适配器
public class CuNotifyListenerAdpater implements CuNotifyListener {

	@Override
	public void onCuConnectStatus(int cuId, CuSessionStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCuOffine(int cuId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceStatus(String puid, boolean online) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChannelStatus(String puid, List<PChannelStatus> status) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 *  @deprecated 此方法已被{@link #onDeviceOut(int, String)}代替。原因，业务需要感知从呢个平台上退网
	 */
	@Override
	public void onDeviceOut(String puid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceOut(int cuId, String puid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGps(String puid, List<Gps> gpsList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceLoadComplate(int cuId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetTvWall(int paramInt, TvWall paramTvWall) {

	}

	@Override
	public void onGetTvWallScheme(int paramInt, TvWallScheme paramTvWallScheme) {

	}

	@Override
	public void onDeviceIn(int cuId, PDevice device) {
		// TODO Auto-generated method stub
		
	}

	

}
