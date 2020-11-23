package com.escalab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.ConsultaPersona;

import com.escalab.service.IConsultaPersonaService;


@RestController
@RequestMapping("/consultapersona")
public class ConsultaPersonaController {
	
	@Autowired
	private IConsultaPersonaService iConsultaPersonaService;
	
	@GetMapping(value = "/{idPersona}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<List<ConsultaPersona>> listar(@PathVariable("idPersona") Integer idPersona) {
		List<ConsultaPersona> consultapersona = new ArrayList<>();
		consultapersona = iConsultaPersonaService.listarPersonaPorId(idPersona);
		return new ResponseEntity<List<ConsultaPersona>>(consultapersona, HttpStatus.OK);
	}

	@PostMapping("/registrar")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> registrarConsultaPersona(@Valid @RequestBody FiltroConsultaDTO filtro) {
		Integer integer = iConsultaPersonaService.registrarConsultaPersona(filtro);
		return new ResponseEntity<Object>(integer, HttpStatus.OK);
	}

}
