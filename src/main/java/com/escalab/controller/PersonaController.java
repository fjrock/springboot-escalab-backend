package com.escalab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escalab.exception.ModeloNotFoundException;
import com.escalab.model.Persona;
import com.escalab.service.IPersonaService;

@RestController
@RequestMapping("/persona")
public class PersonaController {
	
	@Autowired
	private IPersonaService iPersonaService;

	
	@GetMapping("/{id}")
	public ResponseEntity<Persona> listarPorId(@PathVariable("id") Integer id) {
		Persona obj = iPersonaService.leerPorId(id);
		if (obj.getIdPersona() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Persona>(obj, HttpStatus.OK);
	}
}
