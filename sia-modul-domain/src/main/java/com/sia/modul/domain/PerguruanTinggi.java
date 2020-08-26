package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="perguruan_tinggi")
public class PerguruanTinggi {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_perguruan_tinggi")
	private UUID idPerguruanTinggi;
	
	@Column(name="nm_perguruan_tinggi")
	private String nmPerguruanTinggi;

	@Column(name="a_perguruan_tinggi_terhapus")
	private boolean aPerguruanTinggiTerhapus;

	public PerguruanTinggi() {
		
	}

	public UUID getIdPerguruanTinggi() {
		return idPerguruanTinggi;
	}

	public void setIdPerguruanTinggi(UUID idPerguruanTinggi) {
		this.idPerguruanTinggi = idPerguruanTinggi;
	}

	public String getNmPerguruanTinggi() {
		return nmPerguruanTinggi;
	}

	public void setNmPerguruanTinggi(String nmPerguruanTinggi) {
		this.nmPerguruanTinggi = nmPerguruanTinggi;
	}

	public boolean isaPerguruanTinggiTerhapus() {
		return aPerguruanTinggiTerhapus;
	}

	public void setaPerguruanTinggiTerhapus(boolean aPerguruanTinggiTerhapus) {
		this.aPerguruanTinggiTerhapus = aPerguruanTinggiTerhapus;
	}
	
}
