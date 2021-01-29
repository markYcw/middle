package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

public class BurnStatusResponse extends MTResponse {

	private int totalsize;

	private int freesize;

	private int burnstatus;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		totalsize = jsonData.optInt("totalsize");
		freesize = jsonData.optInt("freesize");
		burnstatus = jsonData.optInt("burnstatus");
	}

	public int getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
	}

	public int getFreesize() {
		return freesize;
	}

	public void setFreesize(int freesize) {
		this.freesize = freesize;
	}

	public int getBurnstatus() {
		return burnstatus;
	}

	public void setBurnstatus(int burnstatus) {
		this.burnstatus = burnstatus;
	}

}
