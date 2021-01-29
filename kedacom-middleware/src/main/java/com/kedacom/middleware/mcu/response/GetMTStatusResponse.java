package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.MTStatus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取终端状态
 * 
 * @author YueZhipeng
 * 
 */
public class GetMTStatusResponse extends McuResponse {

	private MTStatus mtstatus;

	public MTStatus getMtstatus() {
		return mtstatus;
	}

	public void setMtstatus(MTStatus mtstatus) {
		this.mtstatus = mtstatus;
	}

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

		JSONObject obj;
		try {
			obj = jsonData.getJSONObject("mtstatus");
			this.mtstatus = parseMTStatus(obj);
		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}

	}

	public static MTStatus parseMTStatus(JSONObject obj) {
		int status = obj.optInt("status");
		boolean isquiet = obj.optBoolean("isquiet");
		boolean isdumb = obj.optBoolean("isdumb");
		int curvideo = obj.optInt("curvideo");
		boolean issndvideo2 = obj.optBoolean("issndvideo2");
		int inputvolume = obj.optInt("inputvolume");
		int outputvolume = obj.optInt("outputvolume");
		boolean ishasmatrix = obj.optBoolean("ishasmatrix");
		boolean isenablefecc = obj.optBoolean("isenablefecc");
		boolean ismixing = obj.optBoolean("ismixing");
		boolean isinvmp = obj.optBoolean("isinvmp");
		boolean isintvwall = obj.optBoolean("isintvwall");
		boolean isrec = obj.optBoolean("isrec");
		boolean issndaudio = obj.optBoolean("issndaudio");
		boolean issndvideo = obj.optBoolean("issndvideo");
		String lookmainmt = obj.optString("lookmainmt");
		String looksecmt = obj.optString("looksecmt");
		String lookaudmt = obj.optString("lookaudmt");
		String mtinfo = obj.optString("mtinfo");
		String confe164 = obj.optString("confe164");
		int ssno = obj.optInt("ssno");

		MTStatus mtstatus = new MTStatus();
		mtstatus.setStatus(status);
		mtstatus.setIsquiet(isquiet);
		mtstatus.setIsdumb(isdumb);
		mtstatus.setCurvideo(curvideo);
		mtstatus.setIssndvideo2(issndvideo2);
		mtstatus.setInputvolume(inputvolume);
		mtstatus.setOutputvolume(outputvolume);
		mtstatus.setIshasmatrix(ishasmatrix);
		mtstatus.setIsenablefecc(isenablefecc);
		mtstatus.setIsmixing(ismixing);
		mtstatus.setIsinvmp(isinvmp);
		mtstatus.setIsintvwall(isintvwall);
		mtstatus.setIsrec(isrec);
		mtstatus.setIssndaudio(issndaudio);
		mtstatus.setIssndvideo(issndvideo);
		mtstatus.setLookmainmt(lookmainmt);
		mtstatus.setLooksecmt(looksecmt);
		mtstatus.setLookaudmt(lookaudmt);
		mtstatus.setMtinfo(mtinfo);
		mtstatus.setConfe164(confe164);
		mtstatus.setSsno(ssno);

		return mtstatus;
	}
}
