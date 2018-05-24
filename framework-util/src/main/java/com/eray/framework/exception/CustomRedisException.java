package com.eray.framework.exception;

/**
 * @Description Jedis异常类
 * @CreateTime 2018-1-9 上午11:43:00
 * @CreateBy 刘兵
 */
public class CustomRedisException extends Exception {
	private static final long serialVersionUID = 1L;

	public CustomRedisException() {
	}

	public CustomRedisException(String message) {
		super(message);
	}

	public CustomRedisException(Throwable cause) {
		super(cause);
	}

	public CustomRedisException(String message, Throwable cause) {
		super(message, cause);
	}
}