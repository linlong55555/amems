package com.eray.framework.exception;

/**
 * @Description 采番异常类
 * @CreateTime 2018-1-9 上午11:43:00
 * @CreateBy 刘兵
 */
public class SaibongException extends Exception {
	private static final long serialVersionUID = 1L;

	public SaibongException() {
	}

	public SaibongException(String message) {
		super(message);
	}

	public SaibongException(Throwable cause) {
		super(cause);
	}

	public SaibongException(String message, Throwable cause) {
		super(message, cause);
	}
}