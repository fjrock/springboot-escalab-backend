package com.escalab.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.escalab.model.Producto;
import com.escalab.service.IProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductoController.class)
@Import({ SecurityConfig.class, AuthException.class })
class ProductoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IProductoService productoService;

	@Test
	@WithMockUser(authorities = "USER")
	void get_ok() throws Exception {
		Producto p = new Producto();
		p.setIdProducto(1);
		when(productoService.leerPorId(1)).thenReturn(p);
		mockMvc.perform(get("/producto/1")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void get_notFound() throws Exception {
		when(productoService.leerPorId(2)).thenReturn(new Producto());
		mockMvc.perform(get("/producto/2")).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void delete_notFound() throws Exception {
		when(productoService.leerPorId(99)).thenReturn(new Producto());
		mockMvc.perform(delete("/producto/99")).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void delete_ok() throws Exception {
		Producto p = new Producto();
		p.setIdProducto(1);
		when(productoService.leerPorId(1)).thenReturn(p);
		mockMvc.perform(delete("/producto/1")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void post_created() throws Exception {
		Producto saved = new Producto();
		saved.setIdProducto(3);
		when(productoService.registrar(any())).thenReturn(saved);
		Producto body = new Producto();
		body.setNombre("x");
		body.setStock(1);
		body.setFechaCreacion(LocalDateTime.now());
		mockMvc.perform(post("/producto").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void put_ok() throws Exception {
		Producto body = new Producto();
		body.setIdProducto(1);
		body.setNombre("x");
		body.setStock(2);
		when(productoService.modificar(any())).thenReturn(body);
		mockMvc.perform(put("/producto").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isOk());
	}
}
