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
import com.escalab.model.ConsultaProducto;
import com.escalab.repo.IConsultaProductoRepo;

@ExtendWith(MockitoExtension.class)
class ConsultaProductoServiceImplTest {

	@Mock
	private IConsultaProductoRepo repo;

	@InjectMocks
	private ConsultaProductoServiceImpl service;

	@Test
	void listarProductoPorId() {
		List<ConsultaProducto> list = List.of(new ConsultaProducto());
		when(repo.listarProductoPorId(3)).thenReturn(list);
		assertEquals(list, service.listarProductoPorId(3));
	}

	@Test
	void registrarConsultaProducto() {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setIdConsulta(1);
		f.setIdProducto(2);
		when(repo.registrarConsultaProducto(1, 2)).thenReturn(8);
		assertEquals(8, service.registrarConsultaProducto(f));
	}
}
