package com.escalab.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaCategoria;
import com.escalab.repo.IConsultaCategoriaRepo;

@ExtendWith(MockitoExtension.class)
class ConsultaCategoriaServiceImplTest {

	@Mock
	private IConsultaCategoriaRepo repo;

	@InjectMocks
	private ConsultaCategoriaServiceImpl service;

	@Test
	void listarCategoriaPorId() {
		List<ConsultaCategoria> list = List.of(new ConsultaCategoria());
		when(repo.listarCategoriaPorId(5)).thenReturn(list);
		assertEquals(list, service.listarCategoriaPorId(5));
	}

	@Test
	void registrarConsultaCategoria() {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setIdConsulta(1);
		f.setIdCategoria(2);
		when(repo.registrarConsultaCategoria(1, 2)).thenReturn(7);
		assertEquals(7, service.registrarConsultaCategoria(f));
	}
}
