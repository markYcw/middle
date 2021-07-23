package com.kedacom.middleware.client;

import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.NetException;
import com.kedacom.middleware.util.ConnStatus;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TCP客户端。用于连接远程服务器
 * @author TaoPeng
 *
 */
public class TCPClient implements IClient {

	private static final Logger log = Logger.getLogger(TCPClient.class);

	/**
	 * 等待“响应”的默认超时时间，单位：毫秒
	 */
	private static int defaultResponseTimeout = 10000 * 2;

	/**
	 * 服务器IP
	 */
	private String serverIP;
	/**
	 * 服务器端口
	 */
	private int serverPort = 45670;

	/**
	 * 本地端口
	 */
	private static int defaultLocalPort = 55670; //之前出过1个BUG,连接本地服务的45670端口时，socket本地端口有时会随机到45670端口。

	private Socket socket;

	/**
	 * 连接超时时间,单位：毫秒。默认：5000(5秒)
	 */
	private int timeout = 5000;

	/**
	 * 等待数据包超时时间，单位：毫秒。默认：15000(15秒)
	 */
	private int timeout3Read = 15000;

	/**
	 * 连接状态
	 */
	private ConnStatus connStatus = new ConnStatus();
	/**
	 * 连接监听器
	 */
	private TCPClientConnMonitor connMonitor;
	/**
	 * 数据接收器
	 */
	private TCPClientDataReciver dataReciver;
	/**
	 * 服务器数据和状态监听器
	 */
	private Set<TCPClientListener> listeners = new HashSet<TCPClientListener>();

	/**
	 * 响应数据的缓存
	 */
	private Hashtable<Integer, RequestResponse> resultCache = new Hashtable<Integer, RequestResponse>();

	/**
	 * 创建线程池，修改目前notify里面new thread 方法，以免在大量设备的监控平台下出现OOM
	 * @seeonNotify
	 */
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();



	/**
	 * 流水号
	 */
	private static int _ssno = 0;
	private static int getSsno() {

		_ssno = _ssno + 1 ;
		if(_ssno > Integer.MAX_VALUE){
			_ssno = 0;
		}
		return _ssno;
	}

	/**
	 * 增加监听器
	 * @see #removeListener(TCPClientListener)
	 * @param listener
	 * @return
	 */
	public boolean addListener(TCPClientListener listener){
		return this.listeners.add(listener);
	}

	/**
	 * 删除监听器
	 * @param listener
	 * @see #addListener(TCPClientListener)
	 * @return
	 */
	public boolean removeListener(TCPClientListener listener){
		return this.listeners.remove(listener);
	}

	/**
	 * 计算一个可用的本地端口
	 * @return
	 * @throws IOException
	 */
	private int computeLocalPort() throws IOException{
		/*
		 * 注意：这是一个很特殊的方法。
		 * 原因：之前遇到这样一个BUG,java先于中间件启动，java尝试去连接中间件时，
		 *      java中的客户端Socket可能会占用45670端口，导致中间件不能启动。
		 */
		int count = 20;
		int port = TCPClient.defaultLocalPort;
		do{
			ServerSocket serverSocket = new ServerSocket(0); // 读取空闲的可用端口
			port = serverSocket.getLocalPort();
			serverSocket.close();
			if(port != this.serverPort){
				//主要目的：不能占用45670端口
				break;
			}
		}while(count -- > 0); //最多尝试20次
		return port;

	}
	/**
	 * 建立连接
	 * @see #startConnect()
	 * @see #close()
	 * @see #reConnect()
	 * @throws ConnectException
	 */
	protected synchronized void connect() throws ConnectException {
		if(isConnected()){
			//已连接
			log.debug("already connected.");
			return;
		}


		//建立连接
		log.debug(concat("ready to connect: serverIP=", serverIP, "; serverPort=", serverPort, "; timeout=", timeout));
		try{
//			int localPort = TCPClient.localPort ++;
			int localPort = computeLocalPort();
			log.debug("localPort=" + localPort);
			if(timeout <= 0) {
				socket = new Socket(serverIP, serverPort, null, localPort);
			} else {
				SocketAddress socketAddress = new InetSocketAddress(serverIP, serverPort);
				SocketAddress localAddress = new InetSocketAddress(localPort);
				socket = new Socket();
				socket.bind(localAddress);
				socket.connect(socketAddress, timeout);
			}

		}catch(Exception e){
			String err = concat("连接服务器失败: serverIP=", serverIP, "; serverIP=", serverPort, "; timeout=", timeout);
			throw new ConnectException(err, e);
		}

		log.debug(concat("connect finished: localIP=", socket.getLocalAddress(), "; localPort=", socket.getLocalPort()));

		//准备接收数据包
		log.debug("start to reciver...");
		if(dataReciver == null || !dataReciver.isRun()){
			dataReciver = new TCPClientDataReciver(this);
			dataReciver.start();
			log.debug("start to reciver finished.");
		}

	}

