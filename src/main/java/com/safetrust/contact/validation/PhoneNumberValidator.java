package com.safetrust.contact.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetrust.contact.annotation.PhoneNumberValidationAnnotation;
import com.safetrust.contact.repository.ContactRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements
		ConstraintValidator<PhoneNumberValidationAnnotation, String> {

	@Autowired
	private ContactRepository contactRepo;

	@Override
	public boolean isValid(String strPhone, ConstraintValidatorContext context) {
	
		boolean isExist = contactRepo.existsByStrTelephoneNumber(strPhone);
		
		return !isExist;
	}
}
