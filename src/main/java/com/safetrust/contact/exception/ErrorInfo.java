package com.safetrust.contact.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {

	@JsonProperty("errorMessage")
	private String strErrorMessage;
	
	@JsonProperty("errorCode")
	private String strErrorCode;
	
}
