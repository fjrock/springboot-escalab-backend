package com.escalab.service;

import java.util.List;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaProducto;

public interface IConsultaProductoService {

	List<ConsultaProducto> listarProductoPorConsulta(Integer idProducto);
	
	Integer registrarConsultaProducto (FiltroConsultaDTO filtro);
}
