package com.escalab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaProducto;
import com.escalab.repo.IConsultaProductoRepo;
import com.escalab.service.IConsultaProductoService;

@Service
public class ConsultaProductoServiceImpl implements IConsultaProductoService {
	
	@Autowired
	private IConsultaProductoRepo iConsultaProductoRepo;
	
	@Override
	public List<ConsultaProducto> listarProductoPorId(Integer idProducto) {
		return iConsultaProductoRepo.listarProductoPorId(idProducto);
	}

	@Override
	public Integer registrarConsultaProducto(FiltroConsultaDTO filtro) {
		return iConsultaProductoRepo.registrarConsultaProducto(filtro.getIdConsulta(),filtro.getIdProducto());
	}

}
