package com.joh.cms.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.joh.cms.domain.model.NotificationD;
import com.joh.cms.domain.model.ProductD;
import com.joh.cms.model.PriceCategory;
import com.joh.cms.service.PriceCategoryService;
import com.joh.cms.service.ProductService;
import com.joh.cms.service.ReportService;

@Controller()
public class StockController {

	private static final Logger logger = Logger.getLogger(StockController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private PriceCategoryService priceCategoryService;

	@GetMapping(path = "/adminRoot")
	private String adminRoot(Model model) {
		logger.info("adminRoot->fired");

		List<NotificationD> notificationDs = reportService.findAdminNotifications();
		logger.info("notificationDs=" + notificationDs);

		model.addAttribute("notificationDs", notificationDs);

		return "adminRoot";
	}

	@GetMapping(path = "/adminStock")
	private String getAdminStock(Model model) {
		logger.info("getAdminStock->fired");

		List<ProductD> productDs = productService.findStock();

		List<PriceCategory> priceCategories = priceCategoryService.findAll();

		logger.info("productDs=" + productDs);

		model.addAttribute("productDs", productDs);
		model.addAttribute("priceCategories", priceCategories);

		return "adminStock";
	}
}
