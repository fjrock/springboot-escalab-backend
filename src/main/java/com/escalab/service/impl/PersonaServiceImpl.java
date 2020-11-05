package com.escalab.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.escalab.model.Persona;
import com.escalab.repo.IPersonaRepo;
import com.escalab.service.IPersonaService;



@Service
public class PersonaServiceImpl implements IPersonaService{
	
	@Autowired
	private IPersonaRepo iPersonaepo;
	
	@Override
	public Persona leerPorId(Integer id) {
		Optional<Persona> op = iPersonaepo.findById(id);
		return op.isPresent() ? op.get() : new Persona();
 	}

}
