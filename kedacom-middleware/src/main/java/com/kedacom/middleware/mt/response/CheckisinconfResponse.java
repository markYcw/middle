package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.domain.Confproperty;
import com.kedacom.middleware.mt.domain.Mtproperty;
import com.kedacom.middleware.mt.request.CheckisinconfRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 终端响应(Response):终端是否在会议中.
 * @see CheckisinconfRequest
 * @author LiPengJia
 * @see CheckisinconfRequest
 */
public class CheckisinconfResponse extends MTResponse {

	/*
	 * { "isinconf": true, "conf": { "id": "", "name": "201702071510", "number":
	 * "1486451459" }, "mts": [{ "e164": "", "name": "7920" }, { "e164": "",
	 * "name": "H700" }], "resp": { "name": "checkisinconf", "ssno": 4, "ssid":
	 * 14, "errorcode": 0, "devtype": 2 } }
	 */
	/**
	 * 是否在点对点会议中 true:是,false:否
	 */
	private boolean inconf;
	private Confproperty conf;
	private Mtproperty mtp;
	private List<Mtproperty> mts;
	private int type;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		inconf = jsonData.optBoolean("isinconf");
		type = jsonData.optInt("type");
		JSONObject obj = jsonData.optJSONObject("conf");
		if (obj != null) {
			conf = new Confproperty();
			conf.setId(obj.optString("id"));
			conf.setName(obj.optString("name"));
			conf.setNumber(obj.optString("number"));
		}
		JSONObject mtobj = jsonData.optJSONObject("mt");
		if (mtobj != null) {
			mtp = new Mtproperty();
			mtp.setE164(mtobj.optString("e164"));
			mtp.setName(mtobj.optString("name"));
		}
		JSONArray array = jsonData.optJSONArray("mts");
		if (array != null) {
			mts = new ArrayList<Mtproperty>();
			Mtproperty mt = null;
			for (int i = 0; i < array.length(); i++) {
				JSONObject oj = new JSONObject();
				mt = new Mtproperty();
				mt.setE164(oj.optString("e164"));
				mt.setName(oj.optString("name"));
				mts.add(mt);
			}
		}
	}

	public boolean isInconf() {
		return inconf;
	}

	public void setInconf(boolean inconf) {
		this.inconf = inconf;
	}

	public Confproperty getConf() {
		return conf;
	}

	public void setConf(Confproperty conf) {
		this.conf = conf;
	}

	public List<Mtproperty> getMts() {
		return mts;
	}

	public void setMts(List<Mtproperty> mts) {
		this.mts = mts;
	}

	public Mtproperty getMtp() {
		return mtp;
	}

	public void setMtp(Mtproperty mtp) {
		this.mtp = mtp;
	}

	public CheckisinconfResponse() {
	}

	public CheckisinconfResponse(boolean inconf, Confproperty conf,
                                 Mtproperty mtp, List<Mtproperty> mts) {
		super();
		this.inconf = inconf;
		this.conf = conf;
		this.mtp = mtp;
		this.mts = mts;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
