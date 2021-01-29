package com.kedacom.middleware.exception;

/**
 * 
 * 协议异常
 * @author TaoPeng
 * 
 */
public class ProtocolException extends NetException {
	private static final long serialVersionUID = 1L;

	public ProtocolException() {
		super();
	}

	public ProtocolException(String message) {
		super(message);
	}

	public ProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProtocolException(Throwable cause) {
		super(cause);
	}

}
