package com.escalab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.swagger.v3.oas.models.OpenAPI;

@SpringBootTest
class SwaggerConfigTest {

	@Autowired
	private OpenAPI openAPI;

	@Test
	void openApiInfo() {
		assertEquals("Ofrecelo API Documentation", openAPI.getInfo().getTitle());
		assertEquals("1.0", openAPI.getInfo().getVersion());
	}
}
