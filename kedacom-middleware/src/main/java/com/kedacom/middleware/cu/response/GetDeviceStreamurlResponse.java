package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.domain.Urlinfo;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.获取通道播放URL
 * 
 * @author zhangyanan
 * 
 */
public class GetDeviceStreamurlResponse extends CuResponse {
	private List<Urlinfo> urlinfos=new ArrayList<Urlinfo>();
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		JSONArray urlinfoArr = jsonData.optJSONArray("urlinfos");
		if(urlinfoArr != null && urlinfoArr.length() > 0){
			for (int i = 0; i < urlinfoArr.length() ; i++) {
				JSONObject jsonObj = urlinfoArr.optJSONObject(i);
				Urlinfo urlinfo=new Urlinfo();
				urlinfo.setUrl(jsonObj.optString("url"));
				urlinfo.setFactory(jsonObj.optString("factory"));
				urlinfo.setStreamtype(jsonObj.optInt("streamtype"));
				this.urlinfos.add(urlinfo);
			}
		}
	}
	public List<Urlinfo> getUrlinfos() {
		return urlinfos;
	}
	public void setUrlinfos(List<Urlinfo> urlinfos) {
		this.urlinfos = urlinfos;
	}
	
}
