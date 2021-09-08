package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.domain.RecFile;
import com.kedacom.middleware.vrs.domain.RecFileInfo;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 11:01
 * @description
 */
@Data
public class QueryRecFileResponse extends VRSResponse{
    //录像文件信息
    private RecFileInfo recFileInfo;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        int resulttype = jsonData.optInt("resulttype");
        int curnum = jsonData.optInt("curnum");
        int totalnum = jsonData.optInt("totalnum");
        JSONArray array = jsonData.optJSONArray("recfileinfo");
        ArrayList<RecFile> files = new ArrayList<>();
        if(array != null){
            for(int i =0 ; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                int taskid = obj.optInt("taskid");
                int fileid = obj.optInt("fileid");
                String path = obj.optString("path");
                String starttime = obj.optString("starttime");
                String endtime = obj.optString("endtime");
                int size = obj.optInt("size");
                RecFile recFile = new RecFile();
                recFile.setTaskid(taskid);
                recFile.setFileid(fileid);
                recFile.setPath(path);
                recFile.setStarttime(starttime);
                recFile.setEndtime(endtime);
                recFile.setSize(size);
                files.add(recFile);
            }
        }
        recFileInfo = new RecFileInfo();
        recFileInfo.setResulttype(resulttype);
        recFileInfo.setCurnum(curnum);
        recFileInfo.setTotalnum(totalnum);
        recFileInfo.setRecfileinfo(files);
    }
}
