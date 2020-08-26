package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="rumpun_mk")
public class RumpunMK{
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_rumpun_mk")
	private UUID idRumpunMK; 
	
	@Column(name="nm_rumpun_mk")
	@NotEmpty(message="Nama rumpun mata kuliah tidak boleh kosong")
	private String namaRumpunMK;
	   
	@Column(name="a_status_rumpun_mk")
	private boolean statusRumpunMK;

	public UUID getIdRumpunMK() {
		return idRumpunMK;
	}

	public void setIdRumpunMK(UUID idRumpunMK) {
		this.idRumpunMK = idRumpunMK;
	}

	public String getNamaRumpunMK() {
		return namaRumpunMK;
	}

	public void setNamaRumpunMK(String namaRumpunMK) {
		this.namaRumpunMK = namaRumpunMK;
	}

	public boolean getStatusRumpunMK() {
		return statusRumpunMK;
	}

	public void setStatusRumpunMK(boolean statusRumpunMK) {
		this.statusRumpunMK = statusRumpunMK;
	}

}
