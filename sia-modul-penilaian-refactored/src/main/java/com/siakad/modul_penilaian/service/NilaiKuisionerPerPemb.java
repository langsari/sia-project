package com.siakad.modul_penilaian.service;

import java.util.UUID;

public class NilaiKuisionerPerPemb {
	private UUID idPemb;
	private UUID idKuisioner;
	private Double nilai;
	
	public NilaiKuisionerPerPemb(UUID idPemb, UUID idKuisioner, Double nilai) {
		this.idPemb = idPemb;
		this.idKuisioner = idKuisioner;
		this.nilai = nilai;
	}

	public UUID getIdPemb() {
		return idPemb;
	}

	public void setIdPemb(UUID idPemb) {
		this.idPemb = idPemb;
	}

	public UUID getIdKuisioner() {
		return idKuisioner;
	}

	public void setIdKuisioner(UUID idKuisioner) {
		this.idKuisioner = idKuisioner;
	}

	public Double getNilai() {
		return nilai;
	}

	public void setNilai(Double nilai) {
		this.nilai = nilai;
	}
	
}
