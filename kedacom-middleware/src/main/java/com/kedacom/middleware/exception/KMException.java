package com.kedacom.middleware.exception;

/**
 * 
 * 中间件模块根异常
 * @author TaoPeng
 * 
 */
public class KMException extends Exception {
	private static final long serialVersionUID = 1L;

	public KMException() {
		super();
	}

	public KMException(String message) {
		super(message);
	}

	public KMException(String message, Throwable cause) {
		super(message, cause);
	}

	public KMException(Throwable cause) {
		super(cause);
	}

}
