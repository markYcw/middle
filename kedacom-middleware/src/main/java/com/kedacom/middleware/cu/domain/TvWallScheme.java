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



    /*public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTvwallid() {
        return this.tvwallid;
    }

    public void setTvwallid(String tvwallid) {
        this.tvwallid = tvwallid;
    }
    */

}
