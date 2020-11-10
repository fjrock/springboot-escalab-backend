package com.escalab.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalab.model.Producto;
import com.escalab.repo.IProductoRepo;
import com.escalab.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService{
	
	@Autowired
	private IProductoRepo iProductoRepo;

	@Override
	public Producto registrar(Producto producto) {
		return iProductoRepo.save(producto);
		
	}

	@Override
	public Producto modificar(Producto producto) {
		return iProductoRepo.save(producto);
	}

	@Override
	public Producto leerPorId(Integer id) {
		Optional<Producto> op = iProductoRepo.findById(id);
		return op.isPresent() ? op.get() : new Producto();
	}

	@Override
	public boolean eliminar(Integer id) {
		iProductoRepo.deleteById(id);
		return true;
	}

}
