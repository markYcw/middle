package com.kedacom.middleware.vrs.domain;

import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 11:07
 * @description 录像文件下载信息类
 */
@Data
public class RecFileInfo {

    //0：所有返回数据均有效
    //1：仅totalnum有效
    //2：totalnum无效，后来的数据有效
    private int resulttype;

    //当前查询数目
    private int curnum;

    //查询的总条数
    private int totalnum;

    //录像文件信息
    private List<RecFile> recfileinfo;

}
