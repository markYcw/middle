package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.SetPipModeRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：设置画面显示模式.
 * @author LiPengJia
 * @see SetPipModeRequest
 */
public class SetPipModeResponse extends MTResponse {

	/*{
		“resp”:{ “name”:” setpipmode”,
		      “ssno”:1,
		      “ssid”:5，
		      “errorcode”:0
		}
		}*/

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

//		
//		“confeinfo”:{
//				  “takemode”:1,
//			      “regtogk”:1,
//			      “lockmode”:1,
//			      “recmode”:1,
//			      “confname”:””,
//			      “confe164”:””,
//			      “confid”:111,
//			      “encryptmode”,1,
//		          “capsupport”:{
//		             “dstreamtype”:1,
//		             “dcmaxbitrate”:1,
//		             “dcmediatype”:1,
//		             “dcresolution”:9,
//		             “dcframerate”:25,
//		             “dcish239”:1
//		              “mvmaxbitrate”:1,
//		             “mvmediatype”:1,
//		              “mvresolution”:9,
//		             “mvframerate”:25,
//		             “mamediatype”:1,
//		             “svmaxbitrate”:1,
//		             “svmediatype”:1,
//		              “svresolution”:9,
//		             “svframerate”:25,
//		             “samediatype”:1
//			},
//
////			
//		JSONObject json1 = jsonData.getJSONObject("confeinfo");
//		int encryptmode = json1.optInt("encryptmode");
//		JSONObject jsonCapsupport =  json1.optJSONObject("capsupport");
//		int dstreamtype = jsonCapsupport.optInt("dstreamtype");
		
	}

}
