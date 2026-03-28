package com.escalab.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.escalab.AuthException;
import com.escalab.SecurityConfig;

@WebMvcTest(OAuthTokenController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({ SecurityConfig.class, AuthException.class })
class OAuthTokenControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthenticationManager authenticationManager;

	private static String basic(String user, String pass) {
		return "Basic " + Base64.getEncoder().encodeToString((user + ":" + pass).getBytes(StandardCharsets.UTF_8));
	}

	@Test
	void token_success() throws Exception {
		when(authenticationManager.authenticate(any()))
				.thenReturn(new UsernamePasswordAuthenticationToken("u", "p", AuthorityUtils.createAuthorityList("USER")));
		mockMvc.perform(post("/oauth/token").param("grant_type", "password").param("username", "u").param("password", "p")
				.header("Authorization", basic("testclient", "testsecret"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.token_type").value("Bearer")).andExpect(jsonPath("$.access_token").exists());
	}

	@Test
	void unsupported_grant_type() throws Exception {
		mockMvc.perform(post("/oauth/token").param("grant_type", "client_credentials").param("username", "u")
				.param("password", "p").header("Authorization", basic("testclient", "testsecret")))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").value("unsupported_grant_type"));
	}

	@Test
	void invalid_client_missingAuth() throws Exception {
		mockMvc.perform(post("/oauth/token").param("grant_type", "password").param("username", "u").param("password", "p"))
				.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.error").value("invalid_client"));
	}

	@Test
	void invalid_client_notBasicScheme() throws Exception {
		mockMvc.perform(post("/oauth/token").param("grant_type", "password").param("username", "u").param("password", "p")
				.header("Authorization", "Bearer sometoken")).andExpect(status().isUnauthorized());
	}

	@Test
	void invalid_client_wrongSecret() throws Exception {
		mockMvc.perform(post("/oauth/token").param("grant_type", "password").param("username", "u").param("password", "p")
				.header("Authorization", basic("testclient", "wrong"))).andExpect(status().isUnauthorized());
	}

	@Test
	void invalid_client_wrongClientId() throws Exception {
		mockMvc.perform(post("/oauth/token").param("grant_type", "password").param("username", "u").param("password", "p")
				.header("Authorization", basic("otherclient", "testsecret"))).andExpect(status().isUnauthorized());
	}

	@Test
	void invalid_base64() throws Exception {
		mockMvc.perform(post("/oauth/token").param("grant_type", "password").param("username", "u").param("password", "p")
				.header("Authorization", "Basic !!!not-valid-base64!!!")).andExpect(status().isUnauthorized());
	}

	@Test
	void invalid_client_decodedWithoutColon() throws Exception {
		String noColon = Base64.getEncoder().encodeToString("soloUsuario".getBytes(StandardCharsets.UTF_8));
		mockMvc.perform(post("/oauth/token").param("grant_type", "password").param("username", "u").param("password", "p")
				.header("Authorization", "Basic " + noColon)).andExpect(status().isUnauthorized());
	}

	@Test
	void not_authenticated_branch() throws Exception {
		Authentication auth = mock(Authentication.class);
		when(auth.isAuthenticated()).thenReturn(false);
		when(authenticationManager.authenticate(any())).thenReturn(auth);
		mockMvc.perform(post("/oauth/token").param("grant_type", "password").param("username", "u").param("password", "p")
				.header("Authorization", basic("testclient", "testsecret"))).andExpect(status().is5xxServerError());
	}
}
