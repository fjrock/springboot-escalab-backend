package com.escalab.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.escalab.model.Persona;
import com.escalab.repo.IPersonaRepo;

@ExtendWith(MockitoExtension.class)
class PersonaServiceImplTest {

	@Mock
	private IPersonaRepo repo;

	@InjectMocks
	private PersonaServiceImpl service;

	@Test
	void registrar() {
		Persona p = new Persona();
		when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
		assertEquals(p, service.registrar(p));
	}

	@Test
	void modificar() {
		Persona p = new Persona();
		when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
		assertEquals(p, service.modificar(p));
	}

	@Test
	void leerPorId_present() {
		Persona p = new Persona();
		p.setIdPersona(1);
		when(repo.findById(1)).thenReturn(Optional.of(p));
		assertEquals(1, service.leerPorId(1).getIdPersona());
	}

	@Test
	void leerPorId_empty() {
		when(repo.findById(2)).thenReturn(Optional.empty());
		assertNull(service.leerPorId(2).getIdPersona());
	}

	@Test
	void eliminar() {
		assertTrue(service.eliminar(3));
		verify(repo).deleteById(3);
	}
}
