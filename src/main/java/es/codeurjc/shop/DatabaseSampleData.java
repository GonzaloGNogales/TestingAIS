package es.codeurjc.shop;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.ProductService;

@Component
public class DatabaseSampleData {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustomerService customerService;
	
	@PostConstruct
	public void initData() {
		
		productService.createProduct("Cartón de leche", 10, 1);
		productService.createProduct("Cereales", 25, 0);
		productService.createProduct("Galletas", 30, 3);	
		
		customerService.createCustomer("Pepe",  15);
		customerService.createCustomer("Juan",  5);
		customerService.createCustomer("Antonio",  60);
		
	}

}
