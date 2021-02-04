package com.kedacom.middleware.cu.devicecache;

import com.kedacom.middleware.cu.domain.*;
import keda.common.util.ToolsUtil;

import java.text.Collator;
import java.util.*;

/**
 * 设备缓存。一个监控平台一个缓存。
 * @author TaoPeng
 *
 */
public class CuDeviceCache {
	
	/**
	 * 监控平台内置根分组的ID。
	 * 通过接口不能获取到内置根分组，只能通过识别“内置未分组”的parentId。
	 */
	private String rootGroupId;
	
	/**
	 * 监控平台上报的根分组ID
	 */
	private String pubRootGroupId;
	
	private Object deviceLock = new Object(); //设备集合同步锁
	/**
	 * 分组
	 * key:分组ID, value：分组信息
	 */
	private Hashtable<String, PGroup> groups = new Hashtable<String, PGroup>(20);

	private ArrayList<PGroup> sortGroups = new ArrayList<PGroup>(20); //经过排序的分组列表
	
	/**
	 * 设备状态
	 * key:设备puid, value：PChannelStatus对象（合成通道录像状态）
	 */
	private Hashtable<String, PChannelStatus> status_map = new Hashtable<String, PChannelStatus>();
	
	/**
	 * 设备
	 * key:设备ID, value：设备信息
	 */
	private Hashtable<String, PDevice> devices = new Hashtable<String, PDevice>(100);
	
	/**
	 * 设备
	 * key:分组ID, value：分组下的设备，根据名称排序。
	 */
	private Hashtable<String, ArrayList<PDevice>> devicesByGroup = new Hashtable<String, ArrayList<PDevice>>(20);

	/**
	 *电视墙集合
	 **/
	private List<TvWall> tvWalls;

    public List<TvWall> getTvWalls() {
        return tvWalls;
    }

    public void setTvWalls(List<TvWall> tvWalls) {
        this.tvWalls = tvWalls;
    }

    public List<TvWallScheme> getTvWallSchemes() {
        return tvWallSchemes;
    }

    public void setTvWallSchemes(List<TvWallScheme> tvWallSchemes) {
        this.tvWallSchemes = tvWallSchemes;
    }

    /**
	 *电视墙预案集合
	 **/
	private List<TvWallScheme> tvWallSchemes;

	/**
	 * 设备是否加载完成
	 */
	private boolean loadComplete = false;
	
	public CuDeviceCache(){
		
	}

	/**
	 * 清空数据
	 */
	public void clear(){
		this.groups.clear();
		this.sortGroups.clear();
		this.devices.clear();
		this.devicesByGroup.clear();
		this.status_map.clear();
	}
	
	/**
	 * 增加分组
	 * @param group
	 */
	public void addDeviceGroup(PGroup group){
		if(group.isUnnamedGroup()){
			/*
			 * “内置未分组”的上级分组ID，是“内置根分组”
			 */
			this.rootGroupId = group.getParentId();
		}
		
		if(ToolsUtil.isEmpty(group.getParentId())){
			this.pubRootGroupId = group.getId();
		}
		
		this.groups.put(group.getId(), group);
		checkSortRootGroups(group);
		
		//维护分组的树状结构
		//2018-10-16 LinChaoYu 连接平台1.0时，出现 上级分组ID 与 分组ID相同：0000000000000000000000000
		if(group.getParentId() == null || group.getParentId().equals(group.getId())) {
			addSortRootGroups(group);
		} else {
			PGroup parent = groups.get(group.getParentId());
			if(parent != null) {
				parent.addChildGroup(group);
				group.setParentId(parent.getId());
			} else {
				addSortRootGroups(group);
			}
		}
		
	}
	public void addDeviceGroups(Collection<PGroup> groups){
		for(PGroup g : groups){
			this.addDeviceGroup(g);
		}
	}
	
	private void checkSortRootGroups(PGroup group){
		synchronized(sortGroups){
			if(group == null)
				return;
			//寻找当前所有根分组的父分组是否为group分组，如果是，则加为group的子分组，并从根分组中删除。
			ListIterator<PGroup> it = sortGroups.listIterator();
			while(it.hasNext()){
				PGroup temp = it.next();
				if(temp.getParentId() != null && temp.getParentId().equals(group.getId())){
					group.addChildGroup(temp);
					temp.setParentId(group.getId());
					it.remove();
				}
			}
		}
	}
	
