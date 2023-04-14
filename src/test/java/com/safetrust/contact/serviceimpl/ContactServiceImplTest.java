package com.safetrust.contact.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.safetrust.contact.entities.Contact;
import com.safetrust.contact.exception.DuplicateUniqueKeyException;
import com.safetrust.contact.exception.NotFoundResourceException;
import com.safetrust.contact.modeldto.ContactDTO;
import com.safetrust.contact.repository.ContactRepository;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

	@InjectMocks
	ContactServiceImpl contactServiceImpl;
	
	@Mock
	ContactRepository contactRepository;
	
	List<Contact> contacts = null;
	
	@BeforeEach
    public void init() {
		contacts = new ArrayList<>();
		contacts.add(new Contact(1L, "Contact1", "Contact1@gmail.com", "+849823334161", "HCM City"));
		contacts.add(new Contact(2L, "Contact2", "Contact2@gmail.com", "+849823334162", "HCM City"));
		contacts.add(new Contact(3L, "Contact3", "Contact3@gmail.com", "+849823334163", "HCM City"));
		contacts.add(new Contact(4L, "Contact4", "Contact4@gmail.com", "+849823334164", "HCM City"));
		contacts.add(new Contact(5L, "Contact5", "Contact5@gmail.com", "+849823334165", "HCM City"));
    }
	
	@Test
	void testFindAll_whenSearchParamNotAvailable() {
		
		Page<Contact> pagedResponse = new PageImpl<>(contacts);
		
		when(contactRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(pagedResponse);
		
		Page<ContactDTO> actual = contactServiceImpl.findAll("", Optional.of(1), Optional.of(1));
	
		assertNotNull(actual);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testFindAll_whenSearchParamAvailable() {
		
		Page<Contact> pagedResponse = new PageImpl<>(contacts);
		
		when(contactRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(pagedResponse);
		
		Page<ContactDTO> actual = contactServiceImpl.findAll("Contact1", Optional.of(1), Optional.of(1));
	
		assertNotNull(actual);
	}

	@Test
	void testFindById_whenNotFoundResource() {
		
		Optional<Contact> mockResponse = Optional.ofNullable(null);
		
		when(contactRepository.findById(Mockito.anyLong())).thenReturn(mockResponse);
		
		assertThrows(NotFoundResourceException.class, () -> {
			contactServiceImpl.findById(10L);
		});
	}
	
	@Test
	void testFindById_whenFoundResource() {
		
		Optional<Contact> mockResponse = Optional.of(contacts.get(0));
		
		when(contactRepository.findById(Mockito.anyLong())).thenReturn(mockResponse);
		
		ContactDTO actual = contactServiceImpl.findById(10L);
		
		assertNotNull(actual);
	}

	@Test
	void testSave_whenSaveSuccess() {
		
		ContactDTO contactDTO = ContactDTO.convertEntityToDTO(contacts.get(0));
		
		when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contacts.get(0));
		
		ContactDTO actual = contactServiceImpl.save(contactDTO);
		
		assertNotNull(actual);
	}
	
	@Test
	void testSave_whenSaveDuplicateKeyData() {
		
		ContactDTO contactDTO = ContactDTO.convertEntityToDTO(contacts.get(0));
		
		DataIntegrityViolationException ex = new DataIntegrityViolationException("errorFake");
		
		when(contactRepository.save(Mockito.any(Contact.class))).thenThrow(ex);
		
		assertThrows(DuplicateUniqueKeyException.class, () ->{
			contactServiceImpl.save(contactDTO);
		});
	}

	@Test
	void testUpdate_whenPrimaryKeyNotExist() {
		
		ContactDTO contactDTO = ContactDTO.convertEntityToDTO(contacts.get(0));
		
		when(contactRepository.existsById(Mockito.anyLong())).thenReturn(false);
		
		assertThrows(NotFoundResourceException.class, () ->{
			contactServiceImpl.update(10L, contactDTO);
		});
		
		verify(contactRepository, Mockito.never()).save(contacts.get(0));
	}
	
	@Test
	void testUpdate_whenUpdateSuccess() {
		
		ContactDTO contactDTO = ContactDTO.convertEntityToDTO(contacts.get(0));
		
		when(contactRepository.existsById(Mockito.anyLong())).thenReturn(true);
		
		when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contacts.get(0));
		
		ContactDTO actual = contactServiceImpl.update(10L, contactDTO);
		
		verify(contactRepository, Mockito.atLeastOnce()).save(Mockito.any());
		
		assertNotNull(actual);
	}

	@Test
	void testDelete_whenPrimaryKeyNotExist() {
		
		when(contactRepository.existsById(Mockito.anyLong())).thenReturn(false);
		
		assertThrows(NotFoundResourceException.class, () -> {
			contactServiceImpl.delete(10L);
		});
	}
	
	@Test
	void testDelete_whenDeleteSuccess() {
		
		when(contactRepository.existsById(Mockito.anyLong())).thenReturn(true);
		
		doNothing().when(contactRepository).deleteById(Mockito.anyLong());
		
		assertDoesNotThrow(() -> {
			contactServiceImpl.delete(10L);
		});
		
		verify(contactRepository, Mockito.atLeastOnce()).deleteById(Mockito.anyLong());
	}

}
