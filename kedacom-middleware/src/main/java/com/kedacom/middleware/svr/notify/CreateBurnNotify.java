package com.kedacom.middleware.svr.notify;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.CreateBurnResponseInfo;
import lombok.Data;
import org.json.JSONObject;

/**
 * @ClassName CreateBurnNotify
 * @Description 创建刻录通知
 * @Author ycw
 * @Date 2021/12/2 13:41
 */
@Data
public class CreateBurnNotify extends SVRNotify {

    /**
     * 命令值
     */
    public static final String NAME = "createburn";

    public static CreateBurnResponseInfo burnTask = CreateBurnResponseInfo.builder().build();

    /**
     * 解析数据。
     *
     * @param jsonData 符合JSON规范的字符串。
     */
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
        JSONObject burntask = jsonData.optJSONObject("burntask");

        burnTask.setBurntaskdbid(burntask.optInt("burntaskdbid"));
        burnTask.setBurntaskid(burntask.optInt("burntaskid"));
        burnTask.setDurationtime(burntask.optString("durationtime"));
        burnTask.setStarttime(burntask.optString("starttime"));
    }
}
