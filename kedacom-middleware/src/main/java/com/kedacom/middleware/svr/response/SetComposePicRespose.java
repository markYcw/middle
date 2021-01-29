package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
* @ClassName: SetComposePicRespose 
* @Description: 设置画面合成respose
* @author lzs 
* @date 2019-7-11 上午9:35:30 
* @version V1.0
 */
public class SetComposePicRespose extends SVRResponse {
	
	private int errorcode;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		JSONObject req = jsonData.optJSONObject("resp");
		if(req != null){
			this.errorcode = req.optInt("errorcode");
		}
	}

	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

}
