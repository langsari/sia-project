package com.siakad.modul_penilaian.service;

import java.util.UUID;

public class JSONPertanyaan {
	private UUID idPertanyaan;
	private String pertanyaan;
	private UUID idKuisioner;
	
	public UUID getIdPertanyaan() {
		return idPertanyaan;
	}
	public void setIdPertanyaan(UUID idPertanyaan) {
		this.idPertanyaan = idPertanyaan;
	}
	public String getPertanyaan() {
		return pertanyaan;
	}
	public void setPertanyaan(String pertanyaan) {
		this.pertanyaan = pertanyaan;
	}
	public UUID getIdKuisioner() {
		return idKuisioner;
	}
	public void setIdKuisioner(UUID idKuisioner) {
		this.idKuisioner = idKuisioner;
	}	
	
}
