package com.safetrust.contact.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.safetrust.contact.validation.PhoneNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumberValidationAnnotation {
	
      String message() default "Phone number invalid";

      Class<?>[] groups() default {} ;

      Class<? extends Payload>[] payload() default {} ;
}
