package com.safetrust.contact.exception;

public class DuplicateUniqueKeyException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicateUniqueKeyException(String strErrorMessage, Throwable err) {
		super(strErrorMessage, err);
	}

	public DuplicateUniqueKeyException(String strErrorMessage) {
		super(strErrorMessage);
	}
}
