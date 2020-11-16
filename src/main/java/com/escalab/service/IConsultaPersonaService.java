package com.escalab.service;

import java.util.List;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaPersona;


public interface IConsultaPersonaService {
	
	List<ConsultaPersona> listarPersonaPorId(Integer idPersona);
	
	Integer registrarConsultaPersona (FiltroConsultaDTO filtro);

}
