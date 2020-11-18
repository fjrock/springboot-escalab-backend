package com.escalab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.escalab.model.Consulta;

public interface IConsultaRepo extends JpaRepository<Consulta, Integer>{
	
	@Query("from Consulta c where c.persona.run =:run")
	List<Consulta> buscarTodoPorRun(@Param("run")String run);
	
	@Query("from Consulta c where  LOWER(c.categoria.nombre) =:nombre")
	List<Consulta> buscarTodoPorCategoria(@Param("nombre")String nombre);

	@Query("from Consulta c where  LOWER(c.producto.nombre) =:nombre")
	List<Consulta> buscarTodoPorProducto(@Param("nombre")String nombre);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO consulta(id_categoria, id_persona, id_producto) VALUES (:idCategoria, :idPersona, :idProducto)", nativeQuery = true)
	Integer registrarConsulta(@Param("idCategoria") Integer idCategoria, @Param("idPersona") Integer idPersona,@Param("idProducto") Integer idProducto);


}
