package com.kedacom.middleware.epro.notify;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SignPictureNotify
 * @Description E10Pro服务器签名图片通知
 * @Author ycw
 * @Date 2021/07/01 10:16
 */
@Data
public class SignPictureNotify extends EProNotify{

    public static final String NAME = "signpicture";

    //签名图片路径
    public static List<String> signPicPath = new ArrayList<>();

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
        signPicPath.add(jsonData.optString("signpic_path"));
    }
}
