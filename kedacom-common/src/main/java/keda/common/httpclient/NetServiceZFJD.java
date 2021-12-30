package keda.common.httpclient;


import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.text.MessageFormat;

public class NetServiceZFJD {
	public static final String DEVICE_TYPE_ENCODER = "encoder";
	public static final String DEVICE_TYPE_DECODER = "decoder";
	public static Logger log = Logger.getLogger(NetServiceZFJD.class);
	private static NetServiceZFJD instance;
	
	private NetServiceZFJD() {
		
	}
	
	public static NetServiceZFJD getInstance() {
		if(instance == null) 
			instance = new NetServiceZFJD();
		
		return instance;
	}
	
	/**
	 * 向网上督察系统报笔录相关信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public boolean sendRecordInfo(String json) throws Exception {
		log.debug("json==>>"+json);
		String param = MessageFormat.format("action={0}&sxdata={1}","sxnew",json);
		log.debug("send ZFJD param==>"+param);
		String result = NetRequestZFJD.post("/", "sxdata.do", param);
//		String result = NetRequestZFJD.post("/", "testZFJD.html", param);
		log.debug("receive result==>"+result);
		JSONObject obj = new JSONObject(result);
		if(!obj.isNull("state")){
			int state = obj.getInt("state");
			if(state == 0){
				return true;
			}else{
				if(!obj.isNull("message"))
					throw new Exception(obj.getString("message"));
			}
		}
		return false;
	}
	
}
