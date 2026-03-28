package com.escalab;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class SpringbootEscalabBackendApplicationMainTest {

	@Test
	void mainStartsSpringApplication() {
		ConfigurableApplicationContext ctx = mock(ConfigurableApplicationContext.class);
		try (MockedStatic<SpringApplication> spring = mockStatic(SpringApplication.class)) {
			spring.when(() -> SpringApplication.run(any(Class.class), any(String[].class))).thenReturn(ctx);
			SpringbootEscalabBackendApplication.main(new String[] {});
		}
	}
}
