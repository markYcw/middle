package com.kedacom.middleware.mt.domain;


/**
 * 加密
 * 
 * @author root
 * 
 */
public class Mediacrypt {

	/**
	 * 加密模式
	 */
	private int encryptmode;

	/**
	 * 加密key的长度
	 */
	private int keylen;

	/**
	 * 加密key
	 */
	private String key;


	public Mediacrypt() {
	}

	public Mediacrypt(int encryptmode, int keylen, String key) {
		this.encryptmode = encryptmode;
		this.keylen = keylen;
		this.key = key;
	}

	public int getEncryptmode() {
		return encryptmode;
	}

	public void setEncryptmode(int encryptmode) {
		this.encryptmode = encryptmode;
	}

	public int getKeylen() {
		return keylen;
	}

	public void setKeylen(int keylen) {
		this.keylen = keylen;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
