package com.joh.cms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.cms.model.PriceCategory;

public interface PriceCategoryDAO extends CrudRepository<PriceCategory, Integer> {

}
