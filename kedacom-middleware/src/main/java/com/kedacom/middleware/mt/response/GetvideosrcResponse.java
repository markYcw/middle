package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.domain.VideoSrcs;
import com.kedacom.middleware.mt.request.GetVideoSrcRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 终端响应（Response）：获取终端视频源。
 * @author LiPengJia
 * @see GetVideoSrcRequest
 */
public class GetvideosrcResponse extends MTResponse {

	/*
	{
		“resp”:{ “name”:” getvideosrc”,
		      “ssno”:1,
		      “ssid”:5，
		      “errorcode”:0
		},
		“videosrcs”:[{
			“port”:5,
			“name”:””
			}]
		}
	*/
	/**
	 * 终端视频源
	 */
	private List<VideoSrcs> videoSrcs;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException{
		
		super.parseResp(jsonData);
		
		videoSrcs = new ArrayList<VideoSrcs>();
		try{
			JSONArray videosrcs = jsonData.getJSONArray("videosrcs");
			for(int i=0;i < videosrcs.length() ; i ++){
				JSONObject videosrc = videosrcs.getJSONObject(i);
				int port = videosrc.optInt("port");
				String name = videosrc.optString("name");
				VideoSrcs vs = new VideoSrcs();
				vs.setName(name);
				vs.setPort(port);
				videoSrcs.add(vs);
			}
		}catch(JSONException e){
			throw new DataException(e.getMessage(), e);
		}
	
	}

	public List<VideoSrcs> getVideoSrcs() {
		return videoSrcs;
	}

	public void setVideoSrcs(List<VideoSrcs> videoSrcs) {
		this.videoSrcs = videoSrcs;
	}
	

}
