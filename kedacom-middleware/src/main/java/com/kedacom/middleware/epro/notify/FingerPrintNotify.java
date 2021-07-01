package com.kedacom.middleware.epro.notify;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FingerPrintNotify
 * @Description E10Pro服务器指纹图片通知
 * @Author ycw
 * @Date 2021/07/01 10:16
 */
@Data
public class FingerPrintNotify extends EProNotify{

    public static final String NAME = "fingerprint";
    //指纹图片路径
    public static List<String> fingerPrintPath = new ArrayList<>();

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
        fingerPrintPath.add(jsonData.optString("fingerprint_path"));
    }
}
