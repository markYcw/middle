package com.kedacom.middleware.mcu;

import com.kedacom.middleware.mcu.domain.ConfStatus;
import com.kedacom.middleware.mcu.domain.MTStatus;
import com.kedacom.middleware.mcu.domain.VcrStatus;

import java.util.List;

//适配器
public class McuNotifyListenerAdpater implements McuNotifyListener {

	@Override
	public void onMcuOffine(String mcuId, String mcuIp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMTStatus(String mcuId, MTStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfStatus(String mcuId, ConfStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVcrStatus(String mcuId, VcrStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfList(String mcuId, List<String> e164s) {
		// TODO Auto-generated method stub
		
	}

	

}
