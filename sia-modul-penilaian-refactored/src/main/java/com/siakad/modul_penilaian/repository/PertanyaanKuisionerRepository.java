package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PertanyaanKuisioner;

public interface PertanyaanKuisionerRepository {
	public List<PertanyaanKuisioner> getByIdKuisioner(UUID idKuisioner);
	public PertanyaanKuisioner getById(UUID idPertanyaan);
	public UUID insert(PertanyaanKuisioner pertanyaan);
	public void update(PertanyaanKuisioner pertanyaan);
	public void delete(UUID idPertanyaan);
}
