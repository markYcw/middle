package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.cu.domain.Gps;
import com.kedacom.middleware.cu.domain.PChannel;
import com.kedacom.middleware.cu.domain.PChannelStatus;
import com.kedacom.middleware.cu.domain.PDevice;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 8.3设备状态变更通道
 * 
 * @author dengjie
 * 
 */
public class DeviceStatusNotify extends CuNotify {
	/**
	 * 命令值
	 */
	public static final String NAME = "devstatusnty";
	
	/**状态变更类型：设备上下线*/
	public static final int TYPE_DEVICE_STATUS = 0;
	/**状态变更类型：告警*/
	public static final int TYPE_ALARM = 1;
	/**状态变更类型：视频源*/
	public static final int TYPE_Channel = 2;
	/**状态变更类型：gps信息*/
	public static final int TYPE_GPS = 3;
	/**状态变更类型：录像状态*/
	public static final int TYPE_REC = 4;
	/**状态变更类型：设备入网通知*/
	public static final int TYPE_DEVICE_IN = 14;
	/**状态变更类型：设备退网通知*/
	public static final int TYPE_DEVICE_OUT = 15;
	/**状态变更类型：设备修改通知*/
	public static final int TYPE_DEVICE_UPDATE = 16;
	/**状态变更类型：增加组*/
	public static final int TYPE_ADD_GROUP = 17;
	/**状态变更类型：删除组*/
	public static final int TYPE_DEL_GROUP = 18;
	/**状态变更类型：视频源别名改变*/
	public static final int TYPE_UPDATE_CHANNEL_NAME = 19;
	
	/**
	 * 在线 0
	 * 报警 1
	 * 视频源通道 2
	 * gps 3
	 * 录像状态 4
	 * 收到透明数据 5
	 * 电视墙新增 10
	 * 电视墙删除 11
	 * 电视墙修改 12
	 * 电视墙状态 13
	 * 设备入网 14
	 * 设备退网 15
	 * 设备修改 16
	 * 增加组 17
	 * 删除组 18
	 * 视频源别名改变 19
	 * <pre>
	 * {@link #TYPE_DEVICE_STATUS} 设备上下线
	 * {@link #TYPE_ALARM} 告警
	 * {@link #TYPE_Channel} 视频源
	 * {@link #TYPE_GPS} gps信息
	 * {@link #TYPE_DEVICE_IN} 设备入网通知 
	 * {@link #TYPE_DEVICE_OUT} 设备退网通知
	 * {@link #TYPE_DEVICE_UPDATE} 设备修改通知
	 * </pre>
	 */
	private int type;

	/**
	 * 设备ID
	 */
	private String puid;
	
	/**
	 * 设备是否在线 true 在线,false不在线, null状态未变化
	 */
	private Boolean online;
	
	/**
	 * 视频源通道状态
	 */
	private List<PChannelStatus> channelStatusList = new ArrayList<PChannelStatus>();
	private Map<Integer, PChannelStatus> map = new HashMap<Integer, PChannelStatus>();
	
	/**
	 * Gps信息
	 */
	private List<Gps> gpsList = new ArrayList<Gps>();
	
	/**
	 * 设备入网
	 */
	private PDevice device;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
		this.puid = jsonData.optString("puid");
		this.type = jsonData.optInt("type");
		
		//设备状态变更
		this.online = optBoolean(jsonData, "isonline");

		//通道状态变更
		{
			if (!jsonData.isNull("vidchns")) {
				JSONArray vidchnsArr = jsonData.optJSONArray("vidchns");
				if (vidchnsArr != null && vidchnsArr.length() > 0) {
					for (int i = 0; i < vidchnsArr.length(); i++) {
						JSONObject obj = vidchnsArr.optJSONObject(i);
						PChannelStatus status = new PChannelStatus();
						status.setSn(obj.optInt("sn"));
						status.setName(obj.optString("name"));
						status.setEnable(optBoolean(obj, "isenable"));
						status.setOnline(optBoolean(obj, "isonline"));
						channelStatusList.add(status);
						map.put(status.getSn(), status);
					}
				}
			}
		}

