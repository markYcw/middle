package com.kedacom.middleware.rk100.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ListRkResponse
 * @Description 获取RK100信息返回参数类
 * @Author zlf
 * @Date 2021/6/10 15:00
 */
@Data
public class ListRkResponse extends RkResponse {

    /*
    名称
     */
    private String name;

    /*
    设备唯一编号
     */
    private String deviceSn;

    /*
    型号
     */
    private String deviceModel;

    /*
    虚拟设备列表
     */
    private List<ListRkVirtualDeviceResponse> virtualDeviceList;

    /**
     * 解析数据。
     *
     * @param jsonData 符合JSON规范的字符串。
     * @throws DataException
     */
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        this.name = jsonData.optString("name");
        this.deviceSn = jsonData.optString("device_sn");
        this.deviceModel = jsonData.optString("device_model");
        this.virtualDeviceList = getListRkVirtualDeviceResponse(jsonData.optJSONArray("virtual_device_list"));
    }

    private List<ListRkVirtualDeviceResponse> getListRkVirtualDeviceResponse(JSONArray jsonArray) {
        List<ListRkVirtualDeviceResponse> virtualDeviceResponses = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            ListRkVirtualDeviceResponse virtualDeviceResponse = ListRkVirtualDeviceResponse.builder()
                    .id(jsonObject.optString("id"))
                    .name(jsonObject.optString("name"))
                    .componentList(getListRkVirtualDeviceComponentResponse(jsonObject.optJSONArray("component_list")))
                    .build();
            virtualDeviceResponses.add(virtualDeviceResponse);
        }
        return virtualDeviceResponses;
    }

    private List<ListRkVirtualDeviceComponentResponse> getListRkVirtualDeviceComponentResponse(JSONArray jsonArray) {
        List<ListRkVirtualDeviceComponentResponse> componentResponses = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            ListRkVirtualDeviceComponentResponse componentResponse = ListRkVirtualDeviceComponentResponse.builder()
                    .id(jsonObject.optString("id"))
                    .name(jsonObject.optString("name"))
                    .type(jsonObject.optString("type"))
                    .items(getListRkVirtualDeviceComponentItemResponse(jsonObject.optJSONArray("items")))
                    .build();
            componentResponses.add(componentResponse);
        }
        return componentResponses;
    }

    private List<ListRkVirtualDeviceComponentItemResponse> getListRkVirtualDeviceComponentItemResponse(JSONArray jsonArray) {
        List<ListRkVirtualDeviceComponentItemResponse> itemResponses = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            ListRkVirtualDeviceComponentItemResponse itemResponse = ListRkVirtualDeviceComponentItemResponse.builder()
                    .label(jsonObject.optString("label"))
                    .resultState(jsonObject.optString("result_state"))
                    .value(jsonObject.optString("value"))
                    .build();
            itemResponses.add(itemResponse);
        }
        return itemResponses;
    }

}