	/**
	 * 开始连接。后台开启一个连接线程，直到连接成功。
	 * @see #connect()
	 * @see #close()
	 * @see #reConnect()
	 */
	public void startConnect(){
		if(connMonitor == null || !connMonitor.isRun()){
			connMonitor = new TCPClientConnMonitor(this);
			connMonitor.start();
		}

	}

	/**
	 * 重新连接
	 * @see #connect()
	 * @see #startConnect()
	 * @see #close()
	 */
	protected void reConnect(){
		log.debug("ready to reconnect...");
		startConnect();
	}

	/**
	 * 关闭连接
	 * @see #connect()
	 * @see #startConnect()
	 * @see #reConnect()
	 * @throws IOException
	 */
	public void close(){

		log.debug("ready to close");

		if(connMonitor != null){
			connMonitor.stop();
			connMonitor = null;
		}

		if(dataReciver != null){
			dataReciver.stop();
			dataReciver = null;
		}

		if(socket != null){

			try {
				if(socket.isConnected()){
					log.debug("closed");
					socket.close();
				}
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
			}
			socket = null;
		}

	}

	@Override
	public IResponse sendRequest(IRequest request) throws KMException {
		return this.sendRequest(request, defaultResponseTimeout);
	}

	/**
	 *
	 * @param request
	 * @param timeout 超时时间，单位毫秒
	 * @return
	 * @throws NetException
	 * @throws DataException
	 */
	@Override
	public IResponse sendRequest(IRequest request, long timeout) throws KMException {

		int ssno = getSsno();
		request.setSsno(ssno);

		String req;
		try {
			req = request.toJson();
		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}

		//设置缓存
		RequestResponse result = new RequestResponse();
		result.ssno = ssno;
		result.request = request;
		synchronized (resultCache) {
			resultCache.put(ssno, result);
		}

		//发送
		try{
			this.sendStringData(req);
		}catch(Exception e){
			log.error("发送request失败，cause:" + e, e);
			resultCache.remove(ssno);
			throw e;
		}

		//等待结果对象返回
		if(timeout <=0 ){
			timeout = defaultResponseTimeout;
		}
		boolean isTimeout = false;
		IResponse response = null;
		synchronized(resultCache) {
			long t1 = System.currentTimeMillis();
			while(result.response == null) {

				if(result.exception != null){
					//异常
					throw result.exception;
				}

				try {
					//继续等响应
					resultCache.wait(100);
				} catch (Exception e){
					log.error(buildNetLog("exception"),e);
				}

				long t2 = System.currentTimeMillis();
				if((result.response == null) && (t2 - t1 >= timeout)) {
					//超时
					isTimeout = true;
					break;
				}
			}

			if(!isTimeout){
				//成功
				response = result.response;
			}

			resultCache.remove(ssno);
		}


		if(isTimeout) {
			//没有等到结果，表示结果等待超时
			log.debug(buildNetLog("wait for response timeout, ssno=", ssno));
			throw new ConnectException("服务器无响应，原因：timeout, ssno=" + ssno);
		}

		return response;
	}


	/**
	 * 发送字符串数据
	 * @param data 请求中的参数，格式为符合JSON规范的字符串
	 * @return 返回响应数据，格式为符合JSON规范的字符串
	 * @throws NetException
	 */
	private synchronized void sendStringData(String data) throws NetException {

		log.debug(concat("发送>> ", data));

		if(!isConnected()){
			log.debug("socket未连接，即将自动连接...");
			this.connect();
		}

		byte[] bytes = TCPPackageUtil.buidPack(data);

		try {
			socket.setSoTimeout(timeout3Read);
			socket.getOutputStream().write(bytes);
			log.debug("发送成功："+data);
		} catch (IOException e) {
			ConnectException ce = new ConnectException("发送数据异常", e);
			this.close();
			connStatus.markFail(ce);
			throw ce;
		}

		connStatus.markSuccess();

	}

