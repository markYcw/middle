package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StartPartmixResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * 定制混音
 * @see StartPartmixResponse
 * @author YueZhipeng
 *
 */
public class StartPartmixRequest extends McuRequest {
	private String confe164;
	private Set<String> mtinfos;
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("startpartmix");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.putOpt("mtinfos", mtinfos);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StartPartmixResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public Set<String> getMtinfos() {
		return mtinfos;
	}

	public void setMtinfos(Set<String> mtinfos) {
		this.mtinfos = mtinfos;
	}

}
