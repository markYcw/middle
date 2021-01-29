package keda.common.exception.httpclient;

public class RequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RequestException(){}
	
	public RequestException(String msg){
		super(msg);
	}

}
