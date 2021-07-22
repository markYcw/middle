package com.kedacom.middleware.rk100.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ListRkVirtualDeviceResponse
 * @Description RK100信息的虚拟设备列表
 * @Author zlf
 * @Date 2021/6/10 15:12
 */
@Data
@Builder
public class ListRkVirtualDeviceResponse {

    /*
    名称
     */
    private String name;

    /*

     */
    private String id;

    /*

     */
    private List<ListRkVirtualDeviceComponentResponse> componentList;


}
