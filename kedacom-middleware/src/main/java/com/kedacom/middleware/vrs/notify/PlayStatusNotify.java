package com.kedacom.middleware.vrs.notify;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 录像回放状态上报
 * @author ycw
 * @date 2021/5/24 14:00
 */
@Data
public class PlayStatusNotify extends VRSNotify{

    /**
     * 命令值
     */
    public static final String NAME = "playstatus";

    private int playtaskid;
    //放像状态 0：无效 1：正常播放 2：暂停 3：单帧播放 4：关键帧播放，当前不支持 5：倒放 6：正常结束 7：异常终止
    private int curplaystate;
    //放像速度
    private int curplayrate;
    //放像进度
    private int curplayprog;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
       this.playtaskid = jsonData.optInt("playtaskid");
       this.curplayrate = jsonData.optInt("curplayrate");
       this.curplaystate = jsonData.optInt("curplaystate");
       this.curplayprog = jsonData.optInt("curplayprog");
    }
}
