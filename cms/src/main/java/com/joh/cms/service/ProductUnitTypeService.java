package com.joh.cms.service;

import com.joh.cms.model.ProductUnitType;

public interface ProductUnitTypeService {

	Iterable<ProductUnitType> findAll();

	ProductUnitType save(ProductUnitType productUnitType);

}
