package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 1. 登陆平台
 * 
 * @author dengjie
 * 
 */
public class LoginResponse extends CuResponse {

	/**
	 * 用户编号（仅仅监控平台1.0支持），客户端在平台的唯一标识，本地实时浏览的时候会用到
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 */
	private String userno;

	/**
	 * 当前平台域编号（仅仅监控平台1.0支持）
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 */
	private String cmuno;

	/**
	 * Nat穿越ip（码流接收端和平台不再同一个网段时会用到）（仅仅监控平台1.0支持）
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 */
	private String natIp;

	/**
	 * Nat穿越port（仅仅监控平台1.0支持）
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 */
	private int natPort;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

		JSONObject user = jsonData.optJSONObject("user");
		if (user != null) {
			this.userno = user.optString("userno");
			this.cmuno = user.optString("cmuno");
		}

		JSONObject nat = jsonData.optJSONObject("nat");
		if (nat != null) {
			this.natIp = nat.optString("ip");
			this.natPort = nat.optInt("port");
		}

	}

	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @return
	 */
	public String getUserno() {
		return userno;
	}

	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @return
	 */
	public void setUserno(String userno) {
		this.userno = userno;
	}

	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @return
	 */
	public String getCmuno() {
		return cmuno;
	}

	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @return
	 */
	public void setCmuno(String cmuno) {
		this.cmuno = cmuno;
	}

	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @return
	 */
	public String getNatIp() {
		return natIp;
	}

	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @return
	 */
	public void setNatIp(String natIp) {
		this.natIp = natIp;
	}

	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @return
	 */
	public int getNatPort() {
		return natPort;
	}

	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @return
	 */
	public void setNatPort(int natPort) {
		this.natPort = natPort;
	}

}
