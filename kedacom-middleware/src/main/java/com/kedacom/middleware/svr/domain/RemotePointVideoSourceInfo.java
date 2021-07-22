package com.kedacom.middleware.svr.domain;

/**
 * @ClassName RemotePointVideoSourceInfo
 * @Description TODO
 * @Author zlf
 * @Date 2021/7/8 9:21
 */
public class RemotePointVideoSourceInfo {

    private int outvideochnid;//作为远程点的输出画面通道

    private int h323secvideochnid; //启用双流时第二路码流通道

    private int remmergestate;//是否将远程点作为合成画面 0:不作为 1作为

    public int getOutvideochnid() {
        return outvideochnid;
    }

    public void setOutvideochnid(int outvideochnid) {
        this.outvideochnid = outvideochnid;
    }

    public int getH323secvideochnid() {
        return h323secvideochnid;
    }

    public void setH323secvideochnid(int h323secvideochnid) {
        this.h323secvideochnid = h323secvideochnid;
    }

    public int getRemmergestate() {
        return remmergestate;
    }

    public void setRemmergestate(int remmergestate) {
        this.remmergestate = remmergestate;
    }
}
