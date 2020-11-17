package com.escalab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Información de la persona (natural - juridica) que puede ofrecer productos")
@Entity
@Table(name = "persona")
public class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPersona;

	@ApiModelProperty(notes = "Nombre debe tener maximo 100 caracteres")
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	
	@ApiModelProperty(notes = "Apellido Paterno debe tener maximo 100 caracteres")
	@Column(name = "apellido_paterno", nullable = false, length = 100)
	private String apellidoPaterno;
	
	@ApiModelProperty(notes = "Apellido Materno debe tener maximo 100 caracteres")
	@Column(name = "apellido_materno", nullable = false, length = 100)
	private String apellidoMaterno;
	
	@ApiModelProperty(notes = "Run  debe tener maximo 8 caracteres")
	@Column(name = "run", nullable = false, length = 8)
	private String run;
	
	@ApiModelProperty(notes = "Digito verificador Paterno debe tener maximo 1 caracter")
	@Column(name = "dv", nullable = false, length = 1)
	private String dv;
	
	@ApiModelProperty(notes = "Telefono debe tener maximo 12 caracteres")
	@Column(name = "telefono", nullable = false, length = 12)
	private String telefono;
	
	@ApiModelProperty(notes = "Tipo persona (natural - juridica) debe tener maximo 100 caracteres")
	@Column(name = "tipo_persona", nullable = false, length = 100)
	private String tipoPersona;
	
	@ApiModelProperty(notes = "Email")
	@Email
	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@ApiModelProperty(notes = "define si usuario puede o no ofrecer productos")
	@Column(name = "banned", nullable = false, columnDefinition="boolean default true")
	private boolean banned;
	
	
	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
