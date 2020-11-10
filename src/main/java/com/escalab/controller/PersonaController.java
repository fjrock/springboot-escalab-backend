package com.escalab.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.escalab.exception.ModeloNotFoundException;
import com.escalab.model.Persona;
import com.escalab.service.IPersonaService;

@RestController
@RequestMapping("/persona")
public class PersonaController {
	
	@Autowired
	private IPersonaService iPersonaService;

	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Persona> listarPorId(@PathVariable("id") Integer id) {
		Persona persona = iPersonaService.leerPorId(id);
		if (persona.getIdPersona() == null) {
			throw new ModeloNotFoundException("Error " + id);
		}
		return new ResponseEntity<Persona>(persona, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		Persona persona = iPersonaService.leerPorId(id);
		if (persona.getIdPersona() == null) {
			throw new ModeloNotFoundException("Error " + id);
		}
		iPersonaService.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> registrar(@Valid @RequestBody Persona persona) {
		Persona per = iPersonaService.registrar(persona);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(per.getIdPersona()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@PutMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Persona> modificar(@Valid @RequestBody Persona persona) {
		Persona per = iPersonaService.modificar(persona);
		return new ResponseEntity<Persona>(per, HttpStatus.OK);
	}
	
}
