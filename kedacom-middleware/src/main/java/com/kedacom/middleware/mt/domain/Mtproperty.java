package com.kedacom.middleware.mt.domain;

public class Mtproperty {

	private String e164;

	private String name;

	public Mtproperty() {
	}

	public Mtproperty(String e164, String name) {
		this.e164 = e164;
		this.name = name;
	}

	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
