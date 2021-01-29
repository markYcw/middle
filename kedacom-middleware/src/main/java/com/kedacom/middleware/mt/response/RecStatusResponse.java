package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.domain.Streaminfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecStatusResponse extends MTResponse {

	private int totalspace;

	private int freespace;

	private int recstatus;

	private String recfilename;

	private int streamnum;

	private List<Streaminfo> streaminfos;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		totalspace = jsonData.optInt("totalspace");
		freespace = jsonData.optInt("freespace");
		recstatus = jsonData.optInt("recstatus");
		streamnum = jsonData.optInt("streamnum");
		recfilename = jsonData.optString("recfilename");

		streaminfos = new ArrayList<Streaminfo>();

		JSONArray array = jsonData.optJSONArray("streaminfos");
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				int recfrmnum = obj.optInt("recfrmnum");
				int lostfrmnum = obj.optInt("lostfrmnum");
				Streaminfo stm = new Streaminfo();
				stm.setRecfrmnum(recfrmnum);
				stm.setLostfrmnum(lostfrmnum);
				streaminfos.add(stm);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public int getTotalspace() {
		return totalspace;
	}

	public void setTotalspace(int totalspace) {
		this.totalspace = totalspace;
	}

	public int getFreespace() {
		return freespace;
	}

	public void setFreespace(int freespace) {
		this.freespace = freespace;
	}

	public int getRecstatus() {
		return recstatus;
	}

	public void setRecstatus(int recstatus) {
		this.recstatus = recstatus;
	}

	public String getRecfilename() {
		return recfilename;
	}

	public void setRecfilename(String recfilename) {
		this.recfilename = recfilename;
	}

	public int getStreamnum() {
		return streamnum;
	}

	public void setStreamnum(int streamnum) {
		this.streamnum = streamnum;
	}

	public List<Streaminfo> getStreaminfos() {
		return streaminfos;
	}

	public void setStreaminfos(List<Streaminfo> streaminfos) {
		this.streaminfos = streaminfos;
	}

}
