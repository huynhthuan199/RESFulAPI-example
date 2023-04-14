package com.safetrust.contact.modeldto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetrust.contact.annotation.EmailValidationAnnotation;
import com.safetrust.contact.annotation.PhoneNumberValidationAnnotation;
import com.safetrust.contact.entities.Contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {

	@NotBlank(message = "Name is not blank")
	@JsonProperty("name")
	private String strName;

	@NotBlank(message = "Email is not blank")
	@Email(message = "'${validatedValue}' invalid format")
	@EmailValidationAnnotation(message = "'${validatedValue}' already exists in the system")
	@JsonProperty("emailAddress")
	private String strEmailAddress;

	@NotBlank(message = "telephone number is not blank")
	@PhoneNumberValidationAnnotation(message = "'${validatedValue}' already exists in the system")
	@Pattern(regexp = "(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\\b", message = "Phone number invalid")
	@JsonProperty("telephoneNumber")
	private String strTelephoneNumber;

	@NotBlank(message = "Postal address is not blank")
	@JsonProperty("postalAddress")
	private String strPostalAddress;

	public static ContactDTO convertEntityToDTO(Contact entity) {
		ContactDTO contact = new ContactDTO();
		contact.setStrName(entity.getStrName());
		contact.setStrEmailAddress(entity.getStrEmailAddress());
		contact.setStrPostalAddress(entity.getStrPostalAddress());
		contact.setStrTelephoneNumber(entity.getStrTelephoneNumber());
		return contact;
	}

	public static Contact convertDTOToEntity(ContactDTO contact) {
		Contact entity = new Contact();
		entity.setStrName(contact.getStrName());
		entity.setStrEmailAddress(contact.getStrEmailAddress());
		entity.setStrPostalAddress(contact.getStrPostalAddress());
		entity.setStrTelephoneNumber(contact.getStrTelephoneNumber());
		return entity;
	}
}
