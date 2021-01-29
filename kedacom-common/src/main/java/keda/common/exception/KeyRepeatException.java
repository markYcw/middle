package keda.common.exception;

/**
 * 关键字不唯一异常
 * @author wjs
 *
 */
public class KeyRepeatException extends KedacommonException {
	private static final long serialVersionUID = 1L;

	public KeyRepeatException() {	}
	
	public KeyRepeatException(String msg) {
		super(msg);
	}
	
	public KeyRepeatException(Exception e){
		super(e);
	}
}
