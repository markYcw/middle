package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.*;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 获取会议电视墙通道信息
 * @see GetTvWallChnInfoResponse
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class GetTvWallChnInfoResponse extends McuResponse {

    private HduInfo hduInfo;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        String hdu_id = jsonData.optString("hdu_id");
        hduInfo.setHdu_id(hdu_id);
        int mode = jsonData.optInt("mode");
        hduInfo.setMode(mode);
        //specific部分
        JSONObject specific = jsonData.optJSONObject("specific");
        Specific specificH = new Specific();
        if(specific != null){
            String member_type = specific.optString("member_type");
            specificH.setMember_type(member_type);
            String mt_id = specific.optString("mt_id");
            specificH.setMt_id(mt_id);
            String vmp_id = specific.optString("vmp_id");
            specificH.setVmp_id(vmp_id);
        }
        hduInfo.setSpecific(specificH);
        //poll部分
        JSONObject poll = jsonData.optJSONObject("poll");
        Poll pollH = new Poll();
        if(poll != null){
            String num = poll.optString("num");
            pollH.setNum(num);
            String modeH = poll.optString("mode");
            pollH.setMode(modeH);
            String keep_time = poll.optString("keep_time");
            pollH.setKeep_time(keep_time);
            JSONArray members = poll.optJSONArray("members");
            ArrayList<Members> membersArray= new ArrayList<Members>();
            if(members != null){
                for (int i = 0; i < members.length(); i++){
                    Members membersH = new Members();
                    JSONObject jsonObject = members.optJSONObject(i);
                    membersH.setMt_id(jsonObject.optString("mt_id"));
                    membersArray.add(membersH);
                }
            }
            pollH.setMembers(membersArray);
        }
        hduInfo.setPoll(pollH);
        //spilt部分
        JSONObject spilt = jsonData.optJSONObject("spilt");
        Spilt spiltH = new Spilt();
        if(spilt != null){
            JSONArray members = spilt.optJSONArray("members");
            ArrayList<MemberInfo> memberInfoArray = new ArrayList<MemberInfo>();
            for (int i = 0; i< members.length(); i++){
                MemberInfo memberInfo = new MemberInfo();
                JSONObject jsonObject = members.optJSONObject(i);
                memberInfo.setChn_idx(jsonObject.optInt("chn_idx"));
                memberInfo.setMt_id(jsonObject.optString("mt_id"));
                memberInfoArray.add(memberInfo);
            }
            spiltH.setMembers(memberInfoArray);
        }
        hduInfo.setSpilt(spiltH);
    }


}
