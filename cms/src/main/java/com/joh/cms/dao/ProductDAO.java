package com.joh.cms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.cms.model.Product;

public interface ProductDAO extends CrudRepository<Product, Integer>,ProductDAOExt{

}
