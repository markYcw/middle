package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StopLookTVWallResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 终端停止上墙
 * @see StopLookTVWallResponse 
 * @author YueZhipeng
 *
 */
public class StopLookTVWallRequest extends McuRequest {
	
	/**
	 * 会议e164号
	 */
	private String confe164;
	
	/**
	 * 电视墙ID
	 * 会议平台5.0及以上版本时，电视墙ID为字符串
	 */
	private String eqpid;
	
	/**
	 * 电视墙通道(0~1)（会议平台4.7版本有效）
	 */
	private int chn;
	
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("stoplooktvwall");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("eqpid", eqpid);
		data.put("chn", chn);	
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StopLookTVWallResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getEqpid() {
		return eqpid;
	}

	public void setEqpid(String eqpid) {
		this.eqpid = eqpid;
	}

	public int getChn() {
		return chn;
	}

	public void setChn(int chn) {
		this.chn = chn;
	}

}
