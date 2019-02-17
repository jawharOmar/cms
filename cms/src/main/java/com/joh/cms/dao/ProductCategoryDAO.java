package com.joh.cms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.cms.model.ProductCategory;

public interface ProductCategoryDAO extends CrudRepository<ProductCategory, Integer> {

}
