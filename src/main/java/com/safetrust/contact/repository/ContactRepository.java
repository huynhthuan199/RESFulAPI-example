package com.safetrust.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.safetrust.contact.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

	boolean existsByStrEmailAddress(String strEmail);

	boolean existsByStrTelephoneNumber(String strPhone);

}
