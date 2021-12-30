package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.CapSupport;
import com.kedacom.middleware.mcu.domain.ConfeInfo;
import com.kedacom.middleware.mcu.request.GetConfInfoRequest;


import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取会议信息
 * @see GetConfInfoRequest
 * @author YueZhipeng
 * 
 */
public class GetConfInfoResponse extends McuResponse {
	private static final Logger log = Logger.getLogger(GetConfInfoResponse.class);

	private ConfeInfo confeInfo;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {

		super.parseResp(jsonData);

		try {
			JSONObject confeInfoJson = jsonData.getJSONObject("confinfo");
			JSONObject obj = confeInfoJson;
			int takemode = obj.optInt("takemode");
			int regtogk = obj.optInt("regtogk");
			int lockmode = obj.optInt("lockmode");
			int recmode = obj.optInt("recmode");
			String confname = obj.optString("confname");
			String confe164 = obj.optString("confe164");
			int confid = obj.optInt("confid");
			int encryptmode = obj.optInt("encryptmode");

			JSONObject capSupportJson = confeInfoJson.getJSONObject("capsupport");
			JSONObject obj1 = capSupportJson;
			int dstreamtype = obj1.optInt("dstreamtype");
			int dcmaxbitrate = obj1.optInt("dcmaxbitrate");
			int dcresolution = obj1.optInt("dcresolution");
			int dcframerate = obj1.optInt("dcframerate");
			int dcish239 = obj1.optInt("dcish239");
			int mvmaxbitrate = obj1.optInt("mvmaxbitrate");
			int mvmediatype = obj1.optInt("mvmediatype");
			int mvresolution = obj1.optInt("mvresolution");
			int mvframerate = obj1.optInt("mvframerate");
			int mamediatype = obj1.optInt("mamediatype");
			int svmaxbitrate = obj1.optInt("svmaxbitrate");
			int svmediatype = obj1.optInt("svmediatype");
			int svresolution = obj1.optInt("svresolution");
			int svframerate = obj1.optInt("svframerate");
			int samediatype = obj1.optInt("samediatype");

			CapSupport capsupport = new CapSupport();
			capsupport.setDstreamtype(dstreamtype);
			capsupport.setDcmaxbitrate(dcmaxbitrate);
			capsupport.setDcresolution(dcresolution);
			capsupport.setDcframerate(dcframerate);
			capsupport.setDcish239(dcish239);
			capsupport.setMvmaxbitrate(mvmaxbitrate);
			capsupport.setMvmediatype(mvmediatype);
			capsupport.setMvresolution(mvresolution);
			capsupport.setMvframerate(mvframerate);
			capsupport.setMamediatype(mamediatype);
			capsupport.setSvmaxbitrate(svmaxbitrate);
			capsupport.setSvmediatype(svmediatype);
			capsupport.setSvresolution(svresolution);
			capsupport.setSvframerate(svframerate);
			capsupport.setSamediatype(samediatype);

			String bitrate = obj.optString("bitrate");
			String starttime = obj.optString("starttime");
			String duration = obj.optString("duration");
			String speekermt = obj.optString("speekermt");
			String chairmt = obj.optString("chairmt");

			Set<String> set1 = new HashSet<String>();
			JSONArray mtinfos = obj.optJSONArray("mtinfos");
			log.debug("[MTINFOS]" + (mtinfos != null ? mtinfos.length():0));
			if(mtinfos != null){
			
				for (int m = 0; m < mtinfos.length(); m++) {
					try {
						String mt = mtinfos.getString(m);
						set1.add(mt);
					} catch (JSONException e) {
						throw new DataException(e.getMessage(), e);
					}
				}
			}
			
			Set<String> set2 = new HashSet<String>();
			JSONArray joinedmtinfos = obj.optJSONArray("joinedmtinfos");
			
			if(joinedmtinfos != null){
				for (int n = 0; n < joinedmtinfos.length(); n++) {
					try {
						String mt = mtinfos.getString(n);
						set2.add(mt);
					} catch (JSONException e) {
						throw new DataException(e.getMessage(), e);
					}
				}
			}
				
			ConfeInfo confeInfos = new ConfeInfo();
			confeInfos.setTakemode(takemode);
			confeInfos.setRegtogk(regtogk);
			confeInfos.setLockmode(lockmode);
			confeInfos.setRecmode(recmode);
			confeInfos.setConfname(confname);
			confeInfos.setConfe164(confe164);
			confeInfos.setConfid(confid);
			confeInfos.setEncryptmode(encryptmode);
			confeInfos.setCapsupport(capsupport);
			confeInfos.setBitrate(bitrate);
			confeInfos.setStarttime(starttime);
			confeInfos.setDuration(duration);
			confeInfos.setSpeekermt(speekermt);
			confeInfos.setChairmt(chairmt);
			confeInfos.setMtinfos(set1);
			confeInfos.setJoinedmtinfos(set2);

			this.confeInfo = confeInfos;

		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}

	}

	public ConfeInfo getConfeInfo() {
		return confeInfo;
	}

	public void setConfeInfo(ConfeInfo confeInfo) {
		this.confeInfo = confeInfo;
	}

}
