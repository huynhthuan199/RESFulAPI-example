package com.safetrust.contact.exception;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidDataErrorInfo {

	@JsonProperty("errors")
	private Map<String,String > errorMessage;
	
	@JsonProperty("errorCode")
	private String strErrorCode;
	
}