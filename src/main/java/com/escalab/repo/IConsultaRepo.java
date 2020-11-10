package com.escalab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.escalab.model.Consulta;

public interface IConsultaRepo extends JpaRepository<Consulta, Integer>{
	
	@Query("from Consulta c where c.persona.run =:run")
	List<Consulta> buscarTodoPorRun(@Param("run")String run);
	
	@Query("from Consulta c where  LOWER(c.categoria.nombre) =:nombre")
	List<Consulta> buscarTodoPorCategoria(@Param("nombre")String nombre);
	
	
	

}
