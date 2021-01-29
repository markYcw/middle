package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.BurnStartResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class BurnStartRequest extends MTRequest{

	//刻录机类型
	private int mode;
	
	//文件类型：1刻录笔录文件；2：普通刻录-实时刻录；3：后期补刻
	private int filetype;
	
	//文件路径
	private String filepath;
	
	//是否封盘：0：不封盘；1封盘
	private int lock;
	
	
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("burnstartreq");
		
		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("filetype", filetype);
		json.put("mode", mode);
		json.put("filepath", filepath);
		json.put("lock", lock);
		
		//返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new BurnStartResponse();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getFiletype() {
		return filetype;
	}

	public void setFiletype(int filetype) {
		this.filetype = filetype;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
		this.lock = lock;
	}

}
