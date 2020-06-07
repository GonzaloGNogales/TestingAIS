package es.codeurjc.testing;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import es.codeurjc.shop.Application;

public class SystemTestsRestfulAPI {

	public static Collection<Object[]> values() {

		Object[][] values = { { new Request(6, 1, 200, "id", 7, null, null) }, // Correct 200 OK

				{ new Request(5, 3, 400, null, 0, "message", "CustomerCreditLimitExceededException") }, // Not enough
																										// credit 400
																										// Bad Request
				{ new Request(4, 2, 400, null, 0, "message", "ProductStockWithdrawExceededException") } // Not enough
																										// stock 400 Bad
																										// Request
		};

		return Arrays.asList(values);

	}

	@BeforeAll
	public static void setupClass() {
		Application.start();
	}

	@AfterAll
	public static void teardownClass() {
		Application.stop();
	}

	@ParameterizedTest
	@MethodSource("values")
	public void systemAPITest(Request requests) {

	}

}
