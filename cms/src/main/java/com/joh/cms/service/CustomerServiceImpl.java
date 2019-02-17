package com.joh.cms.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.cms.dao.CustomerDAO;
import com.joh.cms.dao.CustomerPaymentDAO;
import com.joh.cms.dao.CustomerProjectDAO;
import com.joh.cms.model.Customer;
import com.joh.cms.model.CustomerPayment;
import com.joh.cms.model.CustomerProject;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private CustomerProjectDAO customerProjectDAO;
	
	@Autowired
	private CustomerPaymentDAO customerPaymentDAO;

	@Override
	public Iterable<Customer> findAll() {
		return customerDAO.findAll();
	}

	@Override
	public Customer save(Customer customer) {
		return customerDAO.save(customer);
	}

	@Override
	public void delete(int id) {
		customerDAO.delete(id);
	}

	@Override
	public Customer findOne(int id) {
		return customerDAO.findOne(id);
	}

	@Override
	public Customer update(Customer customer) {
		if (customerDAO.findOne(customer.getId()) == null)
			throw new EntityNotFoundException("Customer not fould with id=" + customer.getId());

		return customerDAO.save(customer);
	}

	@Override
	@Transactional
	public void addCustomerPorject(int id, CustomerProject customerProject) {
		customerProjectDAO.save(customerProject);
		Customer customer = customerDAO.findOne(id);
		customer.getCustomerProjects().add(customerProject);
		customerDAO.save(customer);
	}

	@Override
	@Transactional
	public void addCustomerPayment(int id, CustomerPayment customerPayment) {
		customerPaymentDAO.save(customerPayment);
		Customer customer = customerDAO.findOne(id);
		customer.getCustomerPayments().add(customerPayment);
		customerDAO.save(customer);
	}
}
