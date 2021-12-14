package com.kedacom.middleware.client;

import com.kedacom.middleware.exception.ProtocolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;

/**
 * 数据接收监听器
 * @author TaoPeng
 *
 */
public class TCPClientDataReciver {
	
	private static final Logger log = LogManager.getLogger(TCPClientDataReciver.class);
	private TCPClient tcpClient;
	
	public TCPClientDataReciver(TCPClient tcpClient){
		this.tcpClient = tcpClient;
	}
	
	/**
	 * 任务状态：true运行，false 停止。
	 */
	private boolean run = true;
	private Thread thread;

	//主要任务
	private void doWork(){
		log.debug("thread start");
		while(run){
			try{
				Socket socket = tcpClient.getSocket();
				socket.setSoTimeout(0);
				if(socket.isClosed()){
					log.debug("socket is closed");
					continue;
				}

				log.debug("======> TCPPackageUtil readPack socket InputStream");
				
				String data = TCPPackageUtil.readPack(socket.getInputStream());//此操作是阻塞的。
				
//				log.debug("收到 ======> TCPPackageUtil readPack socket InputStream data ="+data);
				try{
					this.onData(data);
				}catch(JSONException e){
					throw new ProtocolException("TCPPackageUtil.readPack for json error: " + e.getMessage(), e);
				}
			}catch(ProtocolException e){
				log.warn("TCPPackageUtil.readPack:"+e.getMessage(), e);
			}catch(Exception e){
				log.error("TCPPackageUtil.readPack:"+e.getMessage(), e);
				if(run){
					//异常。未主动停止，抛出异常
					if(tcpClient.isConnected()){
						//接收数据出现问题捕获异常
						tcpClient.onInterrupt();
					}else{
						//关闭
						tcpClient.onClosed();
					}
				}else{
					//正常。主动停止，抛出异常
				}
				break;
			}
		}
		run = false;
		thread = null;
		log.debug("thread exit");
	}

	//收到数据
	private void onData(String data) throws JSONException {

//		String t = data;
//		if (t != null && t.length() > 1000) {
//			t = t.substring(0, 1000);
//		}
//		log.debug(TCPClient.concat("收到 <== ", t)); //只打印前1000个字符。
//		log.debug(TCPClient.concat("收到 <== ", data));//打印全部字符

		JSONObject json = new JSONObject(data);

		if (isResponse(json)) {
			log.debug(TCPClient.concat("收到请求返回消息 <== ", data));
			//响应
			JSONObject resp = json.getJSONObject("resp");
			int ssno = resp.optInt("ssno");
			//int ssid = resp.optInt("ssid");
			tcpClient.onResponse(ssno, json);
		} else if (isNotify(json)) {
			log.debug(TCPClient.concat("收到到通知消息 <== ", data));
			//通知
			JSONObject nty = json.getJSONObject("nty");
			int ssno = nty.optInt("ssno");
			//int ssid = nty.optInt("ssid");
			tcpClient.onNotify(ssno, json);
		} else {
			log.warn("未知的数据类型");
		}
	}
	
	/**
	 * 返回数据是否是“响应（Response）”
	 * @param jsonData
	 * @return
	 */
	public static boolean isResponse(JSONObject jsonData){
		return jsonData.opt("resp") != null;
	}
	/**
	 * 返回数据是否是“通知（Notify）”
	 * @param jsonData
	 * @return
	 */
	public static boolean isNotify(JSONObject jsonData){
		return jsonData.opt("nty") != null;
	}
	/**
	 * 开始
	 */
	public void start(){
		this.run = true;
		if(thread == null){
			thread = new Thread(){
				@Override
				public void run(){
					doWork();
				}
			};
			thread.setName("KM-Reciver");
			thread.start();
		}
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		this.run = false;
		thread = null;
	}

	public boolean isRun() {
		return run;
	}
	
}
