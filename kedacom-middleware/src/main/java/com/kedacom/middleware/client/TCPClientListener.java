package com.kedacom.middleware.client;

/**
 * TCPClientListener是一个监听器的接口规范，根据此规范实现监听器，
 * 并将监听器注册到{@link TCPClient}，实现对数据以及状态的监听
 * 
 * @see TCPClientListenerAdapter
 * @author TaoPeng
 * 
 */
public interface TCPClientListener {
//
//	/**
//	 * 收到“响应”
//	 * @param response
//	 */
//	public void onResponse(IResponse response);
	
	/**
	 * 收到“通知”
	 * @param notify
	 */
	public void onNotify(INotify notify);
	
	/**
	 * 服务器关闭了(计划中的)连接。在关闭连接后触发
	 * @see #onInterrupt(String, String)
	 */
	public void onClosed(TCPClient client); //服务器关闭了连接
	
	/**
	 * 与服务器连接发生（非计划的）中断，一般为心跳包发送失败时的诊断、其它Socket异常。此事件在中断后触发。
	 * @see #onClosed()
	 */
	public void onInterrupt(TCPClient client); //网络发生中断，正常为心跳包发送失败时的诊断
}
