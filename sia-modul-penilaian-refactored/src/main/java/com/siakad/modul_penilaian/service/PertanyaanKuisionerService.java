package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PertanyaanKuisioner;

public interface PertanyaanKuisionerService {
	public List<PertanyaanKuisioner> ambilBerdasarKuisioner(UUID idKuisioner);
	public PertanyaanKuisioner getById(UUID idPertanyaan);
	public UUID tambahPertanyaan(PertanyaanKuisioner pertanyaan);
	public void simpanPertanyaan(PertanyaanKuisioner pertanyaan);
	public void hapusPertanyaan(UUID idPertanyaan);
}
