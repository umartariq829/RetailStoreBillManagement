package com.example.bm.exception;

public class CustomDataNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8111204610273231017L;

	public CustomDataNotFoundException() {
		super();
	}

	public CustomDataNotFoundException(String message) {
		super(message);
	}
}