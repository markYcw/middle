package com.kedacom.middleware.common.response;

import com.kedacom.middleware.common.CommonResponse;
import com.kedacom.middleware.common.domain.KeyInfo;
import com.kedacom.middleware.common.request.ReadkeyRequest;
import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * 读Keys
 * @see ReadkeyRequest
 * @author ycw
 *
 */
@Data
public class ReadkeysResponse extends CommonResponse {

    private List<KeyInfo> keyInfos;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        this.parseResp(jsonData);
        JSONArray array = jsonData.optJSONArray("keys");
        if(array != null && array.length() > 0){

            for(int i = 0; i<array.length();i++){
                KeyInfo keyInfo = new KeyInfo();
                JSONObject jsonObject = array.optJSONObject(i);
                keyInfo.setKeystate(jsonObject.optInt("keystate"));
                int isusbkey = jsonData.optInt("isusbkey");
                if(isusbkey == 1)
                    keyInfo.setUsbkey(true);
                else
                    keyInfo.setUsbkey(false);
                JSONObject keyval = jsonObject.optJSONObject("keyval");
                if(keyval != null){
                    Iterator<String> keys = keyval.keys();
                    while(keys.hasNext()){
                        String key = keys.next();
                        String value = keyval.optString(key);
                        keyInfo.getAttributes().put(key, value);
                    }
                }
                keyInfos.add(keyInfo);
            }
        }
    }
}
