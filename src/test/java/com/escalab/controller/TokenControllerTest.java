package com.escalab.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.escalab.AuthException;
import com.escalab.SecurityConfig;

@WebMvcTest(TokenController.class)
@Import({ SecurityConfig.class, AuthException.class })
class TokenControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(authorities = "ADMIN")
	void revocarToken() throws Exception {
		mockMvc.perform(get("/tokens/anular/abc.def")).andExpect(status().isOk())
				.andExpect(content().string("La revocacion server-side no aplica con JWT stateless en esta version"));
	}
}
