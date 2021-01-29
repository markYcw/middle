package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.VcrRecorderStatus;
import com.kedacom.middleware.mcu.request.GetRecorderStatusRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取录像机状态（会议平台5.0及以上版本特有）
 * @see GetRecorderStatusRequest
 * @author LinChaoYu
 *
 */
public class GetRecorderStatusResponse extends McuResponse {
	
	private VcrRecorderStatus recStatus;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

		String videoName = jsonData.optString("videoname");//录像文件名
		recStatus.setVideoName(videoName);
		
		int recorderType = jsonData.optInt("recordertype");//录像类型
		recStatus.setRecorderType(recorderType);
		
		int state = jsonData.optInt("state");//录像状态
		recStatus.setState(state);
		
		int anonymous = jsonData.optInt("anonymous");//是否支持免登陆观看直播
		recStatus.setAnonymous(anonymous);
		
		int recorderMode = jsonData.optInt("recordermode");//录像模式
		recStatus.setRecorderMode(recorderMode);
		
		int mainStream = jsonData.optInt("mainstream");//是否录主格式码流（视频+音频）
		recStatus.setMainStream(mainStream);
		
		int dualStream = jsonData.optInt("dualstream");//是否录双流
		recStatus.setDualStream(dualStream);
		
		int publishMode = jsonData.optInt("publishmode");//发布类型
		recStatus.setPublishMode(publishMode);
		
		int currentProgress = jsonData.optInt("currentprogress");//当前录像进度, 单位为: 秒
		recStatus.setCurrentProgress(currentProgress);
		
		List<String> members = new ArrayList<String>();
		JSONArray jsonArray = jsonData.optJSONArray("members");
		if(jsonArray != null){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonMem = jsonArray.optJSONObject(i);
				
				members.add(jsonMem.optString("mtinfo"));
			}
		}
		recStatus.setMembers(members);
	}

	public VcrRecorderStatus getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(VcrRecorderStatus recStatus) {
		this.recStatus = recStatus;
	}
	
}
