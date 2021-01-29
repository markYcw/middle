package com.kedacom.middleware.common.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.common.CommonRequest;
import com.kedacom.middleware.common.response.ReadkeyResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 读Key
 * @see ReadkeyResponse
 * @author TaoPeng
 *
 */
public class ReadkeyRequest extends CommonRequest {

	/**
	 * Key类型，对应KEY中的KEY_LICENSE_TYPE字段
	 */
	private String type;
	
	/**
	 * 文件key路径。仅文件key有效。默认值c:\\kedacom\\licenses\\kedalicense.key
	 * 或/usr/bin/kedalicense.key
	 */
	private String filekey;
	
	@Override
	public String toJson() throws JSONException {

		JSONObject req = super.buildReq("readkey");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.putOpt("type", type);
		data.putOpt("filekey", filekey);
		
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new ReadkeyResponse();
	}

	public String getType() {
		return type;
	}

	public String getFilekey() {
		return filekey;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFilekey(String filekey) {
		this.filekey = filekey;
	}

	
}
