package com.kedacom.middleware.mt.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

public class BurnStatusNotify extends MTNotify {

	public static final String NAME = "burnstatusnotify";

	private int runstatus = 0;
	
	private int burnrate = 0;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseNty(jsonData);
		runstatus = jsonData.optInt("runstatus");
		burnrate = jsonData.optInt("burnrate");
	}

	public int getRunstatus() {
		return runstatus;
	}

	public void setRunstatus(int runstatus) {
		this.runstatus = runstatus;
	}

	public int getBurnrate() {
		return burnrate;
	}

	public void setBurnrate(int burnrate) {
		this.burnrate = burnrate;
	}

}
