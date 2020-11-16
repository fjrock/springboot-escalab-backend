package com.escalab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaCategoria;
import com.escalab.repo.IConsultaCategoriaRepo;
import com.escalab.service.IConsultaCategoriaService;

@Service
public class ConsultaCategoriaServiceImpl implements IConsultaCategoriaService{
	
	@Autowired
	private IConsultaCategoriaRepo iConsultaCategoriaRepo;
	
	@Override
	public List<ConsultaCategoria> listarCategoriaPorId(Integer idCategoria) {
		return iConsultaCategoriaRepo.listarCategoriaPorId(idCategoria);
	}

	@Override
	public Integer registrarConsultaCategoria(FiltroConsultaDTO filtro) {
		return iConsultaCategoriaRepo.registrarConsultaCategoria(filtro.getIdConsulta(),filtro.getIdCategoria());
	}

}
