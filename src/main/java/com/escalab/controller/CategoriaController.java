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
import com.escalab.model.Categoria;
import com.escalab.service.ICategoriaService;


@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private ICategoriaService iCategoriaService;
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Categoria> listarPorId(@PathVariable("id") Integer id) {
		Categoria categoria = iCategoriaService.leerPorId(id);
		if (categoria.getIdCategoria() == null) {
			throw new ModeloNotFoundException("Error " + id);
		}
		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		Categoria categoria = iCategoriaService.leerPorId(id);
		if (categoria.getIdCategoria() == null) {
			throw new ModeloNotFoundException("Error " + id);
		}
		iCategoriaService.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> registrar(@Valid @RequestBody Categoria categoria) {
		Categoria cat = iCategoriaService.registrar(categoria);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cat.getIdCategoria()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Categoria> modificar(@Valid @RequestBody Categoria categoria) {
		Categoria cat = iCategoriaService.modificar(categoria);
		return new ResponseEntity<Categoria>(cat, HttpStatus.OK);
	}

}
