package com.joh.cms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.cms.model.CustomerPayment;

public interface CustomerPaymentDAO extends CrudRepository<CustomerPayment, Integer> {

}
