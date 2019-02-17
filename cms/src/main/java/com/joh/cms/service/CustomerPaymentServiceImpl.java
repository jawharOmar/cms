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
public class CustomerPaymentServiceImpl implements CustomerPaymentService {

	@Autowired
	private CustomerPaymentDAO customerPaymentDAO;

	@Override
	public Iterable<CustomerPayment> findAll() {
		return customerPaymentDAO.findAll();
	}

	@Override
	public CustomerPayment save(CustomerPayment customerPayment) {
		return customerPaymentDAO.save(customerPayment);
	}

	@Override
	public void delete(int id) {
		customerPaymentDAO.delete(id);
	}

	@Override
	public CustomerPayment findOne(int id) {
		return customerPaymentDAO.findOne(id);
	}

	@Override
	public CustomerPayment update(CustomerPayment customerPayment) {
		if (customerPaymentDAO.findOne(customerPayment.getId()) == null)
			throw new EntityNotFoundException("Customer Payment not fould with id=" + customerPayment.getId());

		return customerPaymentDAO.save(customerPayment);
	}
}
