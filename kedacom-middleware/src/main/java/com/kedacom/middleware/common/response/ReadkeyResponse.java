package com.kedacom.middleware.common.response;

import com.kedacom.middleware.common.CommonResponse;
import com.kedacom.middleware.common.request.ReadkeyRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 读Key
 * @see ReadkeyRequest
 * @author TaoPeng
 *
 */
public class ReadkeyResponse extends CommonResponse {

	/**
	 * 0正常;
	 * 1没有key,没有找到与type匹配的key;
	 * 2key错误；
	 * 3系统错误；
	 */
	private int keystate;
	
	/**
	 * 是否是usbkey
	 */
	private boolean usbkey;
	
	private Map<String, String> attributes = new HashMap<String, String>();
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		this.parseResp(jsonData);
		
		this.keystate = jsonData.optInt("keystate");
		
		int isusbkey = jsonData.optInt("isusbkey");
		if(isusbkey == 1)
			this.usbkey = true;
		else
			this.usbkey = false;
		
		JSONObject keyval = jsonData.optJSONObject("keyval");
		if(keyval != null){
			Iterator<String> keys = keyval.keys();
			while(keys.hasNext()){
				String key = keys.next();
				String value = keyval.optString(key);
				this.attributes.put(key, value);
			}
		}
		
	}

	public int getKeystate() {
		return keystate;
	}

	public boolean isUsbkey() {
		return usbkey;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

}
