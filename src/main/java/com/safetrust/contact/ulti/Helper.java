package com.safetrust.contact.ulti;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Helper {

	private Helper() {
		
	}
	
	/**
	 * Get error message form validate bean to JSON object
	 * @param ex    - Exception to validation
	 * @param clazz - Class use reflection get field JSON
	 * @return Map<String, String>
	 */
	public static final Map<String, String> getMessages(MethodArgumentNotValidException ex, Class<?> clazz) {
    	Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( error -> {

        	Field field = null;
			try {
				field = clazz.getDeclaredField(((FieldError) error).getField());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	JsonProperty propertyJson = field.getAnnotation(JsonProperty.class);
        	
            String errorMessage = error.getDefaultMessage();
            errors.put(propertyJson.value(), errorMessage);
        });
        return errors;
    }
}
