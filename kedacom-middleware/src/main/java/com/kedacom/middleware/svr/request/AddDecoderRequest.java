package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.domain.Devinfo;
import com.kedacom.middleware.svr.response.AddDecoderRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：AddDecoderRequest
 * 类描述：添加解码器
 * 创建人：lzs
 * 创建时间：2019-7-19 下午5:05:36
 * @version
 */
public class AddDecoderRequest extends SVRRequest{
	
	private int chnid; //解码通道id
	
	private Devinfo devinfo;
	
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("adddecchn");
		
		//设备信息 Data部分
		JSONObject dev = new JSONObject();
		dev.put("devtype", devinfo.getDvetype());
		dev.put("ipcprotetype",devinfo.getIpcprotetype() );	
		dev.put("manuid", devinfo.getManuid());	
		dev.put("devipaddr",devinfo.getDevipaddr());	
		dev.put("streamtransmode", devinfo.getStreamtransmode());	
		dev.put("szguid",devinfo.getSzguid() );	
		dev.put("szname", devinfo.getSzname());	
		dev.put("sztypename","");	
		dev.put("szdevuuid", "");	
		dev.put("szdevxaddr", "");	
		dev.put("szdevusername","");	
		dev.put("szdevuserpwd","" );	
		dev.put("szdevrtspurl", "");	
		dev.put("decaddtype", devinfo.getDecaddtype());	
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("chnid", chnid);
		data.put("devinfo", dev);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new AddDecoderRespose();
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

	public Devinfo getDevinfo() {
		return devinfo;
	}

	public void setDevinfo(Devinfo devinfo) {
		this.devinfo = devinfo;
	}
}
