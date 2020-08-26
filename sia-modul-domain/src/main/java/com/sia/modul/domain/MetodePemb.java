package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="metode_pemb")
public class MetodePemb {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_metode_pemb")
	private UUID idMetodePemb;
	
	@Column(name="nm_metode_pemb")
	private String namaMetodePemb;
	
	@Column(name="deskripsi_metode_pemb")
	private String deskripsiMetodePemb;

	@Column(name="a_status_metode_pemb")
	private boolean statusMetodePemb;
	
	public UUID getIdMetodePemb() {
		return idMetodePemb;
	}

	public void setIdMetodePemb(UUID idMetodePemb) {
		this.idMetodePemb = idMetodePemb;
	}

	public String getNamaMetodePemb() {
		return namaMetodePemb;
	}

	public void setNamaMetodePemb(String namaMetodePemb) {
		this.namaMetodePemb = namaMetodePemb;
	}

	public String getDeskripsiMetodePemb() {
		return deskripsiMetodePemb;
	}

	public void setDeskripsiMetodePemb(String deskripsiMetodePemb) {
		this.deskripsiMetodePemb = deskripsiMetodePemb;
	}

	public boolean isStatusMetodePemb() {
		return statusMetodePemb;
	}

	public void setStatusMetodePemb(boolean statusMetodePemb) {
		this.statusMetodePemb = statusMetodePemb;
	}
	
	
}
