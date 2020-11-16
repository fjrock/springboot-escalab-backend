package com.escalab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.escalab.model.ConsultaPersona;


public interface IConsultaPersonaRepo  extends JpaRepository<ConsultaPersona, Integer>{
	
	@Query("from ConsultaPersona cp where cp.persona.idPersona = :idPersona")
	List<ConsultaPersona> listarPersonaPorId(@Param("idPersona") Integer idPersona);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO consulta_persona(id_consulta,id_persona) VALUES (:idConsulta,:idPersona)", nativeQuery = true)
	Integer registrarConsultaPersona(@Param("idConsulta") Integer idConsulta,@Param("idPersona") Integer idProducto);

}
