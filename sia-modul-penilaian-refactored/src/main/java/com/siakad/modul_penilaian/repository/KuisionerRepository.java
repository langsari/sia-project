package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Kuisioner;

public interface KuisionerRepository {
	public List<Kuisioner> get(String where, String order, int limit, int offset);
	public UUID insert(Kuisioner kuisioner);
	public void update(Kuisioner kuisioner);
	public void delete(UUID idKuisioner);
	public Kuisioner getById(UUID idKuisioner);
}
