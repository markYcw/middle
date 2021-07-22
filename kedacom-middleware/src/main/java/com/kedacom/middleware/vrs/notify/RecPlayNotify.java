package com.kedacom.middleware.vrs.notify;

import com.kedacom.middleware.cu.domain.TvWall;
import com.kedacom.middleware.exception.DataException;

import com.kedacom.middleware.vrs.domain.Audio;
import com.kedacom.middleware.vrs.domain.SrcAddr;
import com.kedacom.middleware.vrs.domain.Video;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 播放录像通知
 * @author ycw
 * @date 2021/5/13 14:15
 */
@Data
public class RecPlayNotify extends VRSNotify {

    /**
     * 命令值
     */
    public static final String NAME = "recplay";
    /**
     * Svr rtcp地址
     */
    public static List<SrcAddr> srcAddrs = new ArrayList<SrcAddr>();

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        JSONArray array = jsonData.optJSONArray("srcaddr");
        if(array != null && array.length() > 0){
            for(int i = 0; i<array.length();i++){
                SrcAddr srcAddr = new SrcAddr();
                JSONObject jsonObject = array.optJSONObject(i);
                //video部分
                Video videoIn = new Video();
                JSONObject video = jsonObject.optJSONObject("video");
                videoIn.setIp(video.optString("ip"));
                videoIn.setPort(video.optString("port"));
                //audio部分
                Audio audioIn = new Audio();
                JSONObject audio = jsonObject.optJSONObject("audio");
                audioIn.setIp(audio.optString("ip"));
                audioIn.setPort(audio.optString("port"));
                //srcAddr设值
                srcAddr.setVideo(videoIn);
                srcAddr.setAudio(audioIn);
                srcAddrs.add(srcAddr);
            }
        }

    }
}
