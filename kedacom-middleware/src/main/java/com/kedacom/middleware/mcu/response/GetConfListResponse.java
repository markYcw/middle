package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.Confinfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取视频会议列表（中间件5.0接口）
 * @author LinChaoYu
 *
 */
public class GetConfListResponse extends McuResponse {

	private List<Confinfo> confs;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
		JSONArray jsonConfs = jsonData.optJSONArray("confs");
		if(jsonConfs != null){
			confs = new ArrayList<Confinfo>();
			
			for(int i = 0 ; i < jsonConfs.length(); i ++){
				try{
					JSONObject obj = jsonConfs.getJSONObject(i);
					
					Confinfo confInfo = new Confinfo();
					confInfo.setName(obj.optString("name"));
					confInfo.setE164(obj.optString("conf_id"));
					confInfo.setBitrate(obj.optInt("bitrate"));
					confInfo.setDuration(obj.optInt("duration"));
					//其他参数暂时未解析
					
					
					confs.add(confInfo);
				}catch(JSONException e){
					throw new DataException(e.getMessage(), e);
				}
			}
		}
	}

	public List<Confinfo> getConfs() {
		return confs;
	}

	public void setConfs(List<Confinfo> confs) {
		this.confs = confs;
	}
}
