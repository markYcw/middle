package com.kedacom.middleware.svr;

import com.kedacom.middleware.svr.domain.CreateBurnResponseInfo;
import com.kedacom.middleware.svr.domain.Devinfo;

import java.util.List;

/**
 * SVR消息监听器
 * 
 * @author TaoPeng
 * 
 */
public interface SVRNotifyListener {

	/**
	 * SVR掉线通知
	 * 
	 * @param svrIp
	 */
	public void onSVROffine(String svrIp);

	/**
	 * svr刻录进度通知
	 * 
	 * @param svrIp
	 */
	public void onBurnStauts(String svrIp, int ssid);

	/**
	 * SVR录像下载进度
	 * 
	 * @param downloadhandle 下载句柄标示一个下载操作
	 * @param type 1:下载进度 2:下载完成 3:下载失败 4:下载超时
	 *            (录像下载可能要下载多个录像文件，接收到此事件，不停止下载录像的话，
	 *            SDK会继续下载后续录像，除非是最后一段）
	 * 
	 * @param pro 下载进度
	 */
	public void onDownloadrec(int downloadhandle, int type, int pro);
	
	/**
	* @Title: searchEncoderAnDecoder 
	* @Description: 获取编码器和解码器
	* @param @param list
	* @param @return
	* @return List<Devinfo> 返回类型
	* @author lzs 
	* @throws
	* @date 2019-8-20 下午6:12:32 
	* @version V1.0
	 */
	public void searchEncoderAnDecoder(List<Devinfo> list);

	/**
	 * 新建刻录通知
	 * @param info
	 * @param ip
	 */
	public void creatBurn(CreateBurnResponseInfo info, String ip);

}
