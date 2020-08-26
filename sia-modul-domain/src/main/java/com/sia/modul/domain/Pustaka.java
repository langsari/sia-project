package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pustaka")
public class Pustaka {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_pustaka")
	private UUID idPustaka;
	
	@Column(name="nm_pustaka")
	private String namaPustaka;
	
	@Column(name="deskripsi_pustaka")
	private String deskripsiPustaka;
	
	@Column(name="a_sifat_pustaka")
	private String sifatPustaka;
	
	@Column(name="a_status_pustaka")
	private boolean statusPustaka;

	public UUID getIdPustaka() {
		return idPustaka;
	}

	public void setIdPustaka(UUID idPustaka) {
		this.idPustaka = idPustaka;
	}

	public String getNamaPustaka() {
		return namaPustaka;
	}

	public void setNamaPustaka(String namaPustaka) {
		this.namaPustaka = namaPustaka;
	}

	public String getDeskripsiPustaka() {
		return deskripsiPustaka;
	}

	public void setDeskripsiPustaka(String deskripsiPustaka) {
		this.deskripsiPustaka = deskripsiPustaka;
	}

	public String getSifatPustaka() {
		return sifatPustaka;
	}

	public void setSifatPustaka(String sifatPustaka) {
		this.sifatPustaka = sifatPustaka;
	}

	public boolean isStatusPustaka() {
		return statusPustaka;
	}

	public void setStatusPustaka(boolean statusPustaka) {
		this.statusPustaka = statusPustaka;
	} 
}
