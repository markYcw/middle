package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.domain.Cu;
import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.LoginResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 1. 登陆平台
 * 
 * @author TaoPeng
 * @see LoginResponse
 */
public class LoginRequest extends CuRequest {

	
	/**
	 * 平台类型。0：监控平台1.0；7：监控平台2.0
	 */
	private int devtype = Cu.CU2;
	

	/**
	 * 平台IP
	 */
	private String ip;
	/**
	 * 平台端口号
	 */
	private int port = 80;
	/**
	 * 平台用户名
	 */
	private String user;
	/**
	 * 平台密码
	 */
	private String pwd;
	
	/**
	 * 超时时间（仅仅1.0监控平台支持）。单位：毫秒
	 */
	private long timeout = 15000;
	/**
	 * 网络代理ip
	 */
	private String proxyinfo_ip;
	/**
	 * 网络代理端口号
	 */
	private int proxyinfo_port = 0;
	/**
	 * 网络代理用户名
	 */
	private String proxyinfo_user;
	/**
	 * 网络代理密码
	 */
	private String proxyinfo_pwd;
	
	/**
	 * 查看码流地址（平台要求登陆时即将码流查看IP地址发过去）
	 */
	private String streaminfo_ip;
	/**
	 * 查看码流起始端口号
	 */
	private int streaminfo_port;
	/**
	 * 查看码流最大数
	 */
	private int streaminfo_num;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("login");

		// 平台信息 platforminfo
		JSONObject platforminfo = new JSONObject();
		platforminfo.putOpt("ip", this.ip);
		platforminfo.putOpt("port", this.port);
		platforminfo.putOpt("user", this.user);
		platforminfo.putOpt("pwd", this.pwd);
		
		// 代理信息 proxyinfo
		JSONObject proxyinfo = new JSONObject();
		proxyinfo.putOpt("ip", this.proxyinfo_ip);
		if(proxyinfo_port > 0){
			proxyinfo.putOpt("port", this.proxyinfo_port);
		}
		proxyinfo.putOpt("user", this.proxyinfo_user);
		proxyinfo.putOpt("pwd", this.proxyinfo_pwd);

		//码流转发端口（仅仅1.0平台支持）
		JSONObject streaminfo = new JSONObject();
		streaminfo.putOpt("ip", this.streaminfo_ip);
		if(streaminfo_port > 0){
			streaminfo.putOpt("port", this.streaminfo_port);
		}
		if(streaminfo_num > 0){
			streaminfo.putOpt("num", this.streaminfo_num);
		}

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("devtype", devtype);
		data.putOpt("timeout", this.timeout);
		data.putOpt("proxyinfo", proxyinfo);
		data.putOpt("platforminfo", platforminfo);
		data.putOpt("streaminfo", streaminfo);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new LoginResponse();
	}

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getProxyinfo_ip() {
		return proxyinfo_ip;
	}

	public void setProxyinfo_ip(String proxyinfo_ip) {
		this.proxyinfo_ip = proxyinfo_ip;
	}

	public int getProxyinfo_port() {
		return proxyinfo_port;
	}

	public void setProxyinfo_port(int proxyinfo_port) {
		this.proxyinfo_port = proxyinfo_port;
	}

	public String getProxyinfo_user() {
		return proxyinfo_user;
	}

	public void setProxyinfo_user(String proxyinfo_user) {
		this.proxyinfo_user = proxyinfo_user;
	}

	public String getProxyinfo_pwd() {
		return proxyinfo_pwd;
	}

	public void setProxyinfo_pwd(String proxyinfo_pwd) {
		this.proxyinfo_pwd = proxyinfo_pwd;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getStreaminfo_ip() {
		return streaminfo_ip;
	}

	public void setStreaminfo_ip(String streaminfo_ip) {
		this.streaminfo_ip = streaminfo_ip;
	}

	public int getStreaminfo_port() {
		return streaminfo_port;
	}

	public void setStreaminfo_port(int streaminfo_port) {
		this.streaminfo_port = streaminfo_port;
	}

	public int getStreaminfo_num() {
		return streaminfo_num;
	}

	public void setStreaminfo_num(int streaminfo_num) {
		this.streaminfo_num = streaminfo_num;
	}

}
