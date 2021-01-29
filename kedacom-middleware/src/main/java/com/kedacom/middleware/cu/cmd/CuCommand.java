package com.kedacom.middleware.cu.cmd;

import com.kedacom.middleware.client.ICommand;
import com.kedacom.middleware.cu.response.CuResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 监控平台(Cu)指令(command)。
 * @see CuResponse
 * @author TaoPeng
 *
 */
public abstract class CuCommand implements ICommand {

	/**
	 * 消息流水号
	 */
	private int ssno;
	
	/**
	 * 会话标识
	 */
	private int ssid;

	/**
	 * 生成消息的cmd部分
	 * @param name
	 * @return
	 * @throws JSONException
	 */
	protected JSONObject buildCmd(String name) throws JSONException {
		JSONObject req = new JSONObject();
		req.putOpt("cmd", name);
		req.putOpt("ssno", this.getSsno());
		req.putOpt("ssid", this.getSsid());
		return req;
		
	}
	
	public int getSsno() {
		return ssno;
	}

	@Override
	public void setSsno(int ssno) {
		this.ssno = ssno;
	}

	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
	}
	
}
