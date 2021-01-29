package com.kedacom.middleware.mcu.notify;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.ConfStatus;
import org.json.JSONObject;

/**
 * 会议基本状态通知
 * @author TaoPeng
 *
 */
public class ConfStatusNotify extends McuNotify {

	public static final String NAME = "confstatnty";
	/**
	 * 会议基本状态
	 */
	private ConfStatus confStatus;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
		String e164 = jsonData.optString("confe164");
		String speakermt = jsonData.optString("speakermt");
		int lockmode = jsonData.optInt("lockmode");
		int takemode = jsonData.optInt("takemode");
		boolean breggk = jsonData.optBoolean("breggk");
		
		ConfStatus confStatus = new ConfStatus();
		confStatus.setConfe164(e164);
		confStatus.setSpeakermt(speakermt);
		confStatus.setLockmode(lockmode);
		confStatus.setTakemode(takemode);
		confStatus.setBreggk(breggk);
		
		this.confStatus = confStatus;
	}

	public ConfStatus getConfStatus() {
		return confStatus;
	}

	public void setConfStatus(ConfStatus confStatus) {
		this.confStatus = confStatus;
	}


}
