package com.kedacom.middleware.client;

import org.json.JSONException;

/**
 * 描述一个指令（command）。当发送一个“指令”后，不需要等待中间件响应。
 * @see IRequest
 * @see INotify
 * @author TaoPeng
 *
 */
public interface ICommand {

	
	/**
	 * 设置流水号
	 * @param ssno
	 */
	public void setSsno(int ssno);
	
	/**
	 * 将对象转换成JSON字符串。
	 * @return
	 * @throws JSONException 
	 */
	public String toJson() throws JSONException;
	
}
