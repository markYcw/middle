package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.domain.Streaminfos;
import com.kedacom.middleware.mt.response.LoginResponse;
import com.kedacom.middleware.mt.response.StartRecVCRResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Request：开始录像（硬盘录像机）
 * @see LoginResponse
 * @author TaoPeng
 * 
 */
public class StartRecVCRRequest extends MTRequest {

	private String ip;
	private int mode;
	private String labletext;
	private int groupid;
	private int publishtype;
	private String recfilename;
	private int streamnum;
	private List<Streaminfos> streaminfos;
	

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("recservicereq");
		
		//Data部分
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		
		json.put("req", req);
		json.put("mode", 2);//VRS2KB(2)
		json.put("ip", ip);
		json.put("labletext", labletext);
		json.put("groupid", 1);
		json.put("publishtype", 1);
		json.put("recfilename", recfilename);
		json.put("streamnum", streaminfos.size());
		for (int i = 0; i < streaminfos.size(); i++) {
			Streaminfos streaminfo = streaminfos.get(i);
//			streaminfo.setAudioattr(streaminfos.get(i).getAudioattr());
//			streaminfo.setMediacrypt(streaminfos.get(i).getMediacrypt());
//			streaminfo.setNetaddr(streaminfos.get(i).getNetaddr());
//			streaminfo.setStreamtype(streaminfos.get(i).getStreamtype());
//			streaminfo.setVideoattr(streaminfos.get(i).getVideoattr());
			array.put(new JSONObject(streaminfo));
		}
		json.put("streaminfos", array);
		
		//返回
		String str = json.toString();
		return str;
		
	}
	
	@Override
	public IResponse getResponse() {
		return new StartRecVCRResponse();
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public String getLabletext() {
		return labletext;
	}
	public void setLabletext(String labletext) {
		this.labletext = labletext;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getPublishtype() {
		return publishtype;
	}
	public void setPublishtype(int publishtype) {
		this.publishtype = publishtype;
	}
	public String getRecfilename() {
		return recfilename;
	}
	public void setRecfilename(String recfilename) {
		this.recfilename = recfilename;
	}
	public int getStreamnum() {
		return streamnum;
	}
	public void setStreamnum(int streamnum) {
		this.streamnum = streamnum;
	}
	public List<Streaminfos> getStreaminfos() {
		return streaminfos;
	}
	public void setStreaminfos(List<Streaminfos> streaminfos) {
		this.streaminfos = streaminfos;
	}

}
