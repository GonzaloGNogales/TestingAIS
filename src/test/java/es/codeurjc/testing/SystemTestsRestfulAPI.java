package es.codeurjc.testing;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import es.codeurjc.shop.Application;
import io.restassured.http.ContentType;

public class SystemTestsRestfulAPI {

	@BeforeAll
	public static void setupClass() {
		Application.start();
	}

	@AfterAll
	public static void teardownClass() {
		Application.stop();
	}

	@Test
	public void succesfulPurchase() {
		given()
			.request()
				.body("{ \"customerId\" : \"" + 6 + "\", \"productId\" : " + 1 + " }")
				.contentType(ContentType.JSON).
		when()
			.post("http://localhost:8080/api/purchases/").
		then()
			.statusCode(200)
			.body("id", equalTo(7));
	}
	
	@Test
	public void noCreditPurchase() {
		given()
			.request()
				.body("{ \"customerId\" : \"" + 5 + "\", \"productId\" : " + 3 + " }")
				.contentType(ContentType.JSON).
		when()
			.post("http://localhost:8080/api/purchases/").
		then()
			.statusCode(400)
			.body("message", equalTo("CustomerCreditLimitExceededException"));
	}
	
	@Test
	public void noStockPurchase() {
		given()
			.request()
				.body("{ \"customerId\" : \"" + 4 + "\", \"productId\" : " + 2 + " }")
				.contentType(ContentType.JSON).
		when()
			.post("http://localhost:8080/api/purchases/").
		then()
			.statusCode(400)
			.body("message", equalTo("ProductStockWithdrawExceededException"));
	}
	
}
