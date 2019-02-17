package com.joh.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.cms.dao.DiscountTypeDAO;
import com.joh.cms.model.DiscountType;

@Service
public class DiscountTypeServiceImpl implements DiscountTypeService {

	@Autowired
	private DiscountTypeDAO discountTypeDAO;

	@Override
	public Iterable<DiscountType> findAll() {
		return discountTypeDAO.findAll();
	}

}
