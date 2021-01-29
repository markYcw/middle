package com.kedacom.middleware.client;

import org.json.JSONException;

/**
 * 描述一个“请求（request）”。向中件间发送请求后，中件间需要回复数据。
 * @see IResponse
 * @see INotify
 * @author TaoPeng
 *
 */
public interface IRequest {

	
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
	
	/**
	 * 获取此request对应的响应类(response)
	 * @return
	 */
	public IResponse getResponse();
}
