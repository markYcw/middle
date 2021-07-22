package com.kedacom.middleware.rk100.response;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName GetAllLiveStateDetailResponse
 * @Description TODO
 * @Author zlf
 * @Date 2021/6/10 16:27
 */
@Data
@Builder
public class GetAllLiveStateDetailResponse {

    /*

     */
    private String componentId;

    /*

     */
    private String resultState;

}
