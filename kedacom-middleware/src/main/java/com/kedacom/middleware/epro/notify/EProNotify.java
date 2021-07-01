package com.kedacom.middleware.epro.notify;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.INotify;
import org.json.JSONObject;

/**
 * @Description E10PRO服务 通知(notify)的基类。
 * @param:
 * @return:
 * @author:ycw
 * @date: 2021/06/30 14:50
 */
public abstract class EProNotify implements INotify {

    /**
     * 会话标识
     */
    private int ssid;
    private int ssno;

    /**
     * 解析nty节点
     *
     * @param jsonData
     */
    protected void parseNty(JSONObject jsonData) {
        JSONObject req = jsonData.optJSONObject("nty");
        if (req != null) {
            this.ssid = req.optInt("ssid");
            this.ssno = req.optInt("ssno");
        }
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.GK;
    }

    public int getSsid() {
        return ssid;
    }

    public void setSsid(int ssid) {
        this.ssid = ssid;
    }

    public int getSsno() {
        return ssno;
    }

    public void setSsno(int ssno) {
        this.ssno = ssno;
    }
}
