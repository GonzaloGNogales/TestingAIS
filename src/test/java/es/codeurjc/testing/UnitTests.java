package es.codeurjc.testing;

import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.customer.CustomerCreditLimitExceededException;
import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.ProductService;
import es.codeurjc.shop.domain.product.ProductStockWithdrawExceededException;
import es.codeurjc.shop.domain.purchase.PurchaseRepository;
import es.codeurjc.shop.domain.purchase.PurchaseService;
import es.codeurjc.shop.notification.NotificationService;

public class UnitTests {

	public static Collection<Object[]> values() {
		Object[][] values = { { new Request(6, 1, 10, 1, 1, 1, 1, 1) }, // Correct
				{ new Request(5, 3, 25, 1, 1, 1, 0, 0) }, // Not enough credit
				{ new Request(4, 2, 30, 1, 1, 0, 0, 0) } // Not enough stock
		};

		return Arrays.asList(values);
	}

	@DisplayName("Purchase Service Tests")
	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("values")
	public void purchaseServiceTest(Request request) throws ShopException {

		// Given
		PurchaseRepository purchaseRepDouble = mock(PurchaseRepository.class);
		CustomerService customerSDouble = mock(CustomerService.class);
		ProductService productSDouble = mock(ProductService.class);
		NotificationService notificationSDouble = mock(NotificationService.class);

		PurchaseService purchaseService = new PurchaseService(purchaseRepDouble, customerSDouble, productSDouble,
				notificationSDouble);

		// Mocking getProductCost - withdrawProduct - reserveCredit - save

		// Always return cost when id is given
		when(productSDouble.getProductCost(request.getIdProduct())).thenReturn(request.getProductCost());

		// Throw ProductStockWithdrawExceededException when trying to withdraw a product
		// without stock
		if (request.getIdProduct() == 2)
			doThrow(new ProductStockWithdrawExceededException()).when(productSDouble)
					.withdrawProduct(request.getIdProduct());
		
		// Throw CustomerCreditLimitExceededException when trying to reserve credit for
		// a purchase and it is not enough
		else if (request.getIdCustomer() == 5)
			doThrow(new CustomerCreditLimitExceededException()).when(customerSDouble)
					.reserveCredit(request.getIdCustomer(), request.getProductCost());

		when(purchaseRepDouble.save(any())).thenReturn(null);

		// When
		if (request.getIdProduct() == 2) {

			ProductStockWithdrawExceededException notEnoughStockException = Assertions.assertThrows(
					ProductStockWithdrawExceededException.class,
					() -> purchaseService.createPurchase(request.getIdCustomer(), request.getIdProduct()));

		} else if (request.getIdCustomer() == 5) {

			CustomerCreditLimitExceededException creditLimitExceededException = Assertions.assertThrows(
					CustomerCreditLimitExceededException.class,
					() -> purchaseService.createPurchase(request.getIdCustomer(), request.getIdProduct()));

		} else
			purchaseService.createPurchase(request.getIdCustomer(), request.getIdProduct());

		// Then
		verify(productSDouble, times(request.getGetProductCostValue())).getProductCost(request.getIdProduct());
		verify(productSDouble, times(request.getWithdrawProductValue())).withdrawProduct(request.getIdProduct());
		verify(customerSDouble, times(request.getReserveCreditValue())).reserveCredit(request.getIdCustomer(),
				request.getProductCost());
		verify(notificationSDouble, times(request.getNotifyValue())).notify(request.getMessage());
		verify(purchaseRepDouble, times(request.getSaveValue())).save(any());

	}
}
