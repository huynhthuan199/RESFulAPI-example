package com.safetrust.contact.serviceimpl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.safetrust.contact.entities.Contact;
import com.safetrust.contact.exception.DuplicateUniqueKeyException;
import com.safetrust.contact.exception.NotFoundResourceException;
import com.safetrust.contact.modeldto.ContactDTO;
import com.safetrust.contact.repository.ContactRepository;
import com.safetrust.contact.service.CurdCommonService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * ContactServiceImpl <br/>
 * <p>
 * Implement Contact
 * </p>
 * @author ThuanNH
 */
@Service("ContactServiceImpl")
public class ContactServiceImpl implements CurdCommonService<ContactDTO> {

	@Autowired
	private ContactRepository contactRepo;

	@Override
	public Page<ContactDTO> findAll(String strSearch, Optional<Integer> page, Optional<Integer> size) {
		
		int intPage = page.orElse(0);
		
		int intSize = size.orElse(10);

		Page<Contact> pages = null;

		if (StringUtils.isBlank(strSearch)) {
			pages = contactRepo.findAll(PageRequest.of(intPage, intSize));
		} else {
			pages = contactRepo.findAll(this.nameLike(strSearch),
					PageRequest.of(intPage, intSize, Sort.by("strName").descending()));
		}

		return pages.map(ContactDTO::convertEntityToDTO);
	}

	@Override
	public ContactDTO findById(Long lngId) {

		Contact contact = contactRepo.findById(lngId)
				.orElseThrow(() -> new NotFoundResourceException(lngId.toString()));

		return ContactDTO.convertEntityToDTO(contact);
	}

	@Override
	public ContactDTO save(ContactDTO contact) {
		try {
			contactRepo.save(ContactDTO.convertDTOToEntity(contact));
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateUniqueKeyException(e.getMessage());
		}
		return contact;
	}

	@Override
	public ContactDTO update(Long lngId, ContactDTO contactDto) {

		boolean isExist = contactRepo.existsById(lngId);

		if (!isExist) {
			throw new NotFoundResourceException(lngId.toString());
		}

		Contact contact = ContactDTO.convertDTOToEntity(contactDto);

		contact.setId(lngId);

		contactRepo.save(contact);

		return contactDto;
	}

	@Override
	public void delete(Long lngId) {

		boolean isExist = contactRepo.existsById(lngId);

		if (!isExist) {
			throw new NotFoundResourceException(lngId.toString());
		}
		contactRepo.deleteById(lngId);
	}

	/**
	 * Have optimize dynamic query
	 * @param strName - name search
	 * @return Specification<Contact>
	 */
	private Specification<Contact> nameLike(String strName) {
		return new Specification<Contact>() {
	
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Contact> root,
					CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("strName"), "%" + strName + "%");
			}
		};
	}

}
