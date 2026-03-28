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
import com.escalab.model.ConsultaProducto;
import com.escalab.service.IConsultaProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ConsultaProductoController.class)
@Import({ SecurityConfig.class, AuthException.class })
class ConsultaProductoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IConsultaProductoService service;

	@Test
	@WithMockUser(authorities = "USER")
	void listar() throws Exception {
		when(service.listarProductoPorId(2)).thenReturn(List.of(new ConsultaProducto()));
		mockMvc.perform(get("/consultaproducto/2")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void registrar() throws Exception {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setIdConsulta(1);
		f.setIdProducto(2);
		when(service.registrarConsultaProducto(f)).thenReturn(6);
		mockMvc.perform(post("/consultaproducto/registrar").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(f))).andExpect(status().isOk());
	}
}