	private void addSortRootGroups(PGroup group){
		synchronized(sortGroups){
			if(group == null)
				return;
			//排序加入缓存
			for(int index = sortGroups.size()-1; index >= 0; index -- ){
				PGroup temp = sortGroups.get(index);
				if(compareZH_CN(temp.getName(), group.getName()) < 0){
					sortGroups.add(index+1, group);
					return;
				}
			}
			sortGroups.add(0,group);
		}
	}
	
	/**
	 * 根据分组ID获取分组信息
	 * @param groupId
	 * @return
	 */
	public PGroup getPGroupById(String groupId){
		return groups.get(groupId);
	}
	
	/**
	 * 增加设备
	 * @param device
	 */
	public void addDevice(PDevice device){
		
		synchronized (deviceLock) {
			String puid = device.getPuid();
			//根据puid判断设备是否已经存在，如果存在就忽略掉，避免重复
			//if(!this.devices.containsKey(puid)) {
				PDevice pDevice = this.devices.get(puid);
				if (pDevice != null) {
					PDevice pDbk = new PDevice();
					pDbk.setDeviceType(pDevice.getDeviceType());
					pDbk.setDomain(pDevice.getDomain());
					pDbk.setGroupId(pDevice.getGroupId());
					pDbk.setManufact(pDevice.getManufact());
					pDbk.setName(pDevice.getName());
					pDbk.setOnline(pDevice.isOnline());
					pDbk.setPrilevel(pDevice.getPrilevel());
					pDbk.setPuid(pDevice.getPuid());
					pDbk.addChannels(pDevice.getChannels());
					pDbk.addChannels(device.getChannels());
					this.devices.put(puid, pDbk);
				} else {
					this.devices.put(puid, device);
				}
				
				String groupId = device.getGroupId();
				if(ToolsUtil.isEmpty(groupId)){// 2019-09-24 设备入网通知中，设备所属分组为空
					groupId = this.pubRootGroupId;
					device.setGroupId(groupId);
				}else if(this.rootGroupId != null && groupId.equalsIgnoreCase(this.rootGroupId)){
					/*
					 * 如果设备所在分组是“内置根分组”，那把这些设备均放到“内置未分组”下。
					 */
					groupId = PGroup.unNamgedGroupId;
					device.setGroupId(groupId);
				}
				
				
				ArrayList<PDevice> list = this.devicesByGroup.get(groupId);
				if(list == null){
					list = new ArrayList<PDevice>();
					this.devicesByGroup.put(groupId, list);
				}

				int i = 0;
				for( ; i < list.size(); i ++){
					PDevice dev = list.get(i);
					if(device.getName().compareTo(dev.getName()) <= 0){
						break;
					}
				}
				list.add(i, device);
			//}
		}
	}
	
	public void addDevices(Collection<PDevice> devices){
		for(PDevice device : devices){
			this.addDevice(device);
		}
	}
	
	public PDevice getDevice(String puid){
		return this.devices.get(puid);
	}
	
	public PChannel getChannel(String puid, int sn){
		PDevice device = this.getDevice(puid);
		if(device != null){
			return device.getChannel(sn);
		}else{
			return null;
		}
	}
	
	/**
	 * 删除设备
	 * @param puid
	 */
	public void removeDevice(String puid){

		synchronized (deviceLock) {
			PDevice device = this.getDevice(puid);
			if(device != null){
				String groupId = device.getGroupId();
				ArrayList<PDevice> list = this.devicesByGroup.get(groupId);
				if(list != null){
					list.remove(device);
				}
			}
			
			this.devices.remove(puid); //这一行要放在后面，否则调用this.getDevice(puid); 返回的会是null
		}

	}
	
	public void updateDeviceStatus(String puid, Boolean online){
		for(ArrayList<PDevice> devices : devicesByGroup.values()){
			for (PDevice pDevice : devices) {
				if (puid.equals(pDevice.getPuid())) {
					if(online != null){
						pDevice.setOnline(online);
						if(!online){
							if(!online){
								//设备下线，所有通道全部下线
								List<PChannel> channels = pDevice.getChannels();
								for(PChannel chl : channels){
									chl.setOnline(false);
								}
							}
						}
					}
				}
			}
		}
//		PDevice device = this.devices.get(puid);
//		if(device != null){
//			if(online != null){
//				//设备上下线状态变更。deviceStatus.getOnline()==null表示没有状态变更
//				device.setOnline(online);
//				
//				if(!online){
//					//设备下线，所有通道全部下线
//					List<PChannel> channels = device.getChannels();
//					for(PChannel chl : channels){
//						chl.setOnline(false);
//					}
//				}
//			}
//		}
	}
	
