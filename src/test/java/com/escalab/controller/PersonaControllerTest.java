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
import com.escalab.model.Persona;
import com.escalab.service.IPersonaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PersonaController.class)
@Import({ SecurityConfig.class, AuthException.class })
class PersonaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IPersonaService personaService;

	@Test
	@WithMockUser(authorities = "USER")
	void get_ok() throws Exception {
		Persona p = new Persona();
		p.setIdPersona(1);
		when(personaService.leerPorId(1)).thenReturn(p);
		mockMvc.perform(get("/persona/1")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void get_notFound() throws Exception {
		Persona p = new Persona();
		when(personaService.leerPorId(2)).thenReturn(p);
		mockMvc.perform(get("/persona/2")).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void delete_notFound() throws Exception {
		when(personaService.leerPorId(99)).thenReturn(new Persona());
		mockMvc.perform(delete("/persona/99")).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void delete_ok() throws Exception {
		Persona p = new Persona();
		p.setIdPersona(1);
		when(personaService.leerPorId(1)).thenReturn(p);
		mockMvc.perform(delete("/persona/1")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void post_created() throws Exception {
		Persona saved = new Persona();
		saved.setIdPersona(5);
		when(personaService.registrar(any())).thenReturn(saved);
		Persona body = buildValidPersona();
		mockMvc.perform(post("/persona").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void put_ok() throws Exception {
		Persona body = buildValidPersona();
		body.setIdPersona(1);
		when(personaService.modificar(any())).thenReturn(body);
		mockMvc.perform(put("/persona").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isOk());
	}

	private static Persona buildValidPersona() {
		Persona p = new Persona();
		p.setNombre("n");
		p.setApellidoPaterno("a");
		p.setApellidoMaterno("b");
		p.setRun("12345678");
		p.setDv("9");
		p.setTelefono("123456789012");
		p.setTipoPersona("NATURAL");
		p.setEmail("a@b.co");
		return p;
	}
}
