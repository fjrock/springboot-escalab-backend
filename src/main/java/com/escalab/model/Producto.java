package com.escalab.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Información de productos disponibles para ofrecer por un tipo de persona")
@Entity
@Table(name = "producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProducto;
	
	@ApiModelProperty(notes = "Nombre de producto debe tener maximo 50 caracteres")
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	
	@ApiModelProperty(notes = "stock de categoria desde 0 hasta n")
	@Column(name = "stock", nullable = false)
	private Integer stock;
	
	@ApiModelProperty(notes = "fecha de creacion producto a ofrecer")
	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	@ApiModelProperty(notes = "fecha de actualizacion de producto a ofrecer")
	@Column(name = "fecha_actualizacion")
	private LocalDateTime fechaActualizacion;
	
	@ApiModelProperty(notes = "tipo de intercambio de producto a ofrecer (trueque-venta-donacion-otros)")
	@Column(name = "tipo_intercambio")
	private String tipoIntercambio;
	
	@ApiModelProperty(notes = "lugar de entrega de producto a ofrecer (metro-comisaria-cualquier lugar seguro para los usuarios). Debe tener maximo 50 caracteres")
	@Column(name = "lugar_entrega",length = 50)
	private String lugarEntrega;
	
	

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getTipoIntercambio() {
		return tipoIntercambio;
	}

	public void setTipoIntercambio(String tipoIntercambio) {
		this.tipoIntercambio = tipoIntercambio;
	}

	public String getLugarEntrega() {
		return lugarEntrega;
	}

	public void setLugarEntrega(String lugarEntrega) {
		this.lugarEntrega = lugarEntrega;
	}
	
	
	
	

}
