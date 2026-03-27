package com.escalab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


@Entity
@Table(name = "consulta_categoria")
@IdClass(ConsultaCategoriaPK.class)
public class ConsultaCategoria {
	
	@Id
	private Categoria categoria;
	
	@Id
	private Consulta consulta;

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	

}

