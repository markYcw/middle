package com.kedacom.middleware.exception;

/**
 * 
 * 数据异常
 * @author TaoPeng
 * 
 */
public class DataException extends KMException {
	private static final long serialVersionUID = 1L;

	public DataException() {
		super();
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataException(Throwable cause) {
		super(cause);
	}

}
