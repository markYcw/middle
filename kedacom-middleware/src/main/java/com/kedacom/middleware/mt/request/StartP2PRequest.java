package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.StartP2PResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 开启点对点会议
 * @author TaoPeng
 * @see StartP2PResponse
 */
public class StartP2PRequest extends MTRequest {

/*	{
		“req”:{ “name”:”startp2p”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		 “remotemt”:{
		“addrtype”:1,
		“ip”:”127.0.0.1”,
		“alias”:””
		}*/

	//终端4.7有效
	/** ip */
	public static final int ADDRTYPE_IP = 0;
	/** e164 */
	public static final int ADDRTYPE_e164 = 1;
	/** h323id */
	public static final int ADDRTYPE_h323id = 2;
	/** dialnum */
	public static final int ADDRTYPE_dialnum = 3;
	/** sipaddr */
	public static final int ADDRTYPE_sipaddr = 4;
	
	//主呼协议类型 - 终端5.0有效
	public static final int TYPE_h323 = 1;//H.323
	public static final int TYPE_sip = 2;//SIP
	
	/**
	 * 地址类型 {@link #ADDRTYPE_IP} / {@link #ADDRTYPE_e164} / {@link #ADDRTYPE_h323id} / {@link #ADDRTYPE_dialnum} / {@link #ADDRTYPE_sipaddr}
	 * 终端4.7有效
	 */
	private int addrType;
	
	/**
	 * 主呼协议类型 {@link #TYPE_h323} / {@link #TYPE_sip}
	 * 终端5.0有效
	 */
	private int type;
	
	/**
	 * 终端IP
	 */
	private String ip;
	
	/**
	 * 别名
	 */
	private String alias;
	
	/**
	 * 会议码率:512/768/1024/4096
	 */
	private int bitrate = 1024;
	
	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("startp2p");

		//Data部分
		JSONObject remotemt = new JSONObject();
		remotemt.put("addrtype", addrType);
		remotemt.put("type", type);
		remotemt.put("ip", ip);
		remotemt.put("alias", alias);
		remotemt.put("bitrate", bitrate);
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("remotemt", remotemt);
		
		//返回
		String ret = data.toString();
		return ret;
		
	}

	@Override
	public IResponse getResponse() {
		return new StartP2PResponse();
	}

	public int getAddrType() {
		return addrType;
	}

	public void setAddrType(int addrType) {
		this.addrType = addrType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
