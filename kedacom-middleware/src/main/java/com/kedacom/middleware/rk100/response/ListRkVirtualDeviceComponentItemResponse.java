package com.kedacom.middleware.rk100.response;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName ListRkVirtualDeviceComponentItemResponse
 * @Description TODO
 * @Author zlf
 * @Date 2021/6/10 15:14
 */
@Data
@Builder
public class ListRkVirtualDeviceComponentItemResponse {

    /*
    Open表示开；Close表示关
     */
    private String label;

    /*
    表示响应命令，影响开关请求data取值
     */
    private String value;

    /*
    影响状态取值
     */
    private String resultState;

}
