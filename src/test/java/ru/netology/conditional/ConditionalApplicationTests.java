package ru.netology.conditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	private static final GenericContainer<?> devApp = new GenericContainer<>("devapp")
			.withExposedPorts(8080);
	private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
			.withExposedPorts(8081);

	@BeforeAll
	public static void setUp() {
		devApp.start();
		prodApp.start();
	}

	@ParameterizedTest
	@CsvSource({"8080, dev"})
	void testDevApp(int port, String profile) {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(port) + "/profile", String.class);
		System.out.println(entity.getBody());
		Assertions.assertEquals("Current profile is " + profile, entity.getBody());
	}

	@ParameterizedTest
	@CsvSource({"8081, production"})
	void testProdApp(int port, String profile) {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(port) + "/profile", String.class);
		System.out.println(entity.getBody());
		Assertions.assertEquals("Current profile is " + profile, entity.getBody());
	}

}
