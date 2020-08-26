package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.StatusKuisioner;

public interface StatusKuisionerService {
	public boolean apakahKuisionerTerisi(UUID idKrs, UUID idKuisioner);
	public List<StatusKuisioner> ambilBerdasarkanPembKuisioner(UUID idPemb, UUID idKuisioner);
	public List<StatusKuisioner> ambilBerdasarkanPemb(UUID idPemb);
	public UUID masukkanStatus(StatusKuisioner status);
	public List<StatusKuisioner> ambilBerdasarkanKrs(UUID idKrs);
}
