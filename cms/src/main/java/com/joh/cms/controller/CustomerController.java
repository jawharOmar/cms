package com.joh.cms.controller;

import java.util.List;

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
import com.joh.cms.model.PriceCategory;
import com.joh.cms.service.CustomerService;
import com.joh.cms.service.PriceCategoryService;

@Controller()
@RequestMapping(path = "/customers")
public class CustomerController {

	private static final Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PriceCategoryService priceCategoryService;

	@GetMapping()
	public String getAllCustomers(Model model) {
		logger.info("getAllCustomers->fired");

		Iterable<Customer> customers = customerService.findAll();

		logger.info("customers=" + customers);
		model.addAttribute("customers", customers);

		return "adminCustomers";

	}

	@GetMapping(path = "/add")
	private String getAddingCustomer(Model model) {
		logger.info("getAddingCustomer->fired");

		List<PriceCategory> priceCategories = priceCategoryService.findAll();
		logger.info("priceCategories=" + priceCategories);

		model.addAttribute("customer", new Customer());

		model.addAttribute("priceCategories", priceCategories);
		return "customer/addCustomer";
	}

	@PostMapping(path = "/add")
	private String addCustomer(@RequestBody @Valid Customer customer, BindingResult result, Model model) {

		logger.info("addCustomer->fired");
		logger.info("customer=" + customer);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("customer", customer);
			return "customer/addCustomer";
		} else {

			if (customer.getPriceCategory().getId() == 0)
				customer.setPriceCategory(null);

			customerService.save(customer);
			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteVendor(@PathVariable int id) {
		logger.info("deleteVendor->fired");
		customerService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String editingCustomer(@PathVariable int id, Model model) {
		logger.info("editingCustomer->fired");
		logger.info("id=" + id);
		Customer customer = customerService.findOne(id);
		logger.info("customer=" + customer);

		List<PriceCategory> priceCategories = priceCategoryService.findAll();
		logger.info("priceCategories=" + priceCategories);

		model.addAttribute("priceCategories", priceCategories);

		model.addAttribute("customer", customer);

		return "customer/editCustomer";
	}

	@PostMapping(path = "/update")
	private String updateCustomer(@RequestBody Customer customer, BindingResult result) {
		logger.info("updateCustomer->fired");

		logger.info("customer=" + customer);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			return "customer/editCustomer";
		} else {

			if (customer.getPriceCategory().getId() == 0)
				customer.setPriceCategory(null);
			customerService.update(customer);
			return "success";
		}
	}

}
