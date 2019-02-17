package com.joh.cms.service;

import com.joh.cms.model.CustomerPayment;

public interface CustomerPaymentService {

	Iterable<CustomerPayment> findAll();

	CustomerPayment save(CustomerPayment customerPayment);

	void delete(int id);

	CustomerPayment findOne(int id);

	CustomerPayment update(CustomerPayment customerPayment);

}
