package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "alamat_pengguna")
public class AlamatPengguna {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_alamat")
	private UUID idAlamatPengguna;
	
	@Type(type = "pg-uuid")
	@Column(name = "id_pengguna", nullable = false)
	private UUID idPengguna;

	@Column(name = "alamat", nullable = false)
	private String alamat;

	public AlamatPengguna(){
		
	}
	
	public AlamatPengguna(UUID idAlamatPengguna, UUID idPengguna, String alamat) {
		super();
		this.idAlamatPengguna = idAlamatPengguna;
		this.idPengguna = idPengguna;
		this.alamat = alamat;
	}

	public UUID getIdAlamatPengguna() {
		return idAlamatPengguna;
	}

	public void setIdAlamatPengguna(UUID idAlamatPengguna) {
		this.idAlamatPengguna = idAlamatPengguna;
	}

	public UUID getIdPengguna() {
		return idPengguna;
	}

	public void setIdPengguna(UUID idPengguna) {
		this.idPengguna = idPengguna;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

}
