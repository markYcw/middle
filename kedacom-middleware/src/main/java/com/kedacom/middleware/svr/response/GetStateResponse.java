package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.Dvd;
import com.kedacom.middleware.svr.domain.SvrState;
import com.kedacom.middleware.svr.request.GetStateRequest;
import org.json.JSONObject;

/**
 * SVR刻录
 *
 * @author DengJie
 * @see GetStateRequest
 */
public class GetStateResponse extends SVRResponse {

    /**
     * svr状态对象
     */
    private SvrState svrState;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        svrState = new SvrState();
        svrState.setOnline(jsonData.optInt("online"));
        svrState.setBurning(jsonData.optInt("burning"));
        svrState.setBurntaskdoing(jsonData.optInt("burntaskdoing"));
        svrState.setBurntaskid(jsonData.optInt("burntaskid"));
        svrState.setRecing(jsonData.optInt("recing"));

        JSONObject jd1 = jsonData.optJSONObject("dvd1");
        if (jd1 != null) {
            Dvd dvd1 = new Dvd();
            dvd1.setDiscstatus(jd1.optInt("discstatus"));
            dvd1.setDoorstatus(jd1.optInt("doorstatus"));
            dvd1.setRemaincapacity(jd1.optInt("remaincapacity"));
            dvd1.setRemaintime(jd1.optInt("remaintime"));
            dvd1.setTotalcapacity(jd1.optInt("totalcapacity"));
            dvd1.setWorkstate(jd1.optInt("workstate"));
            dvd1.setWorksubstate(jd1.optInt("worksubstate"));
            svrState.setDvd1(dvd1);
        }
        JSONObject jd2 = jsonData.optJSONObject("dvd2");
        if (jd2 != null) {
            Dvd dvd2 = new Dvd();
            dvd2.setDiscstatus(jd2.optInt("discstatus"));
            dvd2.setDoorstatus(jd2.optInt("doorstatus"));
            dvd2.setRemaincapacity(jd2.optInt("remaincapacity"));
            dvd2.setRemaintime(jd2.optInt("remaintime"));
            dvd2.setTotalcapacity(jd2.optInt("totalcapacity"));
            dvd2.setWorkstate(jd2.optInt("workstate"));
            dvd2.setWorksubstate(jd2.optInt("worksubstate"));
            svrState.setDvd2(dvd2);
        }
    }

    public SvrState getSvrState() {
        return svrState;
    }

    public void setSvrState(SvrState svrState) {
        this.svrState = svrState;
    }

}
