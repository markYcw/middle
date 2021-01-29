package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.IPAddr;
import com.kedacom.middleware.mcu.request.StartMTMonitorRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 开始浏览终端码流
 * @see StartMTMonitorRequest
 * @author YueZhipeng
 *
 */
public class StartMTMonitorResponse extends McuResponse {
	
	private IPAddr audiortcp;
	
	private IPAddr videortcp;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
		try{
			IPAddr audiortcps = new IPAddr();
			
			if(!jsonData.isNull("audiortcp")){
				JSONObject audiortcp = jsonData.getJSONObject("audiortcp");
				
				if(audiortcp != null){
					String ip = audiortcp.optString("ip");
					int port = audiortcp.optInt("port");
			
					audiortcps.setIp(ip);
					audiortcps.setPort(port);
				}
			}
			
			IPAddr videortcps = new IPAddr();
			
			if(!jsonData.isNull("videortcp")){
				JSONObject videortcp = jsonData.getJSONObject("videortcp");
				
				if(videortcp != null){
					String ip2 = videortcp.optString("ip");
					int port2 = videortcp.optInt("port");
					
					videortcps.setIp(ip2);
					videortcps.setPort(port2);
				}
			}
			
			this.audiortcp = audiortcps;
			this.videortcp = videortcps;
		}catch(JSONException e){
			throw new DataException(e.getMessage(), e);
		}
	}

	public IPAddr getAudiortcp() {
		return audiortcp;
	}

	public void setAudiortcp(IPAddr audiortcp) {
		this.audiortcp = audiortcp;
	}

	public IPAddr getVideortcp() {
		return videortcp;
	}

	public void setVideortcp(IPAddr videortcp) {
		this.videortcp = videortcp;
	}

}
