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
import com.escalab.model.Producto;
import com.escalab.service.IProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	private IProductoService iProductoService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Producto> listarPorId(@PathVariable("id") Integer id) {
		Producto producto = iProductoService.leerPorId(id);
		if (producto.getIdProducto() == null) {
			throw new ModeloNotFoundException("Error " + id);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		Producto producto = iProductoService.leerPorId(id);
		if (producto.getIdProducto() == null) {
			throw new ModeloNotFoundException("Error " + id);
		}
		iProductoService.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> registrar(@Valid @RequestBody Producto producto) {
		Producto pro = iProductoService.registrar(producto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pro.getIdProducto()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Producto> modificar(@Valid @RequestBody Producto producto) {
		Producto Pro = iProductoService.modificar(producto);
		return new ResponseEntity<Producto>(Pro, HttpStatus.OK);
	}

}
