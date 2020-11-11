package com.escalab.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.escalab.model.Consulta;

public interface IGuardaConsultaRepo extends JpaRepository<Consulta, Integer>{
	
	@Modifying
	@Query(value = "INSERT INTO consulta(id_consulta, id_categoria, id_persona, id_producto) VALUES (:idConsulta, :idCategoria, :idPersona, :idProducto)", nativeQuery = true)
	Integer registrar(@Param("idConsulta") Integer idConsulta, @Param("idCategoria") Integer idCategoria, @Param("idPersona") Integer idPersona,@Param("idProducto") Integer idProducto);

}
