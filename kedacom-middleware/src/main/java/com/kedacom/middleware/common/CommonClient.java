package com.kedacom.middleware.common;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.common.domain.KeyInfo;
import com.kedacom.middleware.common.request.ReadkeyRequest;
import com.kedacom.middleware.common.request.ReadkeysRequest;
import com.kedacom.middleware.common.response.ReadkeyResponse;
import com.kedacom.middleware.common.response.ReadkeysResponse;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;


import org.apache.log4j.Logger;

import java.util.List;

/**
 * 提供“公共部分”接口访问。
 * @author TaoPeng
 * @date 2021/5/26 18:44  alter by ycw
 */
public class CommonClient {

	private static final Logger log = Logger.getLogger(CommonClient.class);

	private KM km;
	public CommonClient(KM km){
		this.km = km;
	}

	private IClient getClient(){
		return km.getClient();
	}

	/**
	 * 读Key
	 * @param KEY_LICENSE_TYPE Key类型,对应KEY_LICENSE_TYPE
	 * @param filekeyPath 文件Key路径，如果是usbkey，此字段可设置为null
	 * @return
	 * @throws KMException
	 */
	public ReadkeyResponse readyKey(String KEY_LICENSE_TYPE, String filekeyPath) throws KMException {

		ReadkeyRequest request = new ReadkeyRequest();
		request.setType(KEY_LICENSE_TYPE);
		request.setFilekey(filekeyPath);

		ReadkeyResponse response = (ReadkeyResponse)this.getClient().sendRequest(request);
		if(response.getKeystate() != 0){
			if(response.getKeystate() == 1){
				throw new RemoteException("没有找到对应的KEY");
			}else if(response.getKeystate() == 2){
				throw new RemoteException("KEY错误");
			}else if(response.getKeystate() == 3){
				throw new RemoteException("系统错误");
			}
		}
		return response;
	}

	/**
	 * 读Keys
	 * @param filekeyPath 文件Key路径，如果是usbkey，此字段可设置为null
	 * @return
	 * @throws KMException
	 */
	public List<KeyInfo> readyKeys(String filekeyPath) throws KMException {

		ReadkeysRequest request = new ReadkeysRequest();
		request.setFilekey(filekeyPath);

		ReadkeysResponse response = (ReadkeysResponse)this.getClient().sendRequest(request);
		if(response.getKeyInfos().size()<0){
			throw new RemoteException("系统错误");
		}
		return response.getKeyInfos();
	}
	
//	/**
//	 * 申请会话
//	 * @see #destroySSID()
//	 * @return
//	 */
//	public int getSSID(DeviceType deviceType){
//		GetSSIDRequest request = new GetSSIDRequest();
//		request.setDeviceType(deviceType);
//		
//		int ssid = -1;
//		try {
//			GetSSIDResponse resp = (GetSSIDResponse)getClient().sendRequest(request);
//			ssid = resp.getSsid();
//		} catch (KMException e) {
//			log.error("getSSID failed", e);
//		}
//		return ssid;
//	}
//	/**
//	 * 释放会话
//	 * @see #getSSID()
//	 * @return
//	 */
//	public boolean destroySSID(int ssid){
//		DestroySSIDRequest request = new DestroySSIDRequest();
//		request.setSsid(ssid);
//		
//		try {
//			getClient().sendRequest(request);
//			return true;
//		} catch (KMException e) {
//			log.warn("destroySSID failed", e);
//			return false;
//		}
//	}
//	
	
}
