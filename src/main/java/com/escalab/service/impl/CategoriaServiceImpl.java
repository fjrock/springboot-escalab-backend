package com.escalab.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalab.model.Categoria;
import com.escalab.repo.ICategoriaRepo;
import com.escalab.service.ICategoriaService;

@Service
public class CategoriaServiceImpl implements ICategoriaService{
	
	@Autowired
	private ICategoriaRepo iCategoriaRepo;

	@Override
	public Categoria registrar(Categoria categoria) {
		return iCategoriaRepo.save(categoria);
		
	}

	@Override
	public Categoria modificar(Categoria categoria) {
		return iCategoriaRepo.save(categoria);
	}

	@Override
	public Categoria leerPorId(Integer id) {
		Optional<Categoria> op = iCategoriaRepo.findById(id);
		return op.isPresent() ? op.get() : new Categoria();
	}

	@Override
	public boolean eliminar(Integer id) {
		iCategoriaRepo.deleteById(id);
		return true;
	}
	
	

}
