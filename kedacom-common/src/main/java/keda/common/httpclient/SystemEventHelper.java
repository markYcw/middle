package keda.common.httpclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/***
 * 系统事件帮助执行类，是一个后台执行线程
 */
public class SystemEventHelper {
	private static Logger log = LogManager.getLogger(SystemEventHelper.class);
	/**监听组**/
	private Set<SystemEventListener> listeners = new HashSet<SystemEventListener>();
	private HelperThread t = null;
	private String nameSpace = "";
	private String getSystemEventURL;
	private NetRequest nr;
	private String projectName;
	private String stopIdentify = "haveNoAuth";
	public SystemEventHelper(String projectName){
		this(projectName, "ajax/", "getsystemevent.html");
	}
	public SystemEventHelper(String projectName, String nameSpace, String getSystemEventURL) {
		super();
		nr = new NetRequest();
		this.projectName = projectName;
		this.nameSpace = nameSpace;
		this.getSystemEventURL = getSystemEventURL; 
	}

	public void setSessionID(String sessionID) {
		nr.setSessionId(sessionID);
	}
	public void setServerName(String serverName) {
		nr.setServer(serverName);
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public void setGetSystemEventURL(String getSystemEventURL) {
		this.getSystemEventURL = getSystemEventURL;
	}
	public String getStopIdentify() {
		return stopIdentify;
	}
	public void setStopIdentify(String stopIdentify) {
		this.stopIdentify = stopIdentify;
	}


	/**
	 * 真实执行线程
	 */
	public class HelperThread extends Thread {
		public boolean stoped = false;
		
		/**
		 * 最后事件Id，默认为0，小程序初始是查询系统事件使用ID0，后台Action进行判断
		 * 请求事件ID==0时ID=系统事件池中的最大系统ID(lastEventId),为的是获取在客户
		 * 端上线后产生的最后事件，以此为起点，下次请求系统事件回事本次事件后的所有事件，
		 * 并记录最后一次事件ID。
		 */
		public long lastEventId = 0;
		
		public HelperThread() {
			this.setName("SystemEventHelperThread" + (int)(Math.random() * 1000));
			this.setDaemon(true);
		}
		
		public void run() {
			while(!stoped) {
				try {
					
					String param = MessageFormat.format("lastEventId={0,number,#}", lastEventId);
					
					/// 从后台取得系统事件
					String response = nr.post(nameSpace, getSystemEventURL, param);
					log.debug(projectName + " : systemEventHelper systemevent: " + response);
					JSONObject obj = new JSONObject(response);
					String result = obj.getString("result");
					if(result.equals("ok")) {
						if(!obj.isNull("data")) {
							JSONObject data = obj.getJSONObject("data");
							if(!data.isNull("lastEventId")) {
								lastEventId = data.getLong("lastEventId");
							}
							if(!data.isNull("eventList")) {
								JSONArray eventList = data.getJSONArray("eventList");
								if(eventList.length() > 0){
									for(int i=0;i<eventList.length();i++){
										JSONObject eventObject = eventList.getJSONObject(i);
										log.debug(projectName + " : systemEventHelper eventObject:" + eventObject);
										long eventId = eventObject.getLong("eventId");
										if(eventId > lastEventId)
											lastEventId = eventId;
										
										
										/*int eventType = eventObject.getInt("eventType");
										String srcSessionId = null;
										if(!eventObject.isNull("srcSessionId")){
											srcSessionId = eventObject.getString("srcSessionId");
										}
										JSONObject dataObject = null;
										SystemEvent event = new SystemEvent();
										event.setEventId(eventId);
										event.setEventType(eventType);
										event.setSrcSessionId(srcSessionId);
										switch(eventType) {
											case SystemEvent.TYPE_DEVICEONLINE:
											case SystemEvent.TYPE_DEVICESTATUS:
											case SystemEvent.TYPE_BURNSTATUS:
												dataObject = eventObject.getJSONObject("data");
												event.setData(PChannel.loadFromJson(dataObject));
												//event.setJsonData(dataObject);
												break;
											case SystemEvent.TYPE_TVWALL:
												dataObject =  eventObject.getJSONObject("data");
											event.setData(TvWall);
												//event.setJsonData(dataObject);
												break;
											case SystemEvent.TYPE_MAPLAYERADD:
											case SystemEvent.TYPE_MAPLAYERUPD:
											case SystemEvent.TYPE_MAPLAYERDEL:
											case SystemEvent.TYPE_MAPIMAGECHG:
												dataObject =  eventObject.getJSONObject("data");
//												event.setData(MapLayer.loadFormJsonData(dataObject));
												event.setJsonData(dataObject);
												break;
											case SystemEvent.TYPE_MAPSHAPEADD:
											case SystemEvent.TYPE_MAPSHAPEUPD:
											case SystemEvent.TYPE_MAPSHAPEDEL:
												dataObject =  eventObject.getJSONObject("data");
//												event.setData(MapShape.loadFormJsonData(dataObject));
												event.setJsonData(dataObject);
												break;
										}*/
										for(SystemEventListener listener : listeners) {
											listener.systemEventArrived(eventObject, null);
										}
									}
								}
							}
						}
					}else if(stopIdentify.equals(result)){
						stoped = true;
						break;
					} else {
						for(SystemEventListener listener : listeners) {
							listener.systemEventArrived(null, result);
						}
					}
				} catch (Exception e) {
					try{
						log.error(projectName + " : SystemEventHelper exception",e);
						for(SystemEventListener listener : listeners) {
							listener.systemEventArrived(null, e.getMessage());
						}
					}catch(Exception ex){}
				}
			}
		}
	}

	public void addListener(SystemEventListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(SystemEventListener listener) {
		listeners.remove(listener);
	}
	
	public void start() {
		//所有设备树Applet共用一个事件请求
		if(t==null){
			t = new HelperThread();
			t.start();
		}
	}
	
	public void stop() {
		if(t != null) {
			t.stoped = true;
		}
	}
	
}
