package keda.common.exception;

/**
 * 不合法的参数，主要用于传递参数不合法时抛出
 * @author wjs
 *
 */
public class IllegalParamException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalParamException() {	}
	
	public IllegalParamException(String msg) {
		super(msg);
	}
	
	public IllegalParamException(Exception e){
		super(e);
	}
	public IllegalParamException(Throwable cause){
		super(cause);
	}
	public IllegalParamException(String message, Throwable throwable){
		super(message, throwable);
	}
}
