package com.safetrust.contact.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.safetrust.contact.modeldto.ContactDTO;
import com.safetrust.contact.ulti.Contants.ErrorCode;
import com.safetrust.contact.ulti.Contants.ErrorMessage;
import com.safetrust.contact.ulti.Helper;;

@RestControllerAdvice
public class HandleGlobal {
	
	private final Logger logger = LoggerFactory.getLogger (this.getClass());

	@ExceptionHandler(value = { NotFoundResourceException.class })
	protected ResponseEntity<ErrorInfo> handleResourceNotFound(NotFoundResourceException ex) {

		String strErrorMsg = String.format(ErrorMessage.CONTACT_NOT_FOUND.getStrErrorMsg(), ex.getMessage());

		logger.error("Exception info: {}", strErrorMsg);
		
		return new ResponseEntity<>(new ErrorInfo(strErrorMsg, ErrorCode.NOT_FOUND.getStrErrorCode()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	protected ResponseEntity<InvalidDataErrorInfo> handleValidationException(
			MethodArgumentNotValidException ex) {
		
		logger.error("Exception info: {}", ex.getMessage());
		
		return new ResponseEntity<>(new InvalidDataErrorInfo(Helper.getMessages(ex, ContactDTO.class),
				ErrorCode.DATA_INVALID.getStrErrorCode()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { DuplicateUniqueKeyException.class })
	protected ResponseEntity<ErrorInfo> handleDupicateUniqueKeyException(
			DuplicateUniqueKeyException ex) {
		
		logger.error("Exception info: {}", ex.getMessage());
		
		return new ResponseEntity<>(new ErrorInfo(ErrorMessage.DUPLICATE_KEY.getStrErrorMsg(), ErrorCode.DATA_DUPLICATE.getStrErrorCode()),
				HttpStatus.BAD_REQUEST);
	}
}
