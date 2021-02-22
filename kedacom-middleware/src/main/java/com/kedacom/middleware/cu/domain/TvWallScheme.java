package com.kedacom.middleware.cu.domain;

import lombok.Data;

import java.util.List;

/**
 * 电视墙预案
 */
@Data
public class TvWallScheme {
        /**
         * 电视墙预案ID
         */
        private int id;
        /**
         * 客户ID
         */
        private String client;
        /**
         * 所属类型
         */
        private int type;
        /**
         * 预案名称
         */
        private String name;
        /**
         * 电视墙ID
         */
        private String tvwallid;
        /**
         * 预案集
         */
        private List<Schemes> schemes;


}
