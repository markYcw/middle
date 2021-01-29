package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.cu.domain.Record;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 10.1 获取录像
 * 
 * @author dengjie
 * 
 */
public class GetRecordNotify extends CuNotify {
	/**
	 * 命令值
	 */
	public static final String NAME = "getrecord";
	/**
	 * 录像
	 */
	private List<Record> records = new ArrayList<Record>();
	/**
	 * 设备通道信息
	 */
	private int channelChnid;
	/**
	 * 设备通道信息
	 */
	private String channelPuid;
	/**
	 * 是否结束
	 */
	private boolean isend;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseNty(jsonData);
		JSONArray recordArr = jsonData.optJSONArray("records");
		if (recordArr != null && recordArr.length() > 0) {
			for (int i = 0; i < recordArr.length(); i++) {
				Record record = new Record();
				JSONObject obj = recordArr.optJSONObject(i);
				record.setEndtime(obj.optString("id"));
				record.setId(obj.optString("mcu"));
				record.setMcu(obj.optString("starttime"));
				record.setStarttime(obj.optString("endtime"));
				records.add(record);
			}
		}
		JSONObject channel = jsonData.optJSONObject("channel");
		this.channelChnid = channel.optInt("chnid");
		this.channelPuid = channel.optString("puid");
		this.isend = jsonData.optBoolean("isend");
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public int getChannelChnid() {
		return channelChnid;
	}

	public void setChannelChnid(int channelChnid) {
		this.channelChnid = channelChnid;
	}

	public String getChannelPuid() {
		return channelPuid;
	}

	public void setChannelPuid(String channelPuid) {
		this.channelPuid = channelPuid;
	}

	public boolean isIsend() {
		return isend;
	}

	public void setIsend(boolean isend) {
		this.isend = isend;
	}

	public static String getName() {
		return NAME;
	}

}
