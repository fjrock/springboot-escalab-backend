package com.escalab.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Información relacionada a consulta con producto")
@Entity
@Table(name = "consulta_producto")
@IdClass(ConsultaProductoPK.class)
public class ConsultaProducto {

	@Id
	private Producto producto;
	
	@Id
	private Consulta consulta;

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	
	
}
