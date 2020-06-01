package es.codeurjc.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.product.Product;
import es.codeurjc.shop.domain.product.ProductNotFoundException;
import es.codeurjc.shop.domain.product.ProductService;
import es.codeurjc.shop.domain.purchase.PurchaseService;

@Controller
public class ShopWebController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private PurchaseService purchaseService;

	@GetMapping("/")
	public String index(Model model) {

		model.addAttribute("products", this.productService.getProducts());

		return "index";
	}
	
	@GetMapping("/product/{id}")
	public String product(@PathVariable long id, Model model) throws ProductNotFoundException {

		Product product = this.productService.getProduct(id);
		
		model.addAttribute("product", product);
		model.addAttribute("outOfStock", product.getStock() == 0);

		return "product";
	}
	
	@PostMapping("/purchase")
	public String buyProduct(@RequestParam long productId, @RequestParam long customerId, Model model) {

		try { 
		
			purchaseService.createPurchase(customerId, productId);
		
		} catch (ShopException e) {			
			model.addAttribute("error", e.getClass().getSimpleName());
		}

		return "purchase";
	}

}
