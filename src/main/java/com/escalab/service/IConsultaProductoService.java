package com.escalab.service;

import java.util.List;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaProducto;

public interface IConsultaProductoService {

	List<ConsultaProducto> listarProductoPorId(Integer idProducto);
	
	Integer registrarConsultaProducto (FiltroConsultaDTO filtro);
}
