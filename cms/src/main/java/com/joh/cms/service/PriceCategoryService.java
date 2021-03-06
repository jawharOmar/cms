package com.joh.cms.service;

import java.util.List;

import com.joh.cms.model.PriceCategory;

public interface PriceCategoryService {

	List<PriceCategory> findAll();

	PriceCategory save(PriceCategory priceCategory);

	void delete(int id);

	PriceCategory findOne(int id);

	PriceCategory update(PriceCategory priceCategory);

}
