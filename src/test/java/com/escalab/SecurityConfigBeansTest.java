package com.escalab;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootTest
class SecurityConfigBeansTest {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private JwtDecoder jwtDecoder;

	@Autowired
	private SecurityFilterChain securityFilterChain;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Test
	void beansLoad() {
		assertNotNull(passwordEncoder);
		assertNotNull(jwtEncoder);
		assertNotNull(jwtDecoder);
		assertNotNull(securityFilterChain);
		assertNotNull(authenticationManager);
	}
}
