package com.escalab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.escalab.model.ConsultaCategoria;



public interface IConsultaCategoriaRepo extends JpaRepository<ConsultaCategoria, Integer> {
	
	@Query("from ConsultaCategoria cc where cc.categoria.idCategoria = :idCategoria")
	List<ConsultaCategoria> listarCategoriaPorId(@Param("idCategoria") Integer idCategoria);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO consulta_categoria(id_consulta,id_categoria) VALUES (:idConsulta,:idCategoria)", nativeQuery = true)
	Integer registrarConsultaCategoria(@Param("idConsulta") Integer idConsulta,@Param("idCategoria") Integer idCategoria);

}
