package com.escalab.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.Consulta;
import com.escalab.service.IConsultaService;

import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Cubre la rama {@code filtro == null} en ConsultaController (no alcanzable vía HTTP con
 * {@code @RequestBody} obligatorio).
 */
class ConsultaControllerNullFiltroTest {

	@Test
	void buscarTodoPorRun_nullFiltro() {
		IConsultaService mockSvc = mock(IConsultaService.class);
		ConsultaController c = new ConsultaController();
		ReflectionTestUtils.setField(c, "IConsultaService", mockSvc);
		ResponseEntity<List<Consulta>> r = c.buscarTodoPorRun(null);
		assertTrue(r.getBody().isEmpty());
	}

	@Test
	void buscarTodoPorCategoria_nullFiltro() {
		IConsultaService mockSvc = mock(IConsultaService.class);
		ConsultaController c = new ConsultaController();
		ReflectionTestUtils.setField(c, "IConsultaService", mockSvc);
		ResponseEntity<List<Consulta>> r = c.buscarTodoPorCategoria(null);
		assertTrue(r.getBody().isEmpty());
	}

	@Test
	void buscarTodoPorProducto_nullFiltro() {
		IConsultaService mockSvc = mock(IConsultaService.class);
		ConsultaController c = new ConsultaController();
		ReflectionTestUtils.setField(c, "IConsultaService", mockSvc);
		ResponseEntity<List<Consulta>> r = c.buscarTodoPorProducto(null);
		assertTrue(r.getBody().isEmpty());
	}
}
