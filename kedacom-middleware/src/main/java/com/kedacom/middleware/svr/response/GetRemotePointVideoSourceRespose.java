package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.RemotePointVideoSourceInfo;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：GetRemotePointVideoSourceRespose
 * 类描述：
 * 创建人：lzs
 * 创建时间：2019-8-8 上午9:47:47
 */
public class GetRemotePointVideoSourceRespose extends SVRResponse {

    private RemotePointVideoSourceInfo remotePointVideoSourceInfo;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        remotePointVideoSourceInfo = new RemotePointVideoSourceInfo();
        remotePointVideoSourceInfo.setOutvideochnid(jsonData.optInt("outvideochnid"));
        remotePointVideoSourceInfo.setH323secvideochnid(jsonData.optInt("h323secvideochnid"));
        remotePointVideoSourceInfo.setRemmergestate(jsonData.optInt("remmergestate"));
    }

    public RemotePointVideoSourceInfo getRemotePointVideoSourceInfo() {
        return remotePointVideoSourceInfo;
    }

    public void setRemotePointVideoSourceInfo(RemotePointVideoSourceInfo remotePointVideoSourceInfo) {
        this.remotePointVideoSourceInfo = remotePointVideoSourceInfo;
    }
}
