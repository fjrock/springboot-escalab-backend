package com.escalab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.escalab.model.ConsultaProducto;



public interface IConsultaProductoRepo extends JpaRepository<ConsultaProducto, Integer>{
	
	@Query("from ConsultaProducto cp where cp.consulta.idConsulta = :idConsulta")
	List<ConsultaProducto> listarProductoPorId(@Param("idConsulta") Integer idConsulta);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO consulta_producto(id_consulta,id_producto) VALUES (:idConsulta,:idProducto)", nativeQuery = true)
	Integer registrarConsultaProducto(@Param("idConsulta") Integer idConsulta,@Param("idProducto") Integer idProducto);


}
