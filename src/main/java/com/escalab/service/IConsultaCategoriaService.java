package com.escalab.service;

import java.util.List;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaCategoria;

public interface IConsultaCategoriaService {
	
    List<ConsultaCategoria> listarCategoriaPorId(Integer idCategoria);
	
	Integer registrarConsultaCategoria (FiltroConsultaDTO filtro);

}
