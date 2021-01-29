package com.kedacom.middleware.cu.domain;
/*
 * 分区信息
 */
public class PPartition {
     private int partitionId;
	 
	 private String partName;
	 
	 private long totalSize;
	 
	 private long freeSize;
	 
	 private int partState;

	public int getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(int partitionId) {
		this.partitionId = partitionId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public long getFreeSize() {
		return freeSize;
	}

	public void setFreeSize(long freeSize) {
		this.freeSize = freeSize;
	}

	public int getPartState() {
		return partState;
	}

	public void setPartState(int partState) {
		this.partState = partState;
	}
	 
}
