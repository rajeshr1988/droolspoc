package com.javainuse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javainuse.SpringBootDroolsHelloWorldApp;
import com.javainuse.model.Product;
import com.javainuse.model.Products;
import com.javainuse.service.JewelleryShopService;

@RestController
public class JewelleryShopController {
	 private static final Logger logger = LoggerFactory.getLogger(JewelleryShopController.class);
	private final JewelleryShopService jewelleryShopService;

	@Autowired
	public JewelleryShopController(JewelleryShopService jewelleryShopService) {
		this.jewelleryShopService = jewelleryShopService;
	}

	@RequestMapping(value = "/getdiscount", method = RequestMethod.POST, produces = "application/xml")
	public Products getQuestions(@RequestBody Products products) {
		logger.debug("debug controller");
		logger.info("info controller");
		logger.warn("warn controller");
		//Product product = new Product();
		//product.setType(type);

		jewelleryShopService.getProductDiscount(products);

		return products;
	}

}
