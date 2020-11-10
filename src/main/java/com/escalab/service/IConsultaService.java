package com.escalab.service;

import java.util.List;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.Consulta;

public interface IConsultaService {

	List<Consulta> buscarTodoPorRun(FiltroConsultaDTO filtro);
	
	List<Consulta> buscarTodoPorCategoria(FiltroConsultaDTO filtro);
}