	/**
	 * 更新通道（视频源）状态
	 * @param puid
	 * @param channelStatus
	 */
	public void updateChannelStatus(String puid, List<PChannelStatus> channelStatus){
		if(channelStatus == null){
			return;
		}
		PDevice device = this.devices.get(puid);
		if(device == null){
			return;
		}
		for(PChannelStatus status : channelStatus){
			int sn = status.getSn();
			PChannel chl = device.getChannel(sn);
			if(chl != null){
				Boolean enable = status.getEnable();
				if(enable != null){
					//可用性变更。enable==null表示没有状态变更
					chl.setEnable(enable.booleanValue());
				}
				
				Boolean online = status.getOnline();
				if(online != null){
					//上下线状态变更。online==null表示没有状态变更
					chl.setOnline(online.booleanValue());
				}
				
				Boolean isPlatRec = status.getPlatRecord();
				if(isPlatRec != null){
					chl.setPlatRecord(isPlatRec);
				}
				
				Boolean isPuRec = status.getPuRecord();
				if(isPuRec != null){
					chl.setPuRecord(isPuRec);
				}
				
				String name = status.getName();
				if(name != null && name.length() > 0){
					chl.setName(name);
				}
			}else{
				Boolean enable = status.getEnable();
				//2019-03-12 对接平台1.0时出现设备不在线，获取设备时，上报的通道列表为空，后续设备通道状态又上报上来 LinChaoYu ADD
				PChannel channel = new PChannel();
				
				if(status.getName() != null && status.getName().length() > 0)
					channel.setName(status.getName());
				else
					channel.setName(device.getName());
				
				channel.setPuid(puid);
				channel.setSn(status.getSn());
				if(enable != null){
					//可用性变更。enable==null表示没有状态变更
					channel.setEnable(enable.booleanValue());
				}
				
				Boolean online = status.getOnline();
				if(online != null){
					//上下线状态变更。online==null表示没有状态变更
					channel.setOnline(online.booleanValue());
				}
				
				Boolean isPlatRec = status.getPlatRecord();
				if(isPlatRec != null){
					channel.setPlatRecord(isPlatRec);
				}
				
				Boolean isPuRec = status.getPuRecord();
				if(isPuRec != null){
					channel.setPuRecord(isPuRec);
				}
				
				// device.addChannel(channel);
			}
		}
	}

	public boolean isLoadComplete() {
		return loadComplete;
	}

	public void setLoadComplete(boolean loadComplete) {
		this.loadComplete = loadComplete;
	}
	
	/**
	 * 返回所有分组
	 * @return
	 */
	public List<PGroup> getGroups(){
		List<PGroup> list = new ArrayList<PGroup>(20);
		list.addAll(this.sortGroups);
		return list;
	}
	
	/**
	 * 返回所有分组
	 * @return
	 */
	public List<PGroup> getAllGroups(){
		List<PGroup> list = new ArrayList<PGroup>();
		for(PGroup group : this.groups.values()){
			list.add(group);
		}
		
		return list;
	}
	
	/**
	 * 返回所有设备
	 * @return
	 */
	public List<PDevice> getDevices(){
		List<PDevice> list = new ArrayList<PDevice>(20);
		for(ArrayList<PDevice> devices : devicesByGroup.values()){
			list.addAll(devices);
		}
		return list;
	}
	
	/**
	 * 返回所有设备(包含未分组设备)
	 * @return
	 */
	public List<PDevice> getAllDevices(){
		List<PDevice> list = new ArrayList<PDevice>(20);
		for(PDevice device : devices.values()){
			list.add(device);
		}
		return list;
	}
	
	/**
	 * 获取指定分组的设备
	 * @param groupId
	 * @return
	 */
	public List<PDevice> getDeivcesByGroupId(String groupId){
		ArrayList<PDevice> devices = this.devicesByGroup.get(groupId);
		ArrayList<PDevice> list = new ArrayList<PDevice>();
		if(devices != null){
			list.addAll(devices);
		}
		return list;
	}

	/**
	 * 获取监控平台“内置根分组”的ID
	 * @return
	 */
	public String getRootGroupId() {
		return rootGroupId;
	}
	
	/**
	 * 中文字符串大小比较
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int compareZH_CN(String s1, String s2){
		if(s1 == null || s2 == null) return 0;
		return Collator.getInstance(java.util.Locale.CHINA).compare(s1, s2);  
	}

	public Hashtable<String, PChannelStatus> getStatus_map() {
		return status_map;
	}

	public void setStatus_map(Hashtable<String, PChannelStatus> status_map) {
		this.status_map = status_map;
	}
	
}
