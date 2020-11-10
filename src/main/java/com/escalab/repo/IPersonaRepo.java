package com.escalab.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.escalab.model.Persona;


public interface IPersonaRepo extends JpaRepository<Persona, Integer>{

	
	//CREAR METODO BORRAR TODO PARA DBA
}
