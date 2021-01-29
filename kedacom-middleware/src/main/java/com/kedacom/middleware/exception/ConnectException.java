package com.kedacom.middleware.exception;

/**
 * 
 * 连接异常
 * @author TaoPeng
 * 
 */
public class ConnectException extends NetException {
	private static final long serialVersionUID = 1L;

	public ConnectException() {
		super();
	}

	public ConnectException(String message) {
		super(message);
	}

	public ConnectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectException(Throwable cause) {
		super(cause);
	}

}
