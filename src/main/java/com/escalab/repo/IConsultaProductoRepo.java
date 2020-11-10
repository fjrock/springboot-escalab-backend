package com.escalab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.escalab.model.ConsultaProducto;



public interface IConsultaProductoRepo extends JpaRepository<ConsultaProducto, Integer>{
	
	@Query("from ConsultaProducto cp where cp.consulta.idConsulta = :idConsulta")
	List<ConsultaProducto> listarProductoPorId(@Param("idConsulta") Integer idConsulta);

}
