package com.safetrust.contact.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", length=50, nullable=false)
	private String strName;
	
	@Column(name="email_address", length=50, nullable=false, unique=true)
	private String strEmailAddress;
	
	@Column(name="telephone_number", length=50, nullable=false, unique=true)
	private String strTelephoneNumber;
	
	@Column(name="postal_address", length=50, nullable=false, unique=false)
	private String strPostalAddress;

}
