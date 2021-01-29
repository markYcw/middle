package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.domain.PDisk;
import com.kedacom.middleware.cu.domain.PDiskInfo;
import com.kedacom.middleware.cu.domain.PPartition;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiskInfoResponse extends CuResponse{
	/**
	 * 监控平台磁阵信息
	 */
	private PDiskInfo diskinfo = new PDiskInfo();
	
	public PDiskInfo getDiskinfo() {
		return diskinfo;
	}

	public void setDiskinfo(PDiskInfo diskinfo) {
		this.diskinfo = diskinfo;
	}

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
		JSONObject obj = jsonData.optJSONObject("diskinfo");
		String coverpolicy = obj.optString("coverpolicy");
		diskinfo.setCoverpolicy(coverpolicy);
		List<PDisk> list_disk = null;
		JSONArray arrays_disk = obj.optJSONArray("disk");
		if(arrays_disk != null){
			list_disk = this.parseData_disks(arrays_disk);
		}
		diskinfo.setDisk(list_disk);
	}
	
	private List<PDisk> parseData_disks(JSONArray arrays){
		List<PDisk> list = new ArrayList();
		for(int i = 0 ; i < arrays.length(); i ++){
			JSONObject jsonObj = arrays.optJSONObject(i);
			int diskid = jsonObj.optInt("diskid");
			String diskname = jsonObj.optString("diskname");
			long disksize = jsonObj.optLong("disksize");
			int diskState = jsonObj.optInt("diskState");
			int partitionNum = jsonObj.optInt("partitionNum");
			
			List<PPartition> list_partition = null;
			JSONArray arrays_partition = jsonObj.optJSONArray("partition");
			if(arrays_partition != null) {
			   list_partition = this.parseData_partitions(arrays_partition);
			}
			PDisk disk = new PDisk();
			disk.setDiskid(diskid);
			disk.setDiskname(diskname);
			disk.setDisksize(disksize);
			disk.setDiskState(diskState);
			disk.setPartitionNum(partitionNum);
			disk.setPartition(list_partition);
			list.add(disk);
		}
		return list;
	}

	
	private List<PPartition> parseData_partitions(JSONArray arrays){
		List<PPartition> parlist = new ArrayList();
		for(int i = 0 ; i < arrays.length(); i ++){
			JSONObject jsonObj = arrays.optJSONObject(i);
			int partitionId = jsonObj.optInt("partitionId");
			String partName = jsonObj.optString("partName");
			long totalSize = jsonObj.optLong("totalSize");
			long freeSize = jsonObj.optLong("freeSize");
			int partState = jsonObj.optInt("partState");
			PPartition par = new PPartition();
			par.setPartitionId(partitionId);
			par.setPartName(partName);
			par.setTotalSize(totalSize);
			par.setFreeSize(freeSize);
			par.setPartState(partState);
			parlist.add(par);
		}
		return parlist;
	}
}
