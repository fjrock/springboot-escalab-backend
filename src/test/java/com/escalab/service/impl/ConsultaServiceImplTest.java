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
import com.escalab.model.Consulta;
import com.escalab.repo.IConsultaRepo;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceImplTest {

	@Mock
	private IConsultaRepo repo;

	@InjectMocks
	private ConsultaServiceImpl service;

	@Test
	void buscarTodoPorRun() {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setRun("1-9");
		List<Consulta> list = List.of(new Consulta());
		when(repo.buscarTodoPorRun("1-9")).thenReturn(list);
		assertEquals(list, service.buscarTodoPorRun(f));
	}

	@Test
	void buscarTodoPorCategoria() {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setNombre("cat");
		List<Consulta> list = List.of(new Consulta());
		when(repo.buscarTodoPorCategoria("cat")).thenReturn(list);
		assertEquals(list, service.buscarTodoPorCategoria(f));
	}

	@Test
	void buscarTodoPorProducto() {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setNombre("prod");
		List<Consulta> list = List.of(new Consulta());
		when(repo.buscarTodoPorProducto("prod")).thenReturn(list);
		assertEquals(list, service.buscarTodoPorProducto(f));
	}

	@Test
	void registrarConsulta() {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setIdCategoria(1);
		f.setIdPersona(2);
		f.setIdProducto(3);
		when(repo.registrarConsulta(1, 2, 3)).thenReturn(99);
		assertEquals(99, service.registrarConsulta(f));
	}
}
