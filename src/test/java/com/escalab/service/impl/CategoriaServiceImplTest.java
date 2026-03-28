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

import com.escalab.model.Categoria;
import com.escalab.repo.ICategoriaRepo;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

	@Mock
	private ICategoriaRepo repo;

	@InjectMocks
	private CategoriaServiceImpl service;

	@Test
	void registrar() {
		Categoria c = new Categoria();
		when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
		assertEquals(c, service.registrar(c));
	}

	@Test
	void modificar() {
		Categoria c = new Categoria();
		when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
		assertEquals(c, service.modificar(c));
	}

	@Test
	void leerPorId_present() {
		Categoria c = new Categoria();
		c.setIdCategoria(1);
		when(repo.findById(1)).thenReturn(Optional.of(c));
		assertEquals(1, service.leerPorId(1).getIdCategoria());
	}

	@Test
	void leerPorId_empty() {
		when(repo.findById(2)).thenReturn(Optional.empty());
		assertNull(service.leerPorId(2).getIdCategoria());
	}

	@Test
	void eliminar() {
		assertTrue(service.eliminar(3));
		verify(repo).deleteById(3);
	}
}
