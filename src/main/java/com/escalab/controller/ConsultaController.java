package com.escalab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.model.Consulta;
import com.escalab.service.IConsultaService;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
	
	@Autowired
	private IConsultaService IConsultaService;
	
	
	@PostMapping("/buscartodoporrun")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<List<Consulta>> buscarTodoPorRun(@RequestBody FiltroConsultaDTO filtro) {
		List<Consulta> consultas = new ArrayList<>();

		if (filtro != null) {
			
				consultas = IConsultaService.buscarTodoPorRun(filtro);
			
		}
		return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
	}
	
	@PostMapping("/buscartodoporcategoria")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<List<Consulta>> buscarTodoPorCategoria(@RequestBody FiltroConsultaDTO filtro) {
		List<Consulta> consultas = new ArrayList<>();

		if (filtro != null) {
			
				consultas = IConsultaService.buscarTodoPorCategoria(filtro);
			
		}
		return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
	}
	
	@PostMapping("/buscartodoporproducto")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<List<Consulta>> buscarTodoPorProducto(@RequestBody FiltroConsultaDTO filtro) {
		List<Consulta> consultas = new ArrayList<>();

		if (filtro != null) {
			
				consultas = IConsultaService.buscarTodoPorProducto(filtro);
			
		}
		return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
	}
	
	@PostMapping("/registrarconsulta")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> registrarConsulta(@Valid @RequestBody FiltroConsultaDTO filtro) {
		Integer integer = IConsultaService.registrarConsulta(filtro);
		return new ResponseEntity<Object>(integer, HttpStatus.OK);
	}

}
