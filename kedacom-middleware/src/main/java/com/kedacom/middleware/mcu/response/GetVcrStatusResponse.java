package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.VcrChnStatus;
import com.kedacom.middleware.mcu.domain.VcrStatus;
import com.kedacom.middleware.mcu.request.GetVcrStatusRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取录像机状态列表（会议平台4.7版本特有）
 * @see GetVcrStatusRequest
 * @author YueZhipeng
 *
 */
public class GetVcrStatusResponse extends McuResponse {
	
	private List<VcrStatus> vcrstatus;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

		List<VcrStatus> list = new ArrayList<VcrStatus>();
		
		if(!jsonData.isNull("vcrstatus")){
			JSONArray vcrstatus = jsonData.optJSONArray("vcrstatus");
			
			if (vcrstatus != null) {
				for (int i = 0; i < vcrstatus.length(); i++) {
					JSONObject obj = vcrstatus.optJSONObject(i);
					VcrStatus vcrStatus = parseVcrStatus(obj);
					list.add(vcrStatus);
				}
			}
		}
		
		this.vcrstatus = list;
	}
	
	public static VcrStatus parseVcrStatus(JSONObject obj){

		boolean isonline = obj.optBoolean("isonline");
		int recabsid = obj.optInt("recabsid");
		int apsid = obj.optInt("apsid");
		String ip = obj.optString("ip");
		String alias = obj.optString("alias");
		int recchnnum = obj.optInt("recchnnum");
		int playchnnum = obj.optInt("playchnnum");
		boolean isspublic = obj.optBoolean("isspublic");
		boolean isoem = obj.optBoolean("isoem");
		int freespace = obj.optInt("freespace");
		int totalspace = obj.optInt("totalspace");
		
		List<VcrChnStatus> vcrChnStatus = new ArrayList<VcrChnStatus>();
		JSONArray chnstats = obj.optJSONArray("chnstat");
		if(chnstats != null){
			for(int j = 0; j < chnstats.length(); j++){
				JSONObject chnstat = chnstats.optJSONObject(j);
				
				int type = chnstat.optInt("type");
				int stat = chnstat.optInt("stat");
				int recmode = chnstat.optInt("recmode");
				int curprog = chnstat.optInt("curprog");
				int totaltime = chnstat.optInt("totaltime");
				String mtinfo = chnstat.optString("mtinfo");
				String confe164 = chnstat.optString("confe164");
				
				VcrChnStatus vcs = new VcrChnStatus();
				vcs.setType(type);
				vcs.setStat(stat);
				vcs.setRecMode(recmode);
				vcs.setCurProg(curprog);
				vcs.setTotalTime(totaltime);
				vcs.setMtinfo(mtinfo);
				vcs.setConfe164(confe164);
				
				vcrChnStatus.add(vcs);
			}
		}
		
		VcrStatus vcrstatus = new VcrStatus();
		vcrstatus.setIsonline(isonline);
		vcrstatus.setRecabsid(recabsid);
		vcrstatus.setApsid(apsid);
		vcrstatus.setIp(ip);
		vcrstatus.setAlias(alias);
		vcrstatus.setRecchnnum(recchnnum);
		vcrstatus.setPlaychnnum(playchnnum);
		vcrstatus.setIsspublic(isspublic);
		vcrstatus.setIsoem(isoem);
		vcrstatus.setFreespace(freespace);
		vcrstatus.setTotalspace(totalspace);
		vcrstatus.setVcrChnStatus(vcrChnStatus);
		
		return vcrstatus;
		
	}

	public List<VcrStatus> getVcrstatus() {
		return vcrstatus;
	}

	public void setVcrstatus(List<VcrStatus> vcrstatus) {
		this.vcrstatus = vcrstatus;
	}

}
