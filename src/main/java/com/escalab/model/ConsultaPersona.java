package com.escalab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


@Entity
@Table(name = "consulta_persona")
@IdClass(ConsultaPersonaPK.class)
public class ConsultaPersona {
	
	@Id
	private Persona persona;
	
	@Id
	private Consulta consulta;

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	
	

}

