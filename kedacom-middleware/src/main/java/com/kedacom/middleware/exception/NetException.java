package com.kedacom.middleware.exception;

/**
 * 
 * 网络异常
 * @author TaoPeng
 * 
 */
public class NetException extends KMException {
	private static final long serialVersionUID = 1L;

	public NetException() {
		super();
	}

	public NetException(String message) {
		super(message);
	}

	public NetException(String message, Throwable cause) {
		super(message, cause);
	}

	public NetException(Throwable cause) {
		super(cause);
	}

}
