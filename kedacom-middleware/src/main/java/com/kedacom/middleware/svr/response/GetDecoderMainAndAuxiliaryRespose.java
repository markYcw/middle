package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：GetDecoderMainAndauxiliaryRespose
 * 类描述：获取解码器的解码通道和主辅流
 * 创建人：lzs
 * 创建时间：2019-8-7 下午4:52:23
 */
public class GetDecoderMainAndAuxiliaryRespose extends SVRResponse {

    //所解的音视频通道，0表示合成通道，1~16表示具体的IPC，17~28：远程点输入输出相关通道，
    //64：无效通道表示不使用35：远程审讯/庭审通道， 36：外接视频源通道
    private int encchnid;

    private int secstream;//解码主辅流


    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.encchnid = jsonData.optInt("encchnid");
        this.secstream = jsonData.optInt("secstream");
    }

    public int getEncchnid() {
        return encchnid;
    }

    public void setEncchnid(int encchnid) {
        this.encchnid = encchnid;
    }

    public int getSecstream() {
        return secstream;
    }

    public void setSecstream(int secstream) {
        this.secstream = secstream;
    }

}
