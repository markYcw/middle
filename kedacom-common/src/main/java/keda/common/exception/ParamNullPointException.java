package keda.common.exception;

/**
 * 参数空指针异常
 * @author wjs
 *
 */
public class ParamNullPointException extends KedacommonException {
	private static final long serialVersionUID = 1L;

	public ParamNullPointException() {	}
	
	public ParamNullPointException(String msg) {
		super(msg);
	}
	
	public ParamNullPointException(Exception e){
		super(e);
	}
}
