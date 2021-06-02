package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * @ClassName GetSvrComposePicResponse
 * @Description 获取画面合成设置返回参数
 * @Author zlf
 * @Date 2021/5/31 14:30
 */
@Data
public class GetSvrComposePicResponse extends SVRResponse {

    private Integer videoresolution;//分辨率

    private Integer borderwidth;//边框宽度

    private Integer mergestyle;//自定义画面风格所使用的基础画面风格。 2816 1.0不支持

    private Integer picinfonum;//有效合成通道数

    private Integer[] chnid;//各画面信息配置

    private Integer[] pictype;//各个画面主辅流配置0：主流 1：辅流

    /**
     * 解析数据。
     *
     * @param jsonData 符合JSON规范的字符串。
     * @throws DataException
     */
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        this.videoresolution = jsonData.optInt("videoresolution");
        this.borderwidth = jsonData.optInt("borderwidth");
        this.mergestyle = jsonData.optInt("mergestyle");
        this.picinfonum = jsonData.optInt("picinfonum");

        Integer[] picinfo = (Integer[]) jsonData.opt("picinfo");
        this.chnid = picinfo;

        Integer[] pictype = (Integer[]) jsonData.opt("pictype");
        this.pictype = pictype;
    }

}
