package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.cu.domain.PChannel;
import com.kedacom.middleware.cu.domain.PDevice;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 8.2 获取设备信息
 * 
 * @author dengjie
 * 
 */
public class GetDeviceNotify extends CuNotify {
	
	/**
	 * 命令值
	 */
	public static final String NAME = "getdevice";
	
	/**
	 * 监控设备
	 */
	private List<PDevice> devices = new ArrayList<PDevice>();
	
	/**
	 * 是否传完
	 */
	private boolean isend;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
		this.isend = jsonData.optBoolean("isend");
		JSONArray arrays = jsonData.optJSONArray("device");
		if(arrays != null){
			this.parseData_devices(arrays);
		}
	}
	
	private void parseData_devices(JSONArray arrays){

		for(int i = 0 ; i < arrays.length(); i ++){
			JSONObject jsonObj = arrays.optJSONObject(i);
			String groupId = jsonObj.optString("groupid");
			String manufact = jsonObj.optString("manufact");
			String name = jsonObj.optString("name");
			String puid = jsonObj.optString("puid");
			int prilevel = jsonObj.optInt("prilevel");
			int deviceType = jsonObj.optInt("type");
			String domain = jsonObj.optString("domain");
			boolean isonline = false;
			if(!jsonObj.isNull("isonline"))
				isonline = jsonObj.optBoolean("isonline");
			

			PDevice dev = new PDevice();
			dev.setGroupId(groupId);
			dev.setDomain(domain);
			dev.setDeviceType(deviceType);
			dev.setPuid(puid);
			dev.setName(name);
			dev.setManufact(manufact);
			dev.setPrilevel(prilevel);
			dev.setOnline(isonline);
			
			List<PChannel> channels = new ArrayList<PChannel>(); 
			JSONArray snArr = jsonObj.optJSONArray("srcchns");

			if(snArr!=null&&snArr.length()>0){
				for (int j = 0; j < snArr.length(); j++) {
					JSONObject obj = snArr.optJSONObject(j);
					PChannel channel = new PChannel();
					channel.setSn(obj.optInt("sn"));
					channel.setName(obj.optString("name"));
					channel.setOnline(obj.optBoolean("isonline"));
					channels.add(channel);

				}
			}
			dev.addChannels(channels);
			devices.add(dev);
		
		}
	
	}

	public boolean isIsend() {
		return isend;
	}

	public List<PDevice> getDevices() {
		return devices;
	}

	public void setDevices(List<PDevice> devices) {
		this.devices = devices;
	}

	
}
