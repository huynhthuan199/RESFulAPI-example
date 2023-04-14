package com.safetrust.contact.ulti;

import java.util.Arrays;

public class Contants {

	private Contants() {
		
	}
	
	/**
	 * ErrorCode <br/>
	 * <p>
	 * Define error code for application
	 * </p>
	 * @author ThuanNH
	 */
	public enum ErrorCode {
		NOT_FOUND("data-001"),
		DATA_INVALID("data-002"),
		DATA_DUPLICATE("data-003");

		private String strErrorCode;

		private ErrorCode(String strErrorCode) {
			this.strErrorCode = strErrorCode;
		}

		public String getStrErrorCode() {
			return strErrorCode;
		}

		public static ErrorCode valueOfEnum(String s) throws IllegalArgumentException {
			return Arrays.stream(ErrorCode.values())
					.filter(v -> v.strErrorCode.equals(s))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("unknown value: " + s));
		}
	}
	
	/**
	 * ErrorMessage <br/>
	 * <p>
	 * Define error message for application
	 * </p>
	 * @author ThuanNH
	 */
	public enum ErrorMessage {
		CONTACT_NOT_FOUND("Contact %s not found! pls try again"),
		DUPLICATE_KEY("Duplicate unique key");

		private String strErrorMsg;

		private ErrorMessage(String strErrorMsg) {
			this.strErrorMsg = strErrorMsg;
		}

		public String getStrErrorMsg() {
			return strErrorMsg;
		}

		public static ErrorCode valueOfEnum(String s) throws IllegalArgumentException {
			return Arrays.stream(ErrorCode.values())
					.filter(v -> v.strErrorCode.equals(s))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("unknown value: " + s));
		}
	}
}
