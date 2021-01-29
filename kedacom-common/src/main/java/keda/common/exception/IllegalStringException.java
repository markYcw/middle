package keda.common.exception;
/**
 * 不合法的字符串
 * @author wjs
 *
 */
public class IllegalStringException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalStringException() {
		
	}
	
	public IllegalStringException(String msg) {
		super(msg);
	}
}
