package com.siakad.modul_penilaian.service;

import java.util.UUID;

public class JSONNilaiKuisioner {
	private UUID idPertanyaan;
	private double nilaiPertanyaan;
	
	public JSONNilaiKuisioner() {
	}

	public UUID getIdPertanyaan() {
		return idPertanyaan;
	}

	public void setIdPertanyaan(UUID idPertanyaan) {
		this.idPertanyaan = idPertanyaan;
	}

	public double getNilaiPertanyaan() {
		return nilaiPertanyaan;
	}

	public void setNilaiPertanyaan(double nilaiPertanyaan) {
		this.nilaiPertanyaan = nilaiPertanyaan;
	}
}