		{
			//录像状态变更
			if(!jsonData.isNull("recs")){
				JSONArray recs = jsonData.optJSONArray("recs");
				if(recs != null && recs.length() > 0){
					for(int i=0 ; i <recs.length() ; i ++){
						JSONObject obj = recs.optJSONObject(i);
						
						int sn = obj.optInt("sn");
						int plat = obj.optInt("plat");
						int pu = obj.optInt("pu");
						//Boolean isplat = optBoolean(obj, "isplat");
						//Boolean ispu = optBoolean(obj, "ispu");
						
						PChannelStatus status = map.get(sn);
						if(status == null){
							status = new PChannelStatus();
							status.setSn(sn);
							map.put(sn, status);
						}

						//0: 位置 1：空闲 2：录像 3：尝试中 4：停止中
						if (plat == 2) {
							status.setPlatRecord(true);
						}else {
							status.setPlatRecord(false);
						}

						//0: 位置 1：空闲 2：录像 3：尝试中 4：停止中
						if (pu == 2) {
							status.setPuRecord(true);

						}else {
							status.setPuRecord(false);

						}

					}
				}
			}
		}
		
		{
			//GPS信号上报
			if(!jsonData.isNull("gpss")){
				JSONArray gpssArr = jsonData.optJSONArray("gpss");
				if(gpssArr!=null&&gpssArr.length()>0){
					for(int i=0;i<gpssArr.length();i++){
						Gps gpsObj = new Gps();
						JSONObject jsonObj = gpssArr.optJSONObject(i);
						gpsObj.setSn(jsonObj.optInt("sn"));
						gpsObj.setLatitude(jsonObj.optString("latitude"));
						gpsObj.setLongitude(jsonObj.optString("longitude"));
						gpsObj.setMarLatitude(jsonObj.optString("marLatitude"));
						gpsObj.setMarLongitude(jsonObj.optString("marLongitude"));
						gpsObj.setSpeed(jsonObj.optString("speed"));
						gpsObj.setTime(jsonObj.optString("time"));
						gpsObj.setPuid(this.puid);
						gpsList.add(gpsObj);
					}
				}
			}
		}
		
		{
			//设备入网
			if(!jsonData.isNull("device")){
				JSONObject devObj = jsonData.optJSONObject("device");
				if(devObj != null){
					String groupId = devObj.optString("groupid");
					String manufact = devObj.optString("manufact");
					String name = devObj.optString("name");
					String puid = devObj.optString("puid");
					int deviceType = devObj.optInt("type");
					String domain = devObj.optString("domain");
//					boolean isonline = false;
//					if(!devObj.isNull("isonline"))
//						isonline = devObj.optBoolean("isonline");
					

					PDevice dev = new PDevice();
					dev.setGroupId(groupId);
					dev.setDomain(domain);
					dev.setDeviceType(deviceType);
					dev.setPuid(puid);
					dev.setName(name);
					dev.setManufact(manufact);
					dev.setOnline(true);
					
					List<PChannel> channels = new ArrayList<PChannel>(); 
					JSONArray snArr = devObj.optJSONArray("srcchns");

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
					this.device = dev;
				}
			}
		}
		
	}
	
	private static Boolean optBoolean(JSONObject jsonObject, String name){
		Object value = jsonObject.opt(name);
		if(value != null){
			return jsonObject.optBoolean(name);
		}else{
			return Boolean.FALSE;
		}
	}

	public String getPuid() {
		return puid;
	}

	public int getType() {
		return type;
	}

	public Boolean getOnline() {
		return online;
	}

	public List<PChannelStatus> getChannelStatusList() {
		return channelStatusList;
	}

	public List<Gps> getGpsList() {
		return gpsList;
	}

	public PDevice getDevice() {
		return device;
	}

	public void setDevice(PDevice device) {
		this.device = device;
	}


	public Map<Integer, PChannelStatus> getPChannelStatusMap() {
		return map;
	}
}
