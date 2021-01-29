package com.kedacom.middleware.client;

import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.NetException;


/**
 * 通信客户端，负责与远程服务程序进行数据交互。
 * @author TaoPeng
 *
 */
public interface IClient {

	/**
	 * 发送数据
	 * @throws NetException
	 * @throws KMException 
	 */
	public IResponse sendRequest(IRequest request) throws  KMException;

	/**
	 * 发送数据
	 * @param request
	 * @param timeout 超时时间。单位：毫秒。timeout小于等于0是一个无效的值，将会使用底层默认的超时时间。
	 * @return
	 * @throws KMException
	 */
	public IResponse sendRequest(IRequest request, long timeout) throws KMException;
}
