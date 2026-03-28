package com.escalab.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.escalab.AuthException;
import com.escalab.SecurityConfig;
import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaCategoria;
import com.escalab.service.IConsultaCategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ConsultaCategoriaController.class)
@Import({ SecurityConfig.class, AuthException.class })
class ConsultaCategoriaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IConsultaCategoriaService service;

	@Test
	@WithMockUser(authorities = "USER")
	void listar() throws Exception {
		when(service.listarCategoriaPorId(1)).thenReturn(List.of(new ConsultaCategoria()));
		mockMvc.perform(get("/consultacategoria/1")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void registrar() throws Exception {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setIdConsulta(1);
		f.setIdCategoria(2);
		when(service.registrarConsultaCategoria(f)).thenReturn(5);
		mockMvc.perform(post("/consultacategoria/registrar").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(f))).andExpect(status().isOk());
	}
}
