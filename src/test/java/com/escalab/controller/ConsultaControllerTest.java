package com.escalab.controller;

import static org.mockito.Mockito.when;
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
import com.escalab.model.Consulta;
import com.escalab.service.IConsultaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ConsultaController.class)
@Import({ SecurityConfig.class, AuthException.class })
class ConsultaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IConsultaService consultaService;

	@Test
	@WithMockUser(authorities = "USER")
	void buscarTodoPorRun() throws Exception {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setRun("1-9");
		when(consultaService.buscarTodoPorRun(f)).thenReturn(List.of(new Consulta()));
		mockMvc.perform(post("/consulta/buscartodoporrun").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(f))).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void buscarTodoPorCategoria() throws Exception {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setNombre("c");
		when(consultaService.buscarTodoPorCategoria(f)).thenReturn(List.of());
		mockMvc.perform(post("/consulta/buscartodoporcategoria").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(f))).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void buscarTodoPorProducto() throws Exception {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setNombre("p");
		when(consultaService.buscarTodoPorProducto(f)).thenReturn(List.of());
		mockMvc.perform(post("/consulta/buscartodoporproducto").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(f))).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void registrarConsulta() throws Exception {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setIdCategoria(1);
		f.setIdPersona(2);
		f.setIdProducto(3);
		when(consultaService.registrarConsulta(f)).thenReturn(10);
		mockMvc.perform(post("/consulta/registrarconsulta").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(f))).andExpect(status().isOk());
	}
}
