package com.joh.cms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.cms.model.Customer;

public interface CustomerDAO extends CrudRepository<Customer, Integer> {

}
