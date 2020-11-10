package com.escalab.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.escalab.model.ConsultaProducto;

import com.escalab.service.IConsultaProductoService;

@RestController
@RequestMapping("/consultaproducto")
public class ConsultaProductoController {
	
	@Autowired
	private IConsultaProductoService iConsultaProductoService;
	
	@GetMapping(value = "/{idConsulta}")
	public ResponseEntity<List<ConsultaProducto>> listar(@PathVariable("idConsulta") Integer idConsulta) {
		List<ConsultaProducto> consultaproducto = new ArrayList<>();
		consultaproducto = iConsultaProductoService.listarProductoPorConsulta(idConsulta);
		return new ResponseEntity<List<ConsultaProducto>>(consultaproducto, HttpStatus.OK);
	}

}
