package com.kedacom.middleware.rk100.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GetAllLiveStateResponse
 * @Description 获取所有开关状态返回参数类
 * @Author zlf
 * @Date 2021/6/10 16:25
 */
@Data
public class GetAllLiveStateResponse extends RkResponse {

    /*
    状态集合
     */
    private List<GetAllLiveStateDetailResponse> states = new ArrayList<>();

    /**
     * 解析数据。
     *
     * @param jsonData 符合JSON规范的字符串。
     * @throws DataException
     */
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        JSONArray jsonArray = jsonData.optJSONArray("states");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            GetAllLiveStateDetailResponse stateDetailResponse = GetAllLiveStateDetailResponse.builder()
                    .componentId(jsonObject.optString("component_id"))
                    .resultState(jsonObject.optString("result_state"))
                    .build();
            states.add(stateDetailResponse);
        }
    }
}
