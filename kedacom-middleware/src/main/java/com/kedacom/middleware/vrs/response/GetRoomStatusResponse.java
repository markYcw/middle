package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.domain.RoomStatus;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

/**
 * 录像室状态获取响应
 * @author ycw
 * @date 2021/5/24 10:08
 */
@Data
public class GetRoomStatusResponse extends VRSResponse{
    private List<RoomStatus> roomStatuses;
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        JSONArray array = jsonData.optJSONArray("roomstatus");
        if(array != null && array.length() > 0){
            for (int i = 0; i< array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                RoomStatus roomStatus = new RoomStatus();
                int roomid = object.optInt("roomid");
                int isuse = object.optInt("isuse");
                int getstreammode = object.optInt("getstreammode");
                int getstreamstatus = object.optInt("getstreamstatus");
                int istranscoding = object.optInt("istranscoding");
                int recstatus = object.optInt("recstatus");
                String rectaskname = object.optString("rectaskname");
                int rectime = object.optInt("rectime");
                int isburn = object.optInt("isburn");
                int burnmode = object.optInt("burnmode");
                int burnstatus = object.optInt("burnstatus");
                String localmtname = object.optString("localmtname");
                String remotemtname = object.optString("remotemtname");
                int threestreamstatus = object.optInt("threestreamstatus");
                int lasterrno = object.optInt("lasterrno");
                int conferencestatus = object.optInt("conferencestatus");
                roomStatus.setRoomid(roomid);
                roomStatus.setIsuse(isuse);
                roomStatus.setGetstreammode(getstreammode);
                roomStatus.setGetstreamstatus(getstreamstatus);
                roomStatus.setIstranscoding(i);
                roomStatus.setIstranscoding(i);
                roomStatus.setIstranscoding(istranscoding);
                roomStatus.setRecstatus(recstatus);
                roomStatus.setRectaskname(rectaskname);
                roomStatus.setRectime(rectime);
                roomStatus.setIsburn(isburn);
                roomStatus.setBurnmode(burnmode);
                roomStatus.setBurnstatus(burnstatus);
                roomStatus.setLocalmtname(localmtname);
                roomStatus.setRemotemtname(remotemtname);
                roomStatus.setThreestreamstatus(threestreamstatus);
                roomStatus.setLasterrno(lasterrno);
                roomStatus.setConferencestatus(conferencestatus);
                roomStatuses.add(roomStatus);
            }
        }
    }
}
