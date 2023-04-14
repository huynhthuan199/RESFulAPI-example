package com.safetrust.contact.exception;

public class NotFoundResourceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotFoundResourceException(String strErrorMessage, Throwable err) {
		super(strErrorMessage, err);
	}

	public NotFoundResourceException(String strErrorMessage) {
		super(strErrorMessage);
	}

}
