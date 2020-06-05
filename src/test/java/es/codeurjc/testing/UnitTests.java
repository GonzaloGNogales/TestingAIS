package es.codeurjc.testing;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.ProductService;
import es.codeurjc.shop.notification.NotificationService;

public class UnitTests {

	public static Collection<Object[]> values() {
		List<Request> case1 = new ArrayList<>();
		List<Request> case2 = new ArrayList<>();
		List<Request> case3 = new ArrayList<>();
		
		// Correct
		case1.add(new Request(1, 1, 5000, 1, 1, 1, 1, 1));
		
		// Not enough credit
		case2.add(new Request(1, 1, 20, 1, 1, 1, 0, 0));
		
		// Not enough stock
		case3.add(new Request(1, 1, 2500, 1, 1, 0, 0, 0));
		
		Object[][] values = {{case1}, {case2}, {case3}};

		return Arrays.asList(values);
	}

	@ParameterizedTest(name = "{index}: {0}+{1}= {2}")
	@MethodSource("values")
	public void generico() throws ShopException {
		// Given

		CustomerService customerSDouble = mock(CustomerService.class);
		ProductService productSDouble = mock(ProductService.class);
		NotificationService notificationSDouble = mock(NotificationService.class);
		
		
		
		
		
	}
}
