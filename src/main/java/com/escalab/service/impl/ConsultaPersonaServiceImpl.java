package com.escalab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaPersona;
import com.escalab.repo.IConsultaPersonaRepo;
import com.escalab.service.IConsultaPersonaService;

@Service
public class ConsultaPersonaServiceImpl implements IConsultaPersonaService{

	@Autowired
	private IConsultaPersonaRepo iConsultaPersonaRepo;
	
	@Override
	public List<ConsultaPersona> listarPersonaPorId(Integer idPersona) {
		return iConsultaPersonaRepo.listarPersonaPorId(idPersona);
	}

	@Override
	public Integer registrarConsultaPersona(FiltroConsultaDTO filtro) {
		return iConsultaPersonaRepo.registrarConsultaPersona(filtro.getIdConsulta(),filtro.getIdPersona());
	}

}
