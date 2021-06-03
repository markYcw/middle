package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.domain.VRSRecInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询录像
 * 
 * @author LinChaoYu
 *
 */
public class VrsQueryRecResponse extends VRSResponse {

	/**
	 * 录像总数
	 */
	private int totlenum;
	
	/**
	 * 录像集合
	 */
	private List<VRSRecInfo> resInfos = new ArrayList<VRSRecInfo>();
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
		this.totlenum = jsonData.optInt("totlenum");
		
		JSONArray jsonRecinfo = jsonData.optJSONArray("recinfo");
		if(jsonRecinfo != null){
			
			resInfos = new ArrayList<VRSRecInfo>();
			
			for(int i = 0 ; i < jsonRecinfo.length(); i ++){
				try{
					JSONObject obj = jsonRecinfo.getJSONObject(i);
					String name = obj.optString("name");
					
					String rtspurl = null;// 播放地址
					if(!obj.isNull("rtspurl"))
						rtspurl = obj.optString("rtspurl");
					
					int duration = 0;// 持续时间（单位：秒）
					if(!obj.isNull("duration"))
						duration = obj.optInt("duration");
					
					String createtime = null;// 创建时间
					if(!obj.isNull("createtime"))
						createtime = obj.getString("createtime");
					
					VRSRecInfo rec = new VRSRecInfo();
					rec.setName(name);
					rec.setRtspurl(rtspurl);
					rec.setCreatetime(createtime);
					rec.setDuration(duration);
				
					resInfos.add(rec);
				}catch(JSONException e){
					throw new DataException(e.getMessage(), e);
				}
			}
		}
	}

	public int getTotlenum() {
		return totlenum;
	}

	public void setTotlenum(int totlenum) {
		this.totlenum = totlenum;
	}

	public List<VRSRecInfo> getResInfos() {
		return resInfos;
	}

	public void setResInfos(List<VRSRecInfo> resInfos) {
		this.resInfos = resInfos;
	}
}
