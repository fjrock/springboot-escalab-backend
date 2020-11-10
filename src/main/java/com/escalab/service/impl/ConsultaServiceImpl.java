package com.escalab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.Consulta;
import com.escalab.repo.IConsultaRepo;
import com.escalab.service.IConsultaService;

@Service
public class ConsultaServiceImpl implements IConsultaService{
	
	@Autowired	
	private IConsultaRepo iConsultaRepo;


	@Override
	public List<Consulta> buscarTodoPorRun(FiltroConsultaDTO filtro) {
		return iConsultaRepo.buscarTodoPorRun(filtro.getRun());
	}


	@Override
	public List<Consulta> buscarTodoPorCategoria(FiltroConsultaDTO filtro) {
		return iConsultaRepo.buscarTodoPorCategoria(filtro.getNombre());
	}
	

}
