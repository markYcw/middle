package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.Chns;
import com.kedacom.middleware.mcu.domain.TVWalls;
import com.kedacom.middleware.mcu.request.GetTVWallRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取电视墙列表
 * @see GetTVWallRequest
 * @author YueZhipeng
 *
 */
public class GetTVWallResponse extends McuResponse {
   
	private List<TVWalls> tvwalls;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
        List<TVWalls> list = new ArrayList<TVWalls>();
        
        if(!jsonData.isNull("tvwalls")){
        	 JSONArray tvwalls = jsonData.optJSONArray("tvwalls");
        	 
        	 if(tvwalls != null){
        		 for(int i = 0 ; i < tvwalls.length(); i ++){
          			try{
          				JSONObject obj = tvwalls.getJSONObject(i);
          				String alias = obj.optString("alias");
          				String eqpid = obj.optString("eqpid");
          				
          				int apsid = 0;//会议平台5.0及以上版本无效
          				if(obj.isNull("apsid"))
          					apsid = obj.optInt("apsid");
          				
          				boolean isonline = obj.optBoolean("isonline");
          				
          				boolean occupy = false;//是否被占用
          				if(!obj.isNull("occupy"))
          					occupy = obj.optBoolean("occupy");
          				
          				int mode = 0;//电视墙模式
          				if(!obj.isNull("mode"))
          					mode = obj.optInt("mode");
          				
          				int specificMembertype = 0;
          				String specificVmpid = null;
          				if(!obj.isNull("specific")){
          					JSONObject specific = obj.getJSONObject("specific");
          					
          					specificMembertype = specific.optInt("membertype");
          					specificVmpid = specific.optString("vmpid");
          				}
          				
          			    int pollNum = 0;//轮询次数
          			    int pollMode = 0 ;//轮询方式,1-仅图像；3-音视频轮询
          			    int pollKeeptime = 0;//轮询间隔（秒）
          				if(!obj.isNull("poll")){
          					JSONObject poll = obj.getJSONObject("poll");
          					
          					pollNum = poll.optInt("poll");
          					pollMode = poll.optInt("mode");
          					pollKeeptime = poll.optInt("keeptime");
          				}
          				
          				//电视墙通道对应终端信息
          				List<Chns> list2 = new ArrayList<Chns>();
          				
          				if(!obj.isNull("chns")){
          					JSONArray chns = obj.optJSONArray("chns");
          					for(int j = 0 ; j < chns.length(); j ++){
          			            try{
          			            	JSONObject obj2 = chns.getJSONObject(j);
          			            	String mt = obj2.optString("mt");
          			                String conf = obj2.optString("conf");
          			                
          			                int status = 0;
          			                if(!obj2.isNull("status"))
          			                	status = obj2.optInt("status");
          			                
          			                int chnidx = 0;
          			                if(!obj2.isNull("chnidx"))
          			                	chnidx = obj2.optInt("chnidx");
          			                
          			                Chns chnss = new Chns();
          			                chnss.setMt(mt);
          			                chnss.setConf(conf);
          			                chnss.setStatus(status);
          			                chnss.setChnidx(chnidx);
          			                
          			                list2.add(chnss);
          			            }catch(Exception e){
          			            	throw new DataException(e.getMessage(), e);
          			            }
          			        }
          				}
          				
          				TVWalls tw = new TVWalls();
          				tw.setAlias(alias);
          				tw.setEqpid(eqpid);
          				tw.setApsid(apsid);
          				tw.setIsonline(isonline);
          				tw.setChns(list2);
          				tw.setMode(mode);
          				tw.setOccupy(occupy);
          				tw.setPollMode(pollMode);
          				tw.setPollNum(pollNum);
          				tw.setPollKeeptime(pollKeeptime);
          				tw.setSpecificMembertype(specificMembertype);
          				tw.setSpecificVmpid(specificVmpid);
          				
          				list.add(tw);
          			}catch(Exception e){
          				throw new DataException(e.getMessage(), e);
          			}
          		}
        	 }
        }
       
		this.tvwalls = list;
	}

	public List<TVWalls> getTvwalls() {
		return tvwalls;
	}

	public void setTvwalls(List<TVWalls> tvwalls) {
		this.tvwalls = tvwalls;
	}

}
