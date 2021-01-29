package com.kedacom.middleware.mt.domain;

public class Doublepayload {

	private int realpayload;

	private int activepayload;

	public Doublepayload() {
	}

	public Doublepayload(int realpayload, int activepayload) {
		this.realpayload = realpayload;
		this.activepayload = activepayload;
	}

	public int getRealpayload() {
		return realpayload;
	}

	public void setRealpayload(int realpayload) {
		this.realpayload = realpayload;
	}

	public int getActivepayload() {
		return activepayload;
	}

	public void setActivepayload(int activepayload) {
		this.activepayload = activepayload;
	}

}
