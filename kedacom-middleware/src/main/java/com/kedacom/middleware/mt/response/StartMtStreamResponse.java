package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.domain.Tcp;
import com.kedacom.middleware.mt.request.StartMtStreamRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：开始浏览码流
 * @author LiPengJia
 * @see StartMtStreamRequest
 */
public class StartMtStreamResponse extends MTResponse {

/*	{
		“resp”:{ “name”:”startmtstream”,
		      “ssno”:1,
		      “ssid”:5，
		      “errorcode”:0
		},
		“audiortcp”:{“ip”:”127.0.0.1”,”port”:7000},
		“videortcp”:{“ip”:”127.0.0.1”,”port”:8000},
		}*/

	/**
	 * 音频回传端口 {@link Tcp#ip} {@link Tcp#port} 
	 */
	private Tcp audiorTcp;
	/**
	 * 视频回传端口 {@link Tcp#ip} {@link Tcp#port} 
	 */
	private Tcp videorTcp;
	@Override
	public void parseData(JSONObject jsonData) throws DataException{
		super.parseResp(jsonData);
//		try{
		//audiortcp
//		JSONObject audiortcp = jsonData.getJSONObject("audiortcp");
//		String audiortcpIp = audiortcp.optString("ip");
//		audiorTcp.setIp(audiortcpIp);
//		int audiortcpPort = audiortcp.optInt("port");
//		audiorTcp.setPort(audiortcpPort);
//		//videorTcp
//		JSONObject videortcp = jsonData.getJSONObject("videortcp");
//		String videortcpIp = videortcp.optString("ip");
//		videorTcp.setIp(videortcpIp);
//		int videortcpPort = videortcp.optInt("port");
//		videorTcp.setPort(videortcpPort);
//		}catch(JSONException e){
//			throw new DataException(e.getMessage(), e);
//		}
	}
	public Tcp getAudiorTcp() {
		return audiorTcp;
	}
	public void setAudiorTcp(Tcp audiorTcp) {
		this.audiorTcp = audiorTcp;
	}
	public Tcp getVideorTcp() {
		return videorTcp;
	}
	public void setVideorTcp(Tcp videorTcp) {
		this.videorTcp = videorTcp;
	}


}
