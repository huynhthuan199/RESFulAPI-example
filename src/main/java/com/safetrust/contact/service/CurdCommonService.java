package com.safetrust.contact.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

/**
 * CurdCommonService <br/>
 * <p>
 * This is interface common for CURD API
 * </p>
 * @author ThuanNH
 */
public interface CurdCommonService<T> {

	/**
	 * Get All
	 * @param page - page need get
	 * @param size - size need get
	 * @return Page<T>
	 */
	Page<T> findAll(String strSearch, Optional<Integer> page, Optional<Integer> size);
	
	/**
	 * Search By Id
	 * @param lngId - id of Entity
	 * @return T
	 */
	T findById(Long lngId);
	
	/**
	 * Create
	 * @param object - info need create
	 * @return
	 */
	T save(T object);
	
	/**
	 * Update
	 * @param lngId - id of Entity
	 * @param object - info need update
	 * @return T
	 */
	T update(Long lngId, T object);
	
	/**
	 * Delete
	 * @param lngId  - id of Entity
	 */
	void delete(Long lngId);
}
