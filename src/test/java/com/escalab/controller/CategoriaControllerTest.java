package com.escalab.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.escalab.model.Categoria;
import com.escalab.service.ICategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoriaController.class)
@Import({ SecurityConfig.class, AuthException.class })
class CategoriaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ICategoriaService categoriaService;

	@Test
	@WithMockUser(authorities = "USER")
	void get_ok() throws Exception {
		Categoria c = new Categoria();
		c.setIdCategoria(1);
		when(categoriaService.leerPorId(1)).thenReturn(c);
		mockMvc.perform(get("/categoria/1")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void get_notFound() throws Exception {
		when(categoriaService.leerPorId(2)).thenReturn(new Categoria());
		mockMvc.perform(get("/categoria/2")).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void delete_notFound() throws Exception {
		when(categoriaService.leerPorId(99)).thenReturn(new Categoria());
		mockMvc.perform(delete("/categoria/99")).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void delete_ok() throws Exception {
		Categoria c = new Categoria();
		c.setIdCategoria(1);
		when(categoriaService.leerPorId(1)).thenReturn(c);
		mockMvc.perform(delete("/categoria/1")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void post_created() throws Exception {
		Categoria saved = new Categoria();
		saved.setIdCategoria(4);
		when(categoriaService.registrar(any())).thenReturn(saved);
		Categoria body = new Categoria();
		body.setNombre("cat");
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void put_ok() throws Exception {
		Categoria body = new Categoria();
		body.setIdCategoria(1);
		body.setNombre("cat");
		when(categoriaService.modificar(any())).thenReturn(body);
		mockMvc.perform(put("/categoria").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isOk());
	}
}
