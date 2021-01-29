package com.kedacom.middleware.exception;

/**
 * 
 * 远程服务异常。一般用于远程服务返回错误码的情况。
 * @author TaoPeng
 * 
 */
public class RemoteException extends DataException {
	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private int errorcode;
	public RemoteException() {
		super();
	}

	public RemoteException(String message) {
		super(message);
	}

	public RemoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoteException(Throwable cause) {
		super(cause);
	}

	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
