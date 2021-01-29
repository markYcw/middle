package keda.common.exception;

public class KedacommonException extends Exception {
	private static final long serialVersionUID = 1L;

	public KedacommonException() {	}
	
	public KedacommonException(String msg) {
		super(msg);
	}
	
	public KedacommonException(Exception e){
		super(e);
	}
	
	public KedacommonException(String msg, Exception e){
		super(msg, e);
	}
}
