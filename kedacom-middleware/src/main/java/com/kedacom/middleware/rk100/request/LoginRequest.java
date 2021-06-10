package com.kedacom.middleware.rk100.request;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.rk100.response.LoginResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录RK100服务器
 *
 * @author LinChaoYu
 * @see LoginResponse
 */
public class LoginRequest extends RkRequest {

    /**
     * GK的IP地址
     */
    private String ip;

    /**
     * 端口
     */
    private int port;

    /**
     * 设备类型
     */
    private int devtype = DeviceType.RK100.getValue();

    @Override
    public String toJson() throws JSONException {

        //Req部分
        JSONObject req = super.buildReq("login");

        //data部分
        JSONObject data = new JSONObject();
        data.put("devtype", devtype);
        data.put("req", req);
        data.put("ip", this.ip);
        data.put("port", this.port);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new LoginResponse();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getDevtype() {
        return devtype;
    }

    public void setDevtype(int devtype) {
        this.devtype = devtype;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
