package com.joh.cms.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.cms.domain.model.JsonResponse;
import com.joh.cms.model.Customer;
import com.joh.cms.model.CustomerOrder;
import com.joh.cms.model.Product;
import com.joh.cms.service.CustomerOrderService;
import com.joh.cms.service.CustomerService;
import com.joh.cms.service.ProductService;

@Controller
@RequestMapping(path = "/customerOrders")
public class CustomerOrderController {

	private static final Logger logger = Logger.getLogger(CustomerOrderController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping(path = "/add")
	private String getAddingCustomerOrder(Model model) throws JsonProcessingException {

		logger.info("getAddingCustomerOrder->fired");

		Iterable<Customer> customers = customerService.findAll();

		logger.info("customers=" + customers);

		ObjectMapper objectMapper = new ObjectMapper();
		model.addAttribute("jsonCustomers", objectMapper.writeValueAsString(customers));

		Iterable<Product> products = productService.findAll();
		logger.info("products=" + products);
		model.addAttribute("jsonProducts", objectMapper.writeValueAsString(products));
		model.addAttribute("jsonCustomerOrder", objectMapper.writeValueAsString(new CustomerOrder()));

		return "adminAddCustomerOrder";
	}

	@PostMapping(path = "/add")
	@ResponseBody
	private JsonResponse addCustomerOrder(@RequestBody @Validated() CustomerOrder customerOrder, Locale locale) {

		logger.info("addCustomerOrder->fired");

		logger.info("customerOrder=" + customerOrder);

		customerOrder = customerOrderService.save(customerOrder);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));
		jsonResponse.setEtc("" + customerOrder.getId());

		return jsonResponse;
	}

	@PostMapping(path = "/return")
	@ResponseBody
	private JsonResponse retrunCustomerOrder(@RequestBody @Validated() CustomerOrder customerOrder) {

		logger.info("retrunCustomerOrder->fired");

		logger.info("customerOrder=" + customerOrder);

		customerOrder = customerOrderService.saveReturn(customerOrder);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage("success");
		jsonResponse.setEtc("" + customerOrder.getId());

		return jsonResponse;
	}

	@GetMapping(path = "/edit/{id}")
	private String getEditingCustomerOrder(@PathVariable int id, Model model) throws JsonProcessingException {

		logger.info("getEditingCustomerOrder->fired");

		CustomerOrder customerOrder = customerOrderService.findOne(id);

		logger.info("customerOrder=" + customerOrder);

		Iterable<Customer> customers = customerService.findAll();

		logger.info("customers=" + customers);

		ObjectMapper objectMapper = new ObjectMapper();
		model.addAttribute("jsonCustomers", objectMapper.writeValueAsString(customers));

		Iterable<Product> products = productService.findAll();
		logger.info("products=" + products);
		model.addAttribute("jsonProducts", objectMapper.writeValueAsString(products));
		model.addAttribute("jsonCustomerOrder", objectMapper.writeValueAsString(customerOrder));

		return "adminEditCustomerOrder";
	}

	@PostMapping(path = "/update")
	@ResponseBody
	private JsonResponse udpateCustomerOrder(

			@RequestBody @Validated() CustomerOrder customerOrder) {

		logger.info("udpateCustomerOrder->fired");

		logger.info("customerOrder=" + customerOrder);

		customerOrder = customerOrderService.update(customerOrder);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage("success");
		jsonResponse.setEtc("" + customerOrder.getId());

		return jsonResponse;
	}

	@GetMapping(path = "/delete/{id}")
	private String delete(@PathVariable int id) {

		logger.info("udpateCustomerOrder->fired");

		logger.info("id=" + id);

		customerOrderService.delete(id);

		return "success";
	}

	@GetMapping(path = "/{id}")
	public String getCustomerOrder(@PathVariable int id, Model model) {

		logger.info("getCustomerOrder->fired");

		CustomerOrder customerOrder = customerOrderService.findOne(id);
		logger.info("customerOrder=" + customerOrder);

		model.addAttribute("customerOrder", customerOrder);

		return "customerOrder/getCustomerOrder";
	}

	@GetMapping()
	private String getCustomerOrdersByDate(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getCustomerOrdersByDate->fired");

		List<CustomerOrder> customerOrders = customerOrderService.findAllByOrderTimeBetween(from, to);

		logger.info("customerOrders=" + customerOrders);

		model.addAttribute("customerOrders", customerOrders);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "adminCustomerOrders";
	}

	@GetMapping(path = "/sold")
	private String getCustomerOrderProductSold(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getCustomerOrderProductSold->fired");

		List<CustomerOrder> customerOrders = customerOrderService.findAllByOrderTimeBetween(from, to);

		logger.info("customerOrders=" + customerOrders);

		model.addAttribute("customerOrders", customerOrders);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "adminCustomerOrderProductSold";
	}

}
