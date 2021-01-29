package com.kedacom.middleware.cu.domain;

import java.util.List;

public class TvWall {
	
	//电视墙ID
	private String id;
	
	//电视墙名称
	private String name;
	
	//电视墙所处域编号
	private String domain;
	
	//电视机数目
	private int tvcount;
	
	//风格
	private int style;
	
	//画面配置
	private List<Binddecs> list;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getTvcount() {
		return tvcount;
	}

	public void setTvcount(int tvcount) {
		this.tvcount = tvcount;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public List<Binddecs> getList() {
		return list;
	}

	public void setList(List<Binddecs> list) {
		this.list = list;
	}
}
