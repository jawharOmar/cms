package com.joh.cms.service;

import com.joh.cms.model.Customer;
import com.joh.cms.model.CustomerPayment;
import com.joh.cms.model.CustomerProject;

public interface CustomerService {

	Iterable<Customer> findAll();

	Customer save(Customer customer);

	void delete(int id);

	Customer findOne(int id);

	Customer update(Customer customer);

	void addCustomerPorject(int id, CustomerProject customerProject);

	void addCustomerPayment(int id, CustomerPayment customerPayment);

}
