package keda.common.exception.runtime;

public class NotNormalEndException extends Exception {
	private static final long serialVersionUID = 1L;
	public NotNormalEndException(String msg){
		super(msg);
	}
	public NotNormalEndException(){
		this("调用命令未正常结束");
	}
}
