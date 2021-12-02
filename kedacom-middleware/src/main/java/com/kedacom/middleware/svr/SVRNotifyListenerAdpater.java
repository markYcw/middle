package com.kedacom.middleware.svr;

import com.kedacom.middleware.svr.domain.CreateBurnResponseInfo;
import com.kedacom.middleware.svr.domain.Devinfo;

import java.util.List;


//适配器
public class SVRNotifyListenerAdpater implements SVRNotifyListener {

	@Override
	public void onSVROffine(String svrIp) {
		
	}

	@Override
	public void onBurnStauts(String svrIp, int ssid) {
		
	}

	@Override
	public void onDownloadrec(int downloadhandle, int type, int pro) {
		
	}

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
	@Override
	public void searchEncoderAnDecoder(List<Devinfo> list) {
	}

	@Override
	public void creatBurn(CreateBurnResponseInfo info, String ip) {

	}

}
