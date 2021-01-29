package com.kedacom.middleware.cu.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * 分组
 * @author dengjie
 * @see PGroup
 * @see PDevice
 * @see PChannel
 * 
 */
public class PGroup implements Cloneable{

	/**
	 * “未分组”的ID
	 */
	public static String unNamgedGroupId = "IsUnnamedGroup";
	
	/**
	 * 组id
	 */
	private String id;
	/**
	 * 分组名称
	 */
	private String name;
	/**
	 * 上级组id
	 */
	private String parentId;
	/**
	 * 子设备组
	 */
	private List<PGroup> sortChildGroups = new LinkedList<PGroup>();
	
	/**
	 * 改分组下面是否有设备，平台2.0有效，方便刷设备。
	 */
	private boolean ishasdev;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<PGroup> getSortChildGroups() {
		return sortChildGroups;
	}

	public void setSortChildGroups(List<PGroup> sortChildGroups) {
		this.sortChildGroups = sortChildGroups;
	}

	public boolean isIshasdev() {
		return ishasdev;
	}

	public void setIshasdev(boolean ishasdev) {
		this.ishasdev = ishasdev;
	}

	public void addChildGroup(PGroup group){
		synchronized(sortChildGroups){
			if(group == null)
				return;
			//排序加入缓存
			for(int index = sortChildGroups.size()-1; index >= 0; index -- ){
				PGroup temp = sortChildGroups.get(index);
				if(temp.getName().compareToIgnoreCase(group.getName())<0){
					sortChildGroups.add(index+1, group);
					return;
				}
			}
			sortChildGroups.add(0,group);
		}
	}
	
	/**
	 * 返回当前分组是否是监控平台的内置“未分组”。
	 */
	public boolean isUnnamedGroup(){
		return isUnamedGroupId(this.id);
	}
	
	/**
	 * 返回指定的分组ID是否是监控平台内置的“未分组ID”
	 * @param id
	 * @return
	 */
	public static boolean isUnamedGroupId(String id){
		return id != null && id.equalsIgnoreCase(unNamgedGroupId);
	}
	
}
