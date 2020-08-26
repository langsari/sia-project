package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.StatusKuisioner;

public interface StatusKuisionerRepository {
	public StatusKuisioner getById(UUID idStatus);
	public List<StatusKuisioner> getByKrsKuisioner(UUID idKrs, UUID idKuisioner);
	public List<StatusKuisioner> getByKrs(UUID idKrs);
	public UUID insert(StatusKuisioner status);
	public void update(StatusKuisioner status);
	public List<StatusKuisioner> getByPemb(UUID idPemb);
	public List<StatusKuisioner> getByPembKuisioner(UUID idPemb, UUID idKuisioner);
}
