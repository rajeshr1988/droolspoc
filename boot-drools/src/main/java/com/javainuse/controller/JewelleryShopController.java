package com.javainuse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javainuse.model.Product;
import com.javainuse.service.JewelleryShopService;

@RestController
public class JewelleryShopController {

	private final JewelleryShopService jewelleryShopService;

	@Autowired
	public JewelleryShopController(JewelleryShopService jewelleryShopService) {
		this.jewelleryShopService = jewelleryShopService;
	}

	@RequestMapping(value = "/getdiscount", method = RequestMethod.POST, produces = "application/xml")
	public Product getQuestions(@RequestBody Product product) {

		//Product product = new Product();
		//product.setType(type);

		jewelleryShopService.getProductDiscount(product);

		return product;
	}

}
