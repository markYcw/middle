package com.kedacom.middleware.cu.domain;

import java.util.List;

/*
 * 磁阵信息
 */
public class PDiskInfo {
	private String coverpolicy;
	private List<PDisk> disk;
	public String getCoverpolicy() {
		return coverpolicy;
	}
	public void setCoverpolicy(String coverpolicy) {
		this.coverpolicy = coverpolicy;
	}
	public List<PDisk> getDisk() {
		return disk;
	}
	public void setDisk(List<PDisk> disk) {
		this.disk = disk;
	}
}
