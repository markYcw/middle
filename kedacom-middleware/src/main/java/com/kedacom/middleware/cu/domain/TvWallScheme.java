package com.kedacom.middleware.cu.domain;

public class TvWallScheme {
    private int id;

    private String client;

    private int type;

    private String name;

    private String tvwallid;

    public int getId() {
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
}