	/**
	 * 发送指令。
	 * @throws DataException
	 * @throws NetException
	 */
	public void sendCmd(ICommand cmd) throws KMException{
		int ssno = getSsno();
		cmd.setSsno(ssno);

		String data;
		try {
			data = cmd.toJson();
		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}

		//发送
		this.sendStringData(data);
	}
	/**
	 * 收到“响应”
	 * @param jsonData
	 */
	protected void onResponse(int ssno, JSONObject jsonData){
		RequestResponse rr = null;
		synchronized (resultCache) {
			rr = resultCache.get(ssno);
		}

		if(rr == null){
			//无法对应，“响应”无对应的“请求”
			log.debug(buildNetLog(concat("command result already destroyed, can not set command result: ", jsonData)));
		}else{
			IResponse response = rr.request.getResponse();
			try {
				response.parseData(jsonData);
				rr.response = response;
			} catch (DataException e) {
				log.error("数据对象解析失败", e);
				rr.exception = e;
				response  = null;
			}

			synchronized (resultCache) {
				resultCache.notifyAll();
			}
		}
	}

	/**
	 * 收到通知
	 * @param ssno
	 * @param jsonData
	 */
	protected void onNotify(int ssno, JSONObject jsonData){


		INotify notify = null;
		try {
			notify = NotifyFactory.buildNotify(jsonData);
		} catch (Exception e) {
			log.warn("Notify解析失败", e);
			return;
		}

		if(notify == null){
			//通知无效解析
			JSONObject nty = jsonData.optJSONObject("nty");
			log.warn(buildNetLog("无效的通知(Notify):", nty));
			return;
		}

		final INotify nty = notify;
		Thread thread = new Thread(){
			@Override
			public void run() {
				for(TCPClientListener l : listeners){
					try{
						l.onNotify(nty);
					}catch(Exception e){
						log.error("onNotify faild", e);
					}
				}
			}

		};
		thread.setName(concat("KM-Notify-", ssno));
		//thread.start();
		cachedThreadPool.execute(thread);

	}

	/**
	 * 连接（已经意外）中断。
	 */
	protected void onInterrupt(){
		log.debug("onInterrupt");
		this.reConnect();
		Thread thread = new Thread(){
			@Override
			public void run() {
				for(TCPClientListener l : listeners){
					l.onInterrupt(TCPClient.this);
				}
			}

		};
		thread.setName("KM-onInterrupt");
		thread.start();
	}

	/**
	 * 连接（已经）关闭
	 */
	protected void onClosed(){
		log.debug("onClosed");
		this.reConnect();
		Thread thread = new Thread(){
			@Override
			public void run() {
				for(TCPClientListener l : listeners){
					l.onClosed(TCPClient.this);
				}
			}

		};
		thread.setName("KM-onClosed");
		thread.start();
	}

	private String buildNetLog(Object ... content) {
//		return MessageFormat.format("[local:{0} => remote {1}:{2}] {3}", getLocalPort(), serverIP, serverPort, content);
		String c = concat(content);
		String msg = concat("[local:", getLocalPort(), " => remote " , serverIP, ":", serverPort, " ", c);
		return msg;
	}

	/**
	 * 拼接一串字符串
	 * @param strings
	 * @return
	 */
	protected static String concat(Object ...strings){
		StringBuffer sb = new StringBuffer();
		for(Object s : strings){
			sb.append(s);
		}
		return sb.toString();
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getLocalPort(){
		return socket != null ? socket.getLocalPort() : 0;
	}
	public int getTimeout() {
		return timeout;
	}
	/**
	 * 设置连接超时时间,单位：毫秒。默认：5000
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getTimeout3Read() {
		return timeout3Read;
	}

	/**
	 * 设置等待数据包超时时间，单位：毫秒。默认：15000
	 * @param timeout3Read
	 */
	public void setTimeout3Read(int timeout3Read) {
		this.timeout3Read = timeout3Read;
	}
	public ConnStatus getConnStatus() {
		return connStatus;
	}

	protected Socket getSocket() {
		return socket;
	}

	public static int getDefaultResponseTimeout() {
		return defaultResponseTimeout;
	}

	/**
	 * 返回当前连接是否正常
	 * @return
	 */
	public boolean isConnected() {
		return socket != null && socket.isConnected();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String xml = "this is word";
		byte[] bytes = TCPPackageUtil.buidPack(xml);
		out(bytes);
	}
	private static void out(byte[] bytes){
		for(byte b : bytes){
			System.out.print(b);
			System.out.print(" ");
		}
		System.out.println("\n\n----------------------------");
	}
}

class RequestResponse{
	protected long time; //消息构建的时间
	protected int ssno;
	protected IRequest request;
	protected IResponse response;
	protected KMException exception; // null表示异常
	protected RequestResponse(){
		time = System.currentTimeMillis();
	}
}