package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="bentuk_penilaian")
public class BentukPenilaian {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_bentuk_penilaian")
	private UUID idBentuk;
	
	@Column(name="nm_bentuk_penilaian")
	private String namaBentuk;
	
	@Column(name="deskripsi_bentuk_penilaian")
	private String deskripsiBentuk;
	
	@Column(name="a_status_bentuk_penilaian")
	private boolean statusBentuk;

	public UUID getIdBentuk() {
		return idBentuk;
	}

	public void setIdBentuk(UUID idBentuk) {
		this.idBentuk = idBentuk;
	}

	public String getNamaBentuk() {
		return namaBentuk;
	}

	public void setNamaBentuk(String namaBentuk) {
		this.namaBentuk = namaBentuk;
	}

	public String getDeskripsiBentuk() {
		return deskripsiBentuk;
	}

	public void setDeskripsiBentuk(String deskripsiBentuk) {
		this.deskripsiBentuk = deskripsiBentuk;
	}

	public boolean isStatusBentuk() {
		return statusBentuk;
	}

	public void setStatusBentuk(boolean statusBentuk) {
		this.statusBentuk = statusBentuk;
	}
	
	
}
