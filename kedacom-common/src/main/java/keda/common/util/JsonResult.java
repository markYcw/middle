package keda.common.util;

import java.io.Serializable;
import java.util.Hashtable;

/*
 * 之前的项目一般引用自己工程中独立的JsonResult类，但两者完全是一样的。
 * 由于此类的普通使用，所以将其提取到kedacommon工程中，以后新的项目工程均应该直接引用此类
 */

/**
 * 描述一个用于JSON规范的结果集。此类一般用于Web工程，定义一套数据交互规范。服务器将数据（对象）包装，在相应的处理层（比如Struts-json）将实例转换成JSON字符串
 * ，再返回给客户端（浏览器），此规范常用于客户端AJAX请求的数据交互。
 * 
 * @author TaoPeng
 * @referenceBy 基本所有的WEB工程都使用了此类。
 */
public class JsonResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * json数据中的属性标识-结果标记
	 */
	public static final String ATTR_RESULT = "result";
	/**
	 * json数据中的属性标识-数据集
	 */
	public static final String ATTR_DATA = "data";

	/**
	 * 结果标记　－　成功
	 */
	public static final String OK = "ok";

	/**
	 * 结果标记　－　服务器异常
	 */
	public static final String EXCEPTION = "服务器异常";
	public static final String NO_LOGIN_INFO = "当前用户未正常登录或已被注销";

	/** 返回字符串，标识是否成功 */
	private String result;
	/** 哈西表用来存放待返回的数据 */
	private Hashtable<String, Object> data = new Hashtable<String, Object>();

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 设置结果为<b>异常标识</b>。等同于 setResult(JsonResult.EXCEPTION)
	 * 
	 * @see #setResult(String)
	 */
	public void setResultException() {
		this.result = EXCEPTION;
	}

	/**
	 * 设置结果为<b>成功标识</b>。等同于 setResult(JsonResult.OK)
	 * 
	 * @see #setResult(String)
	 */
	public void setResultSuccess() {
		this.result = OK;
	}
	
	public void setResultNologin(){
		this.result = NO_LOGIN_INFO;
	}
	
	public void setResultNoRepeat(){
		this.result = "同一地区只能配置一个厂商接口";
	}
	
	public void setResultException(Throwable t){
		this.setResultException();
		this.addData("exception", t.getMessage());
	}
	
	public void setResultException(String info){
		this.setResultException();
		this.addData("exception", info);
	}
	/**
	 * @deprecated 不建议直接获取全部属性。请使用{@link #getData(String)}
	 * @return
	 */
	public Hashtable<String, Object> getData() {
		return data;
	}

	public Object getData(String key) {
		return data != null ? data.get(key) : null;
	}

	public void addData(String key, Object value) {
		if (value != null) {
			data.put(key, value);
		}
	}
	
	/**
	 * 清楚所有数据
	 */
	public void clear(){
		this.result = null;
		
		if(this.data != null){
			this.data.clear();
		}
	}
}
