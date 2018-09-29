/**
 * 
 */
package com.javainuse.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author rajesh
 *
 */
@XmlRootElement(name = "products")
@XmlAccessorType (XmlAccessType.FIELD)
public class Products {
	
	public List<Product> getProductList() {
		return ProductList;
	}

	public void setProductList(List<Product> productList) {
		ProductList = productList;
	}
	@XmlElement(name = "product")
	List<Product> ProductList;

}
