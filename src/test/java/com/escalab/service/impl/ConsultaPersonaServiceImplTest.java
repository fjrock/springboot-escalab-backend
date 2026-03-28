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
import com.escalab.model.ConsultaPersona;
import com.escalab.repo.IConsultaPersonaRepo;

@ExtendWith(MockitoExtension.class)
class ConsultaPersonaServiceImplTest {

	@Mock
	private IConsultaPersonaRepo repo;

	@InjectMocks
	private ConsultaPersonaServiceImpl service;

	@Test
	void listarPersonaPorId() {
		List<ConsultaPersona> list = List.of(new ConsultaPersona());
		when(repo.listarPersonaPorId(4)).thenReturn(list);
		assertEquals(list, service.listarPersonaPorId(4));
	}

	@Test
	void registrarConsultaPersona() {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setIdConsulta(1);
		f.setIdPersona(2);
		when(repo.registrarConsultaPersona(1, 2)).thenReturn(9);
		assertEquals(9, service.registrarConsultaPersona(f));
	}
}
