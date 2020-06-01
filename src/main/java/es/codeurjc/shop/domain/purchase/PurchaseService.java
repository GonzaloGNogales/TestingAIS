package es.codeurjc.shop.domain.purchase;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.ProductService;
import es.codeurjc.shop.notification.NotificationService;

@Service
public class PurchaseService {

	private PurchaseRepository purchaseRepository;
	private CustomerService customerService;
	private ProductService productService;

	private NotificationService notificationService;

	public PurchaseService(PurchaseRepository purchaseRepository, CustomerService customerService,
			ProductService productService, NotificationService notificationService) {
		this.purchaseRepository = purchaseRepository;
		this.customerService = customerService;
		this.productService = productService;
		this.notificationService = notificationService;
	}

	public List<Purchase> getPurchases() {
		return purchaseRepository.findAll();
	}

	public Purchase getPurchase(long id) {
		return purchaseRepository.findById(id).orElseThrow(PurchaseNotFoundException::new);
	}

	@Transactional
	public Purchase createPurchase(long customerId, long productId) throws ShopException {

		long cost = productService.getProductCost(productId);
		productService.withdrawProduct(productId);
		customerService.reserveCredit(customerId, cost);

		Purchase purchase = new Purchase(customerId, productId);

		notificationService.notify("Purchase: customer=" + customerId + ", product=" + productId);

		return purchaseRepository.save(purchase);
	}
}
