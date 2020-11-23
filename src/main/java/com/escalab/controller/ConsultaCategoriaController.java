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
import com.escalab.model.ConsultaCategoria;
import com.escalab.service.IConsultaCategoriaService;

@RestController
@RequestMapping("/consultacategoria")
public class ConsultaCategoriaController {
	
	@Autowired
	private IConsultaCategoriaService iConsultaCategoriaService;
	
	
	@GetMapping(value = "/{idCategoria}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<List<ConsultaCategoria>> listar(@PathVariable("idCategoria") Integer idCategoria) {
		List<ConsultaCategoria> consultacategoria = new ArrayList<>();
		consultacategoria = iConsultaCategoriaService.listarCategoriaPorId(idCategoria);
		return new ResponseEntity<List<ConsultaCategoria>>(consultacategoria, HttpStatus.OK);
	}

	@PostMapping("/registrar")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> registrarConsultaCategoria(@Valid @RequestBody FiltroConsultaDTO filtro) {
		Integer integer = iConsultaCategoriaService.registrarConsultaCategoria(filtro);
		return new ResponseEntity<Object>(integer, HttpStatus.OK);
	}

}
