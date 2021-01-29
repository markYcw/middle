package com.kedacom.middleware.client;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 描述一个“响应（Response）”。“响应”是中间件对请求的回复数据。
 * @see IRequest
 * @see INotify
 * @author TaoPeng
 *
 */
public interface IResponse {

	/**
	 * 解析数据。
	 * @param jsonData 符合JSON规范的字符串。
	 * @throws JSONException
	 */
	public void parseData(JSONObject jsonData) throws DataException;
}
