package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

public class BurnStatusNotify extends MTResponse {

	private int runstatus;

	private int burnrate;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseResp(jsonData);

		System.out.println("=====>BurnStatusNotify");
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
