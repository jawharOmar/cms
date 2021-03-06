package com.joh.cms.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.cms.dao.CustomerOrderDAO;
import com.joh.cms.dao.ProductDAO;
import com.joh.cms.dao.ProductStepUpDAO;
import com.joh.cms.domain.model.ProductD;
import com.joh.cms.exception.ItemNotAvaiableException;
import com.joh.cms.model.CustomerOrder;
import com.joh.cms.model.CustomerOrderDetail;
import com.joh.cms.model.Product;
import com.joh.cms.model.ProductStepUp;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	private static final Logger logger = Logger.getLogger(CustomerOrderServiceImpl.class);

	@Autowired
	private CustomerOrderDAO customerOrderDAO;

	@Autowired
	private ProductStepUpDAO productStepUpDAO;

	@Autowired
	private ProductDAO productDAO;

	@Override

	@Transactional
	public CustomerOrder save(CustomerOrder customerOrder) {
		double totalPrice = 0;
		for (CustomerOrderDetail customerOrderDetail : customerOrder.getCustomerOrderDetails()) {
			Product product = null;

			ProductD productD = productDAO.findProductByCode(customerOrderDetail.getProductCode());

			totalPrice += customerOrderDetail.getQuantity() * customerOrderDetail.getPrice();
			logger.info("customerOrderDetail.getPrice()=" + customerOrderDetail.getPrice());

			// if (customerOrder.getDiscountType() == null &&
			// !customerOrderDetail.getPrice().equals(0)) {
			// throw new DataIntegrityViolationException("You are tring to discount without
			// assign discount type");
			// }

			for (int i = 0; i < customerOrderDetail.getQuantity(); i++) {
				ProductStepUp itemForStockDown = productStepUpDAO
						.findProductStepUpForStockDown(customerOrderDetail.getProductCode());

				if (itemForStockDown == null) {
					String message = String.format("This product (%s) is not avaiable enough in the stock",
							customerOrderDetail.getProductCode());
					throw new ItemNotAvaiableException(message);
				}

				if (i == 0 && itemForStockDown != null) {
					product = itemForStockDown.getProduct();
					customerOrderDetail.setProduct(product);
				}

				ProductStepUp productStepUp = new ProductStepUp();
				productStepUp.setId(itemForStockDown.getId());
				customerOrderDetail.getProductStepUpIds().add(productStepUp);
				productStepUpDAO.stockDown(itemForStockDown.getId());
			}

		}

		totalPrice = (double) Math.round(totalPrice * 1000d) / 1000d;

		customerOrder.setTotalPrice(totalPrice);

		return customerOrderDAO.save(customerOrder);
	}

	@Override
	@Transactional
	public CustomerOrder saveReturn(CustomerOrder customerOrder) {
		double totalPrice = 0;
		for (CustomerOrderDetail customerOrderDetail : customerOrder.getCustomerOrderDetails()) {

			Product product = null;

			totalPrice += customerOrderDetail.getQuantity() * customerOrderDetail.getPrice();
			logger.info("productD.getPrice()=" + customerOrderDetail.getPrice());

			ProductStepUp itemForStockDown = productStepUpDAO.findOne(customerOrderDetail.getProductStepUpId());

			System.err.println("customerOrderDetail.getQuantity()=" + customerOrderDetail.getQuantity());
			System.err.println("itemForStockDown.getQuantity()=" + itemForStockDown.getQuantity());

			if (itemForStockDown == null || customerOrderDetail.getQuantity() > itemForStockDown.getQuantity()) {
				String message = String.format("This product (%s) is not avaiable enough in the stock",
						customerOrderDetail.getProductCode());
				throw new ItemNotAvaiableException(message);
			}

			if (itemForStockDown != null) {
				product = itemForStockDown.getProduct();
				customerOrderDetail.setProduct(product);
			}
			customerOrderDetail.getProductStepUpIds().add(itemForStockDown);

			for (int i = 0; i < customerOrderDetail.getQuantity(); i++) {
				productStepUpDAO.stockDown(itemForStockDown.getId());
			}

		}

		totalPrice = (double) Math.round(totalPrice * 1000d) / 1000d;

		customerOrder.setTotalPrice(totalPrice);

		return customerOrderDAO.save(customerOrder);
	}

	@Override
	public CustomerOrder findOne(int id) {
		CustomerOrder customerOrder = customerOrderDAO.findOne(id);
		if (customerOrder == null)
			throw new EntityNotFoundException("" + id);
		return customerOrder;
	}

	@Override
	@Transactional
	public CustomerOrder update(CustomerOrder customerOrder) {
		customerOrderDAO.delete(customerOrder.getId());
		customerOrder.setId(customerOrder.getId());
		return save(customerOrder);
	}

	@Override
	public List<CustomerOrder> findAllByOrderTimeBetween(Date from, Date to) {
		return customerOrderDAO.findAllByOrderTimeBetween(from, to);
	}

	@Override
	@Transactional
	public void delete(int id) {
		customerOrderDAO.delete(id);
	}

	@Override
	public double totalLoan(int id) {
		return customerOrderDAO.totalLoan(id) != null ? customerOrderDAO.totalLoan(id) : 0;
	}

}
