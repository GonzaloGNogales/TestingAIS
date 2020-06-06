package es.codeurjc.testing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
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
		Object[][] values = { { new Request(6, 3, 30, 1, 1, 1, 1, 1, "Successful purchase") }, // Correct
				{ new Request(5, 2, 25, 1, 1, 1, 1, 1, "Error: CustomerCreditLimitExceededException") }, // Not enough credit
				{ new Request(6, 1, 10, 1, 1, 1, 1, 1, "Successful purchase"),
				  new Request(4, 1, 10, 1, 1, 1, 1, 1, "Error: ProductStockWithdrawExceededException")} // Not enough stock
		};

		return Arrays.asList(values);
	}

	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
		Application.start();
	}

	@AfterClass
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
	
	public void init() {
		for (WebDriver driver : drivers) {
			driver.get("http://localhost:8080");
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("values")
	public void systemTest(List<Request> requests) throws InterruptedException {

		if (requests.get(0).getIdProduct() != 1) {
			
			init();
			
			drivers.get(0).findElement(By.id("product-" + requests.get(0).getIdProduct())).click();
			drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(requests.get(0).getIdCustomer()));
			drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click();
			
			String msg = drivers.get(0).findElement(By.id("message")).getText(); 
			assertThat(msg).isEqualTo(requests.get(0).getMessage());
			
		} else {
			
			drivers.add(new ChromeDriver());
			
			init();
			
			drivers.get(0).findElement(By.id("product-" + requests.get(0).getIdProduct())).click();
			drivers.get(1).findElement(By.id("product-" + requests.get(1).getIdProduct())).click();
			drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(requests.get(0).getIdCustomer()));																								// cliente
			drivers.get(1).findElement(By.id("customer-id")).sendKeys(String.valueOf(requests.get(1).getIdCustomer())); 
			drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click();
			drivers.get(1).findElement(By.xpath("//input[@value='Purchase']")).click();
			
			String msg1 = drivers.get(0).findElement(By.id("message")).getText();
			assertThat(msg1).isEqualTo(requests.get(0).getMessage());
			String msg2 = drivers.get(1).findElement(By.id("message")).getText();
			assertThat(msg2).isEqualTo(requests.get(1).getMessage());

		}

	}

}
