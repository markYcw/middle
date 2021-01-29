package com.kedacom.middleware.client;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 描述一个“通知（notify）”。中间件主动通知事件。
 * @see ICommand
 * @see IRequest
 * @see IResponse
 * @author TaoPeng
 *
 */
public interface INotify {

	/**
	 * 获取“通知”所属的设备类型。
	 * @return
	 */
	public DeviceType getDeviceType();

	/**
	 * 获取会话标识
	 * @return
	 */
	public int getSsid();

	/**
	 * 解析数据。
	 * @param jsonData 符合JSON规范的字符串。
	 */
	public void parseData(JSONObject jsonData) throws DataException;
}
