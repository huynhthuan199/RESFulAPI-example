package com.safetrust.contact.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetrust.contact.modeldto.ContactDTO;
import com.safetrust.contact.service.CurdCommonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired
	@Qualifier("ContactServiceImpl")	
	private CurdCommonService<ContactDTO> contactService;

	
	@GetMapping("")
	public ResponseEntity<Page<ContactDTO>> allContact(
			@RequestParam(name = "search", required = false) String strInfoSearch
			, @RequestParam("page") Optional<Integer> intPage
			, @RequestParam("size") Optional<Integer> intSize) {
		
		return new ResponseEntity<>(contactService.findAll(strInfoSearch, intPage, intSize), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<ContactDTO> postContact(@Valid @RequestBody ContactDTO contact) {
		return new ResponseEntity<>(contactService.save(contact), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ContactDTO> findIdByContact(@PathVariable("id") Long lngId) {
		return new ResponseEntity<>(contactService.findById(lngId), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContactDTO> updateContact(@PathVariable("id") Long lngId,@Valid @RequestBody ContactDTO contact) {
		return new ResponseEntity<>(contactService.update(lngId, contact), HttpStatus.OK);
	}	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteContact(@PathVariable("id") Long lngId) {
		
		contactService.delete(lngId);
		
		return new ResponseEntity<>("Delete success", HttpStatus.OK);
	}
}
