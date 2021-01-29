package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.cu.domain.PGroup;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

;

/**
 * 8.1获取设备组信息
 * 
 * @author dengjie
 * 
 */
public class GetGroupNotify extends CuNotify {

	public static final String NAME = "getdevicegroup";
	/**
	 * 分组
	 */
	private List<PGroup> group = new ArrayList<PGroup>();
	/**
	 * 是否传完
	 */
	private boolean isend;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseNty(jsonData);
		JSONArray groupArr = jsonData.optJSONArray("group");
		if (groupArr != null && groupArr.length() > 0) {
			for (int i = 0; i < groupArr.length(); i++) {

				JSONObject jsonObj = groupArr.optJSONObject(i);
				PGroup g = new PGroup();

				g.setId(jsonObj.optString("id"));
				g.setName(jsonObj.optString("name"));
				g.setParentId(jsonObj.optString("supid"));
				g.setIshasdev(jsonObj.optBoolean("ishasdev"));
				this.group.add(g);
			}
		}

		this.isend = jsonData.optBoolean("isend");
	}

	public List<PGroup> getGroup() {
		return group;
	}

	public void setGroup(List<PGroup> group) {
		this.group = group;
	}

	public boolean isIsend() {
		return isend;
	}

	public void setIsend(boolean isend) {
		this.isend = isend;
	}
}
