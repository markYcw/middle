package com.kedacom.middleware.cu.domain;

import java.util.List;

public class PDisk {
	private int diskid;

	private String diskname;
	
	private long disksize;
	
	private int diskState;
	
	private int partitionNum;
	
	private List<PPartition> partition;

	public int getDiskid() {
		return diskid;
	}

	public void setDiskid(int diskid) {
		this.diskid = diskid;
	}

	public String getDiskname() {
		return diskname;
	}

	public void setDiskname(String diskname) {
		this.diskname = diskname;
	}

	public long getDisksize() {
		return disksize;
	}

	public void setDisksize(long disksize) {
		this.disksize = disksize;
	}

	public int getDiskState() {
		return diskState;
	}

	public void setDiskState(int diskState) {
		this.diskState = diskState;
	}

	public int getPartitionNum() {
		return partitionNum;
	}

	public void setPartitionNum(int partitionNum) {
		this.partitionNum = partitionNum;
	}

	public List<PPartition> getPartition() {
		return partition;
	}

	public void setPartition(List<PPartition> partition) {
		this.partition = partition;
	}

}
