package com.kedacom.middleware.svr.notify;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.Devinfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：SearchEncoderAndDecoderNotify
 * 类描述：搜索编码器和解码器回调
 * 创建人：lzs
 * 创建时间：2019-8-20 上午9:13:59
 * @version
 *
 */
public class SearchEncoderAndDecoderNotify extends SVRNotify {
	
	public static final String NAME = "devinfonty";

	public static List<Devinfo>  list = new ArrayList<Devinfo>();//存放搜索到的解码器
	
	public static final int DECODER = 2;//解码器
	
	private int errorcode;
	
	private int devnum;
	
	private int totaldevnum;
	
	public volatile static int blast;
	
	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public int getDevnum() {
		return devnum;
	}

	public void setDevnum(int devnum) {
		this.devnum = devnum;
	}

	public int getTotaldevnum() {
		return totaldevnum;
	}

	public void setTotaldevnum(int totaldevnum) {
		this.totaldevnum = totaldevnum;
	}

	public int getBlast() {
		return blast;
	}

	public void setBlast(int blast) {
		this.blast = blast;
	}

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseNty(jsonData);
		blast = jsonData.optInt("blast");

 		JSONArray array = jsonData.optJSONArray("devinfo");
		if(array != null){
			for(int i = 0 ; i < array.length() ; i++){
				JSONObject obj;
				try {
					obj = array.getJSONObject(i);
					if(DECODER == obj.optInt("devtype")){
						Devinfo devinfo = new Devinfo();
						devinfo.setDvetype(obj.optInt("devtype"));
						devinfo.setIpcprotetype(obj.optInt("ipcprotetype"));
						devinfo.setManuid(obj.optInt("manuid"));
						devinfo.setDevipaddr(obj.optString("devipaddr"));
						devinfo.setStreamtransmode(obj.optInt("streamtransmode"));
						devinfo.setDecaddtype(obj.optInt("decaddtype"));
						devinfo.setSzguid(obj.optString("szguid"));
						devinfo.setSzname(obj.optString("szname"));
						devinfo.setSztypename(obj.optString("sztypename"));
						devinfo.setSzdevuuid(obj.optString("szdevuuid"));
						devinfo.setSzdevxaddr(obj.optString("szdevxaddr"));
						devinfo.setSzdevusername(obj.optString("szdevusername"));
						devinfo.setSzdevuserpwd(obj.optString("szdevuserpwd"));
						devinfo.setSzdevrtspurl(obj.optString("szdevrtspurl"));
						list.add(devinfo);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}
		System.out.println(list.size());
	}

	public List<Devinfo> getList() {
		return list;
	}

	public void setList(List<Devinfo> list) {
		this.list = list;
	}

}
