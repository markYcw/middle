package keda.common.httpclient;

import org.json.JSONObject;

/**
 * 系统事件监听器
 * @author root
 *
 */
public interface SystemEventListener {
	/**
	 * 当系统事件到达时执行
	 * @param event 系统事件
	 * @param error 异常提示
	 */
	public void systemEventArrived(JSONObject event, String error);
}
