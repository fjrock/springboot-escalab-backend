package com.escalab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI api() {
		return new OpenAPI().info(new Info()
				.title("Ofrecelo API Documentation")
				.description("Ofrecelo API busca generar intercambio de productos de manera rapida y segura")
				.version("1.0")
				.contact(new Contact().name("FJROCK").url("https://www.fjrock.cl").email("support@fjrock.cl")));
	}
}

