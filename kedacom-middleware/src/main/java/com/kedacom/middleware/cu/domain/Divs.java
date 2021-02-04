package com.kedacom.middleware.cu.domain;
import lombok.Data;

import java.util.List;

/**
 * 解码集
 *
 * @author ycw
 *
 */
@Data
public class Divs {
    /**
     * 解码器通道号
     */
    private int chnid;
    /**
     *轮询集
     */
    private List<Polls> polls;
}
