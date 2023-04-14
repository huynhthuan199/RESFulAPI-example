package com.safetrust.contact.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetrust.contact.annotation.EmailValidationAnnotation;
import com.safetrust.contact.repository.ContactRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements
		ConstraintValidator<EmailValidationAnnotation, String> {

	@Autowired
	private ContactRepository contactRepo;

	@Override
	public boolean isValid(String strEmail, ConstraintValidatorContext context) {
	
		boolean isExist = contactRepo.existsByStrEmailAddress(strEmail);
		
		return !isExist;
		

	}
}
