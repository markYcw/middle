package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.SetComposePicRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
* @ClassName: SetComposePicRequest 
* @Description:  设置合成画面 
* @author lzs 
* @date 2019-7-10 下午5:07:44 
* @version V1.0
 */
public class SetComposePicRequest extends SVRRequest{
	
	private int videoresolution;//分辨率
	
	private int borderwidth;//边框宽度
	
	private int mergestyle;//自定义画面风格所使用的基础画面风格。 2816 1.0不支持
	
	private int picinfonum;//有效合成通道数
	
	private int [] chnid;

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("setmp");
		
		//Data部分
		JSONObject borderColor = new JSONObject();
		borderColor.put("red", 255);
		borderColor.put("green", 0);
		borderColor.put("blue", 0);
		
		//int [] chnid = new int[]{6,2,3,4,5,6,64};//画面合成小画面
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("bordercolor", borderColor);
		data.put("picinfo", chnid);
		data.put("videoresolution", videoresolution);
		data.put("borderwidth", borderwidth);
		data.put("mergestyle", mergestyle);
		data.put("picinfonum", picinfonum);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new SetComposePicRespose();
		
	}

	public int getVideoresolution() {
		return videoresolution;
	}

	public void setVideoresolution(int videoresolution) {
		this.videoresolution = videoresolution;
	}

	public int getBorderwidth() {
		return borderwidth;
	}

	public void setBorderwidth(int borderwidth) {
		this.borderwidth = borderwidth;
	}

	public int getMergestyle() {
		return mergestyle;
	}

	public void setMergestyle(int mergestyle) {
		this.mergestyle = mergestyle;
	}

	public int getPicinfonum() {
		return picinfonum;
	}

	public void setPicinfonum(int picinfonum) {
		this.picinfonum = picinfonum;
	}

	public int[] getChnid() {
		return chnid;
	}

	public void setChnid(int[] chnid) {
		this.chnid = chnid;
	}
}
