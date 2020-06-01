package es.codeurjc.testing;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import es.codeurjc.shop.domain.ShopException;

public class UnitTests {

	public static Collection<Object[]> values() {
		Object[][] values = {};

		return Arrays.asList(values);
	}

	@ParameterizedTest(name = "{index}: {0}+{1}= {2}")
	@MethodSource("values")
	public void generico() throws ShopException {

	}
}
