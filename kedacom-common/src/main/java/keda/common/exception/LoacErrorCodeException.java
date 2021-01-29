package keda.common.exception;

/**
 * 加载错误码文件出错异常
 * @author wjs
 *
 */
public class LoacErrorCodeException extends KedacommonException {
	private static final long serialVersionUID = 1L;

	public LoacErrorCodeException() {	}
	
	public LoacErrorCodeException(String msg) {
		super(msg);
	}
	
	public LoacErrorCodeException(Exception e){
		super(e);
	}
	public LoacErrorCodeException(String msg, Exception e){
		super(msg, e);
	}
}
