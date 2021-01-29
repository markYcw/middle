package com.kedacom.middleware.client;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.cu.notify.CuNotifyFactory;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.gk.notify.GKNotifyFactory;
import com.kedacom.middleware.mcu.notify.McuNotifyFactory;
import com.kedacom.middleware.mt.notify.MTNotifyFactory;
import com.kedacom.middleware.svr.notify.SVRNotifyFactory;
import com.kedacom.middleware.vrs.notify.VRSNotifyFactory;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 用于解析“通知”的工厂入口。
 * @author TaoPeng
 *
 */
public class NotifyFactory {

	private static HashMap<DeviceType, HashMap<String, Class<? extends INotify>>> map = new HashMap<DeviceType, HashMap<String, Class<? extends INotify>>>();
	
	
	/**
	 * 注册“通知”...
	 * @param name
	 * @param cls
	 */
	public static void register(DeviceType deviceType, String name, Class<? extends INotify> cls){
		HashMap<String, Class<? extends INotify>> m = map.get(deviceType);
		if(m == null){
			m = new HashMap<String, Class<? extends INotify>>();
			map.put(deviceType, m);
		}
		m.put(name, cls);
	}
	
	
	private static void init(){
		CuNotifyFactory.init();
		McuNotifyFactory.init();
		MTNotifyFactory.init();
		GKNotifyFactory.init();
		VRSNotifyFactory.init();
//		MTNotifyInit.init();
		//CommonNotifyInit.init();
		SVRNotifyFactory.init();
	}
	static{
		init();
	}
	
	/**
	 * 构造“通知”的实体。
	 * @param jsonData
	 * @return
	 * @throws DataException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static INotify buildNotify(JSONObject jsonData) throws DataException, InstantiationException, IllegalAccessException{
		JSONObject nty = jsonData.optJSONObject("nty");
		if(nty == null){
			throw new DataException("缺少必要的节点nty");
		}

		String name = nty.optString("name");
//		int ssid = nty.optInt("ssid");
		int deviceType = nty.optInt("devtype");
		if(name.contains("burn")){
			deviceType = 2;//请求状态作终端临时处理
		}
		DeviceType dt = DeviceType.parse(deviceType);
		if(dt == null){
			throw new DataException("设备类型未知：" + deviceType);
		}
		HashMap<String, Class<? extends INotify>> m = map.get(dt);
		if(m != null){
			Class<? extends INotify> cls = m.get(name);
			if(cls != null){
				INotify notify = cls.newInstance();
				notify.parseData(jsonData);
				return notify;
			}
		}
	
		return null;
	}
	
}
