package com.kedacom.middleware;

import com.kedacom.middleware.mcu.McuNotifyListener;
import com.kedacom.middleware.mcu.domain.ConfStatus;
import com.kedacom.middleware.mcu.domain.MTStatus;
import com.kedacom.middleware.mcu.domain.VcrStatus;


import org.apache.log4j.Logger;

import java.util.List;

public class MyMcuNotifyListener implements McuNotifyListener{
	private static final Logger log = Logger.getLogger(MyMcuNotifyListener.class);
	@Override
	public void onMcuOffine(String mcuId, String mcuIp) {
		// TODO Auto-generated method stub
		
		log.info(mcuIp);
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
