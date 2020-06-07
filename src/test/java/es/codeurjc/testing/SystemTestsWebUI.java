package es.codeurjc.testing;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import es.codeurjc.shop.Application;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SystemTestsWebUI {

	private List<WebDriver> drivers = new ArrayList<>();

	public static Collection<Object[]> values() {

		// Prepare request batches for giving the examples to the parameterized test
		List<Request> requestBatch1 = new ArrayList<>();
		List<Request> requestBatch2 = new ArrayList<>();
		List<Request> requestBatch3 = new ArrayList<>();

		requestBatch1.add(new Request(6, 3, "Successful purchase")); // Correct example
		requestBatch2.add(new Request(5, 3, "Error: CustomerCreditLimitExceededException")); // Not enough money example
		requestBatch3.add(new Request(6, 1, "Successful purchase"));
		requestBatch3.add(new Request(4, 1, "Error: ProductStockWithdrawExceededException")); // Not enough stock example

		Object[][] values = { { requestBatch1 }, { requestBatch2 }, { requestBatch3 } };

		return Arrays.asList(values);
	}

	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
		Application.start();
	}

	@AfterAll
	public static void teardownClass() {
		Application.stop();
	}

	@BeforeEach
	public void setupTest() {
		drivers.add(new ChromeDriver());
	}

	@AfterEach
	public void teardown() {
		for (WebDriver driver : drivers) {
			if (driver != null) {
				driver.quit();
			}
		}
		drivers.clear();
	}

	@ParameterizedTest
	@MethodSource("values")
	public void systemTest(List<Request> requests) {

		if (requests.get(0).getIdProduct() != 1) { // Check product ID for choosing cases

			drivers.get(0).get("http://localhost:8080"); // Browse main application template

			drivers.get(0).findElement(By.id("product-" + requests.get(0).getIdProduct())).click(); // Click on product

			// Type in buying customer ID
			drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(requests.get(0).getIdCustomer()));
			drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // Click purchase button

			String msg = drivers.get(0).findElement(By.id("message")).getText(); // Save response message
			assertThat(msg).isEqualTo(requests.get(0).getMessage()); // Compare given message with expected

		} else {

			drivers.add(new ChromeDriver()); // We need another customer to buy last milk brick for forcing the exception

			drivers.get(0).get("http://localhost:8080"); // Browse main application template
			drivers.get(1).get("http://localhost:8080");

			drivers.get(0).findElement(By.id("product-" + requests.get(0).getIdProduct())).click(); // Click on product
			drivers.get(1).findElement(By.id("product-" + requests.get(1).getIdProduct())).click();

			// Type customer ID
			drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(requests.get(0).getIdCustomer()));
			drivers.get(1).findElement(By.id("customer-id")).sendKeys(String.valueOf(requests.get(1).getIdCustomer()));

			drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // Click purchase button
			drivers.get(1).findElement(By.xpath("//input[@value='Purchase']")).click();

			// Finally compare messages
			String msg1 = drivers.get(0).findElement(By.id("message")).getText();
			assertThat(msg1).isEqualTo(requests.get(0).getMessage());
			String msg2 = drivers.get(1).findElement(By.id("message")).getText();
			assertThat(msg2).isEqualTo(requests.get(1).getMessage());

		}

	}

}
