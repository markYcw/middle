package com.kedacom.middleware.epro.notify;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * @ClassName SignPdfNotify
 * @Description E10Pro服务器录像文件通知
 * @Author ycw
 * @Date 2021/07/01 10:16
 */
@Data
public class RecordNotify extends EProNotify{

    public static final String NAME = "record";

    //录像文件保存路径
    private String recordPath;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
        this.recordPath = jsonData.optString("record_path");
    }
}
