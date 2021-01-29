package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 10.2 录像日历
 * 
 * @author dengjie
 * 
 */
public class StartPlayPlatrecNotify extends CuNotify {

	/**
	 * 命令值
	 */
	public static final String NAME = "startplayplatrec";
	
	/**
	 * 命令值
	 */
	private String ntyName;
	/**
	 * 会话ID,和请求时相对应。
	 */
	private int ntySsid;
	/**
	 * 错误码 0:成功，大于0失败。
	 */
	private int ntyErrorcode;
	/**
	 * 应答时返回的puid
	 */
	private String nruchnPuid;
	/**
	 * 应答时返回的chnid
	 */
	private int nruchnChnid;
	/**
	 * 应答时返回的rpid
	 */
	private int nruchnRpid;
	/**
	 * 当前进度
	 */
	private String recprogCurprog;
	/**
	 * 单位秒（可能不准）
	 */
	private int recprogTotal;
	/**
	 * 开始时间(可能不准)
	 */
	private String recprogStarttime;
	/**
	 * 是否放完。如果为true, curprog值无效。
	 */
	private boolean recprogIsfinish;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		JSONObject req = jsonData.optJSONObject("nty");
		if (req != null) {
			this.ntyName = req.optString("name");
			this.ntySsid = req.optInt("ssid");
			this.ntyErrorcode = req.optInt("errorcode");
		}
		JSONObject nruchn = jsonData.optJSONObject("nruchn");
		if (nruchn != null) {
			this.nruchnChnid = nruchn.optInt("chnid");
			this.nruchnPuid = nruchn.optString("puid");
			this.nruchnRpid = nruchn.optInt("rpid");
		}
		JSONObject recprog = jsonData.optJSONObject("recprog");
		if (recprog != null) {
			this.recprogCurprog = recprog.optString("curprog");
			this.recprogIsfinish = recprog.optBoolean("isfinish");
			this.recprogStarttime = recprog.optString("starttime");
			this.recprogTotal = recprog.optInt("total");
		}
	}

	public String getNtyName() {
		return ntyName;
	}

	public void setNtyName(String ntyName) {
		this.ntyName = ntyName;
	}

	public int getNtySsid() {
		return ntySsid;
	}

	public void setNtySsid(int ntySsid) {
		this.ntySsid = ntySsid;
	}

	public int getNtyErrorcode() {
		return ntyErrorcode;
	}

	public void setNtyErrorcode(int ntyErrorcode) {
		this.ntyErrorcode = ntyErrorcode;
	}

	public String getNruchnPuid() {
		return nruchnPuid;
	}

	public void setNruchnPuid(String nruchnPuid) {
		this.nruchnPuid = nruchnPuid;
	}

	public int getNruchnChnid() {
		return nruchnChnid;
	}

	public void setNruchnChnid(int nruchnChnid) {
		this.nruchnChnid = nruchnChnid;
	}

	public int getNruchnRpid() {
		return nruchnRpid;
	}

	public void setNruchnRpid(int nruchnRpid) {
		this.nruchnRpid = nruchnRpid;
	}

	public String getRecprogCurprog() {
		return recprogCurprog;
	}

	public void setRecprogCurprog(String recprogCurprog) {
		this.recprogCurprog = recprogCurprog;
	}

	public int getRecprogTotal() {
		return recprogTotal;
	}

	public void setRecprogTotal(int recprogTotal) {
		this.recprogTotal = recprogTotal;
	}

	public String getRecprogStarttime() {
		return recprogStarttime;
	}

	public void setRecprogStarttime(String recprogStarttime) {
		this.recprogStarttime = recprogStarttime;
	}

	public boolean isRecprogIsfinish() {
		return recprogIsfinish;
	}

	public void setRecprogIsfinish(boolean recprogIsfinish) {
		this.recprogIsfinish = recprogIsfinish;
	}

}
