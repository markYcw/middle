package com.kedacom.middleware.mt;


//适配器
public class MTNotifyListenerAdpater implements MTNotifyListener {

	@Override
	public void onMtOffine(String mtId, String mtIp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMTRobbed(String mtId, String mtIp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBurnStauts(String mtId, String mtIp, int ssid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopP2P(String mtId, String mtIp, int ssid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transmitInfo(String mtId, String mtIp, int ssid, String msg, MTClient client) {
		// TODO Auto-generated method stub
		
	}

}
