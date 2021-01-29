package com.kedacom.middleware;


import com.kedacom.middleware.cu.CuDemo;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.mcu.McuDemo;
import com.kedacom.middleware.mt.MTDemo;

/**
 * 应用示例。
 * @author TaoPeng
 *
 */
public class Demo {

	public void demo() throws KMException{
		
		/*
		 * 会议平台的应用示例，请参考：McuDemo.demo();
		 */
		McuDemo.demo();
		
		/*
		 *会议终端的应用示例，请参考： MTDemo.demo();
		 */
		MTDemo.demo();
		
		/*
		 * 监控平台的应用示例，请参考 CuDemo.demo();
		 */
		CuDemo.demo();
	}
}
