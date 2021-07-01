package com.kedacom.middleware.epro.notify;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SignPdfNotify
 * @Description E10Pro服务器签名文件通知
 * @Author ycw
 * @Date 2021/07/01 10:16
 */
@Data
public class SignPdfNotify extends EProNotify{

    public static final String NAME = "signpdf";

    //签名文件路径
    public static Map<String,String> signPdf = new HashMap();

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
        signPdf.put("signPdf",jsonData.optString("signpdf_path"));
    }
}
