package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.domain.MTParam;
import com.kedacom.middleware.mcu.domain.VmpParam;
import com.kedacom.middleware.mcu.response.StartVmpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 开始画面合成
 * 
 * @see StartVmpResponse
 * @author YueZhipeng
 * 
 */
public class StartVmpRequest extends McuRequest {

	/**
	 * 会议e164号
	 */
	private String confe164;

	private VmpParam vmpparam;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("startvmp");

		// mtp部分
		JSONArray mtparams = new JSONArray();
		List<MTParam> list = vmpparam.getMtparams();
		for (MTParam p : list) {
			// JSONObject jp = new JSONObject(p); //也可以这样构造
			JSONObject jp = new JSONObject();
			jp.putOpt("mtinfo", p.getMtinfo());
			jp.put("videotype", p.getVideotype());
			jp.put("isused", p.isUsed());
			mtparams.put(jp);
		}

		// vmp部分
		JSONObject vmp = new JSONObject();
		VmpParam vp = this.getVmpparam();
		vmp.put("isbrdst", vp.isIsbrdst());
		vmp.put("isauto", vp.isIsauto());
		vmp.put("style", vp.getStyle());
		vmp.put("mtparams", mtparams);

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("vmpparam", vmp);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StartVmpResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public VmpParam getVmpparam() {
		return vmpparam;
	}

	public void setVmpparam(VmpParam vmpparam) {
		this.vmpparam = vmpparam;
	}

}
