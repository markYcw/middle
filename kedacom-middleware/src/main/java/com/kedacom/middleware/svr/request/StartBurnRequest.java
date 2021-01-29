package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.StartBurnResponse;
import org.json.JSONException;
import org.json.JSONObject;

;

/**
 * SVR刻录
 * @see StartBurnResponse
 * @author DengJie
 *
 */
public class StartBurnRequest extends SVRRequest {
	/**
	 * DVD刻录模式
	 */
	private int mode;
	/**
	 * 设置刻录任务名称
	 */
	private String burnname;
	
	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("startburn");
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("mode", mode);
		data.put("burnname", burnname);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getBurnname() {
		return burnname;
	}

	public void setBurnname(String burnname) {
		this.burnname = burnname;
	}

	@Override
	public IResponse getResponse() {
		
		return new StartBurnResponse();
	}

}
