package es.codeurjc.testing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import es.codeurjc.shop.Application;
import es.codeurjc.shop.domain.ShopException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SystemTestsWeb {

	// List of navigator drivers needed for this type of tests
	private List<WebDriver> drivers = new ArrayList<>();

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

	@Test
	public void succesfulPurchase() throws ShopException {
		// Given
		Request request = new Request(6, 3, "Successful purchase");

		// When
		drivers.get(0).get("http://localhost:8080"); // Browse main application template

		drivers.get(0).findElement(By.id("product-" + request.getIdProduct())).click(); // Click on product

		// Type in buying customer ID
		drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(request.getIdCustomer()));
		drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // Click purchase button

		String msg = drivers.get(0).findElement(By.id("message")).getText(); // Save response message

		// Then
		assertThat(msg).isEqualTo(request.getMessage()); // Compare given message with expected
	}

	@Test
	public void noCreditPurchase() throws ShopException {
		// Given
		Request request = new Request(5, 3, "Error: CustomerCreditLimitExceededException");

		// When
		drivers.get(0).get("http://localhost:8080"); // Browse main application template

		drivers.get(0).findElement(By.id("product-" + request.getIdProduct())).click(); // Click on product

		// Type in buying customer ID
		drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(request.getIdCustomer()));
		drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // Click purchase button

		String msg = drivers.get(0).findElement(By.id("message")).getText(); // Save response message

		// Then
		assertThat(msg).isEqualTo(request.getMessage()); // Compare given message with expected
	}

	@Test
	public void noStockPurchase() throws ShopException {
		// Given
		Request requestSuccess = new Request(6, 1, "Successful purchase");
		Request requestFail = new Request(4, 1, "Error: ProductStockWithdrawExceededException");
		drivers.add(new ChromeDriver()); // We need another customer to buy last milk brick for forcing the exception

		// When
		drivers.get(0).get("http://localhost:8080"); // Browse main application template
		drivers.get(1).get("http://localhost:8080");

		drivers.get(0).findElement(By.id("product-" + requestSuccess.getIdProduct())).click(); // Click on product
		drivers.get(1).findElement(By.id("product-" + requestFail.getIdProduct())).click();

		// Type customer ID
		drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(requestSuccess.getIdCustomer()));
		drivers.get(1).findElement(By.id("customer-id")).sendKeys(String.valueOf(requestFail.getIdCustomer()));

		drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // Click purchase button
		drivers.get(1).findElement(By.xpath("//input[@value='Purchase']")).click();

		// Finally take messages
		String msg1 = drivers.get(0).findElement(By.id("message")).getText();
		String msg2 = drivers.get(1).findElement(By.id("message")).getText();

		// Then
		assertThat(msg1).isEqualTo(requestSuccess.getMessage());
		assertThat(msg2).isEqualTo(requestFail.getMessage());
	}

}
