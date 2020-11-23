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
import com.escalab.model.ConsultaProducto;

import com.escalab.service.IConsultaProductoService;

@RestController
@RequestMapping("/consultaproducto")
public class ConsultaProductoController {
	
	@Autowired
	private IConsultaProductoService iConsultaProductoService;
	
	@GetMapping(value = "/{idProducto}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<List<ConsultaProducto>> listar(@PathVariable("idProducto") Integer idProducto) {
		List<ConsultaProducto> consultaproducto = new ArrayList<>();
		consultaproducto = iConsultaProductoService.listarProductoPorId(idProducto);
		return new ResponseEntity<List<ConsultaProducto>>(consultaproducto, HttpStatus.OK);
	}

	@PostMapping("/registrar")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> registrarConsultaProducto(@Valid @RequestBody FiltroConsultaDTO filtro) {
		Integer integer = iConsultaProductoService.registrarConsultaProducto(filtro);
		return new ResponseEntity<Object>(integer, HttpStatus.OK);
	}
}
