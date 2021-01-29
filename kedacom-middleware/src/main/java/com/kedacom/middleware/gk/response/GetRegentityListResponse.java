package com.kedacom.middleware.gk.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.gk.domain.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取已注册实体列表
 * @author LinChaoYu
 *
 */
public class GetRegentityListResponse extends GKResponse {

	private List<Point> points;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
		JSONArray jsonPoints = jsonData.optJSONArray("point");
		if(jsonPoints != null){
			points = new ArrayList<Point>();
			
			for(int i = 0 ; i < jsonPoints.length(); i ++){
				try{
					JSONObject obj = jsonPoints.optJSONObject(i);
					if(obj != null){
						int type = -1;
						if(!obj.isNull("type"))
							type = obj.optInt("type");
						
						String ip = obj.optString("ip");
						
						int port = 0;
						if(!obj.isNull("port")){
							port = obj.getInt("port");
						}
			
						List<String> e164s = new ArrayList<String>();
						JSONArray jsonE164s = obj.optJSONArray("e164s");
						for(int j=0;j<jsonE164s.length();j++){
							e164s.add(jsonE164s.optString(j));
						}
						
						Point point = new Point();
						point.setType(type);
						point.setIp(ip);
						point.setE164s(e164s);
						point.setPort(port);
					
						points.add(point);
					}
				}catch(JSONException e){
					throw new DataException(e.getMessage(), e);
				}
			}
		}
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
}
