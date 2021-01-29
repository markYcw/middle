package com.common;

import keda.common.httpclient.NetRequest;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("===========================> 1");
			NetRequest nr = new NetRequest();
			nr.post("http://123.61.25.1/saeef", null, "{}");
			
			System.out.println("===========================> 2");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

}
