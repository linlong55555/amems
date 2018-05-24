package com.eray.component.saibong.exception;

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