package com.kedacom.middleware.mt.domain;

public class Confproperty {

	private String id;
	private String name;
	private String number;

	public Confproperty() {
	}

	public Confproperty(String id, String name, String number) {
		this.id = id;
		this.name = name;
		this.number = number;
	}

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
