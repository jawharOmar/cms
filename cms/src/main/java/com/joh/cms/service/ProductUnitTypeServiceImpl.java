package com.joh.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.cms.dao.ProductUnitTypeDAO;
import com.joh.cms.model.ProductUnitType;

@Service
public class ProductUnitTypeServiceImpl implements ProductUnitTypeService {

	@Autowired
	private ProductUnitTypeDAO productUnitTypeDAO;

	@Override
	public Iterable<ProductUnitType> findAll() {
		return productUnitTypeDAO.findAll();
	}
	
	@Override
	public ProductUnitType save(ProductUnitType productUnitType) {
		return productUnitTypeDAO.save(productUnitType);
	}

}
