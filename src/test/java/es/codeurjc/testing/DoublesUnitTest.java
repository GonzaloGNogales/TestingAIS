package es.codeurjc.testing;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.customer.CustomerCreditLimitExceededException;
import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.ProductService;
import es.codeurjc.shop.domain.product.ProductStockWithdrawExceededException;
import es.codeurjc.shop.domain.purchase.PurchaseRepository;
import es.codeurjc.shop.domain.purchase.PurchaseService;
import es.codeurjc.shop.notification.NotificationService;

public class DoublesUnitTest {

	private PurchaseRepository purchaseRepDouble;
	private CustomerService customerSDouble;
	private ProductService productSDouble;
	private NotificationService notificationSDouble;
	private PurchaseService purchaseService;

	@BeforeEach
	public void mockActors() {
		purchaseRepDouble = mock(PurchaseRepository.class);
		customerSDouble = mock(CustomerService.class);
		productSDouble = mock(ProductService.class);
		notificationSDouble = mock(NotificationService.class);

		purchaseService = new PurchaseService(purchaseRepDouble, customerSDouble, productSDouble, notificationSDouble);
	}

	@Test
	public void succesfulPurchase() throws ShopException {
		// Given
		Request request = new Request(6, 1, 10);

		// Always return cost when id is given
		when(productSDouble.getProductCost(request.getIdProduct())).thenReturn(request.getProductCost());

		// When
		purchaseService.createPurchase(request.getIdCustomer(), request.getIdProduct());

		// Then
		verify(productSDouble, times(1)).getProductCost(request.getIdProduct());
		verify(productSDouble, times(1)).withdrawProduct(request.getIdProduct());
		verify(customerSDouble, times(1)).reserveCredit(request.getIdCustomer(), request.getProductCost());
		verify(notificationSDouble, times(1)).notify(request.getMessage());
		verify(purchaseRepDouble, times(1)).save(any());
	}

	@Test
	public void noCreditPurchase() throws ShopException {
		// Given
		Request request = new Request(5, 3, 25);

		// Always return cost when id is given
		when(productSDouble.getProductCost(request.getIdProduct())).thenReturn(request.getProductCost());

		// Throw CustomerCreditLimitExceededException when trying to reserve credit for
		// a purchase and it is not enough
		doThrow(new CustomerCreditLimitExceededException()).when(customerSDouble).reserveCredit(request.getIdCustomer(),
				request.getProductCost());

		// When
		Assertions.assertThrows(CustomerCreditLimitExceededException.class,
				() -> purchaseService.createPurchase(request.getIdCustomer(), request.getIdProduct()));

		// Then
		verify(productSDouble, times(1)).getProductCost(request.getIdProduct());
		verify(productSDouble, times(1)).withdrawProduct(request.getIdProduct());
		verify(customerSDouble, times(1)).reserveCredit(request.getIdCustomer(), request.getProductCost());
		verify(notificationSDouble, times(0)).notify(request.getMessage());
		verify(purchaseRepDouble, times(0)).save(any());
	}

	@Test
	public void noStockPurchase() throws ShopException {
		// Given
		Request request = new Request(4, 2, 30);

		// Always return cost when id is given
		when(productSDouble.getProductCost(request.getIdProduct())).thenReturn(request.getProductCost());

		// Throw ProductStockWithdrawExceededException when trying to withdraw a product
		// without stock
		doThrow(new ProductStockWithdrawExceededException()).when(productSDouble)
				.withdrawProduct(request.getIdProduct());

		// When
		Assertions.assertThrows(ProductStockWithdrawExceededException.class,
				() -> purchaseService.createPurchase(request.getIdCustomer(), request.getIdProduct()));

		// Then
		verify(productSDouble, times(1)).getProductCost(request.getIdProduct());
		verify(productSDouble, times(1)).withdrawProduct(request.getIdProduct());
		verify(customerSDouble, times(0)).reserveCredit(request.getIdCustomer(), request.getProductCost());
		verify(notificationSDouble, times(0)).notify(request.getMessage());
		verify(purchaseRepDouble, times(0)).save(any());
	}

}
