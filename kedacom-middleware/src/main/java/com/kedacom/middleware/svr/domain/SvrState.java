package com.kedacom.middleware.svr.domain;

import lombok.Data;

/**
 * svr状态
 * @author ycw
 *
 */
@Data
public class SvrState {
	/**
	 * 0：不在线 非0：在线
	 */
	private int online;
	/**
	 * 0：不在线 非0：在线
	 */
	private int burntaskid;
	/**
	 * 0：没有录像1：录像
	 */
	private int recing;
	/**
	 * 0：没有在刻录中1：刻录中
	 */
	private int burning;
	/**
	 * 0：没有刻录任务1：有刻录任务
	 */
	private int burntaskdoing;

	/**
	 * 刻录通道id
	 */
	private int burnchnid;

	/**
	 * 刻录模式 0：无效 1：双盘同步刻录 2：只刻录DVD1 3：只刻录DVD2 4：循环连续刻录
	 */
	private int burnmode;

	/**
	 * svr光盘1
	 */
	private Dvd dvd1;
	/**
	 * svr光盘2
	 */
	private Dvd dvd2;
	
	
}
