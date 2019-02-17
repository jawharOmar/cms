package com.joh.cms.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joh.cms.model.Customer;
import com.joh.cms.model.CustomerPayment;
import com.joh.cms.service.CustomerOrderService;
import com.joh.cms.service.CustomerPaymentService;
import com.joh.cms.service.CustomerService;

@Controller()
@RequestMapping(path = "/customerPayments")
public class CustomerPaymentController {

	private static final Logger logger = Logger.getLogger(CustomerPaymentController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerPaymentService customerPaymentService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@GetMapping(path = "/customer/{id}")
	public String getCustomerProjects(@PathVariable int id, Model model) {
		logger.info("getCustomerProjects->fired");
		logger.info("id=" + id);

		Customer customer = customerService.findOne(id);

		logger.info("customer=" + customer);
		model.addAttribute("customer", customer);

		return "customerPayments";
	}

	@GetMapping(path = "/add/customer/{id}")
	private String getAddingCustomerPayment(@PathVariable int id, Model model) {
		logger.info("getAddingCustomerPayment->fired");
		logger.info("customerId=" + id);

		CustomerPayment customerPayment = new CustomerPayment();

		model.addAttribute("customer", customerService.findOne(id));
		model.addAttribute("customerPayment", customerPayment);

		return "customerPayment/addCustomerPayment";
	}

	@PostMapping(path = "/add/customer/{id}")
	private String addCustomerPayment(@PathVariable int id, @RequestBody @Valid CustomerPayment customerPayment,
			BindingResult result, Model model) {

		logger.info("addCustomerPayment->fired");
		logger.info("customerId=" + id);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("customer", customerService.findOne(id));
			model.addAttribute("customerPayment", customerPayment);
			return "customerPayment/addCustomerPayment";
		} else {

			if (customerPayment.getCustomerProject().getId() == 0)
				customerPayment.setCustomerProject(null);

			customerService.addCustomerPayment(id, customerPayment);

			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteCustomerPayment(@PathVariable int id) {
		logger.info("deleteCustomerPayment->fired");
		customerPaymentService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}/customer/{customerId}")
	private String getEditingCustomerPayment(@PathVariable int id, @PathVariable int customerId, Model model) {
		logger.info("getEditingCustomerPayment->fired");
		logger.info("id=" + id);
		CustomerPayment customerPayment = customerPaymentService.findOne(id);
		logger.info("customerPayment=" + customerPayment);

		model.addAttribute("customer", customerService.findOne(customerId));
		model.addAttribute("customerPayment", customerPayment);

		return "customerPayment/editCustomerPayment";
	}

	@PostMapping(path = "/update/customer/{id}")
	private String updateCustomerPayment(@RequestBody CustomerPayment customerPayment, @PathVariable int id,
			BindingResult result, Model model) {
		logger.info("updateCustomerPayment->fired");

		logger.info("customerPayment=" + customerPayment);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("customerPayment", customerPayment);
			model.addAttribute("customer", customerService.findOne(id));
			return "customerPayment/editCustomerPayment";
		} else {

			if (customerPayment.getCustomerProject().getId() == 0)
				customerPayment.setCustomerProject(null);
			customerPaymentService.update(customerPayment);
			return "success";
		}
	}

	@GetMapping(path = "/print/{id}")
	public String customerPaymentPrint(@PathVariable int id, Model model) {
		logger.info("customerPaymentPrint->fired");
		logger.info("id=" + id);

		CustomerPayment customerPayment = customerPaymentService.findOne(id);
		logger.info("customerPayment=" + customerPayment);

		double totalLoan = customerOrderService.totalLoan(customerPayment.getCustomer().getId());

		logger.info("totalLoan=" + totalLoan);

		model.addAttribute("totalLoan", totalLoan);

		model.addAttribute("customerPayment", customerPayment);

		return "customerPaymentPrint";
	}

}
