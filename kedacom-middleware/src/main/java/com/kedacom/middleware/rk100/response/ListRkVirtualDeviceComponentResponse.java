package com.kedacom.middleware.rk100.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ListRkVirtualDeviceComponentResponse
 * @Description TODO
 * @Author zlf
 * @Date 2021/6/10 15:13
 */
@Data
@Builder
public class ListRkVirtualDeviceComponentResponse {

    /*

     */
    private String id;

    /*
    名称
     */
    private String name;

    /*

     */
    private String type;

    /*

     */
    private List<ListRkVirtualDeviceComponentItemResponse> items;

}
