package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.domain.IPAddr;
import com.kedacom.middleware.mcu.response.StopMTMonitorResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停止浏览终端码流
 * @see StopMTMonitorResponse
 * @author YueZhipeng
 */
public class StopMTMonitorRequest extends McuRequest {

	/**
	 * 会议e164号
	 */
	private String confe164;
	
	/**
	 * 终端IP或者e164号
	 */
	private String mtinfo;
	
	/**
	 * 接受码流地址
	 */
	private IPAddr dstaddr;
	
	@Override
	public String toJson() throws JSONException {

		// Req部分
		JSONObject req = super.buildReq("stopmtmonitor");
		
		//dst部分
		JSONObject dstaddrJson = null;
		if(dstaddr != null){
			dstaddrJson = new JSONObject();
			dstaddrJson.put("ip", dstaddr.getIp());
			dstaddrJson.put("port", dstaddr.getPort());
		}
		
		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtinfo", mtinfo);
		data.putOpt("dstaddr", dstaddrJson);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StopMTMonitorResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
	}

	public IPAddr getDstaddr() {
		return dstaddr;
	}

	public void setDstaddr(IPAddr dstaddr) {
		this.dstaddr = dstaddr;
	}


}
