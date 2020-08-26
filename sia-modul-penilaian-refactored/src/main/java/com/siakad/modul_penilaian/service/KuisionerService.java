package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Kuisioner;

public interface KuisionerService {
	public List<Kuisioner> ambilSemuaKuisioner();
	public UUID tambahKuisioner(Kuisioner kuisioner);
	public Kuisioner ambilKuisioner(UUID idKuisioner);
	public void hapusKuisioner(UUID idKuisioner);
}
