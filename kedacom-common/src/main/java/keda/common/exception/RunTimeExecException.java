package keda.common.exception;

/**
 * 运行系统命令异常，为Runtime.exec方法异常
 * @author wjs
 *
 */

public class RunTimeExecException extends Exception {
	private static final long serialVersionUID = 1L;

	public RunTimeExecException() {
		
	}
	
	public RunTimeExecException(String msg) {
		super(msg);
	}
}
