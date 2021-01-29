package keda.common.exception;

/**
 * 未检测mount文件异常
 * @author wjs
 *
 */
public class MountFileNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public MountFileNotFoundException() {	}
	
	public MountFileNotFoundException(String msg) {
		super(msg);
	}
	
	public MountFileNotFoundException(Exception e){
		super(e);
	}
}
