package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KomponenNilai;

public interface KomponenNilaiRepository {
	public List<KomponenNilai> get(String where, String order, int limit, int offset);
	public List<KomponenNilai> leftJoin(UUID idPemb);
	public UUID insert(KomponenNilai komp);
	public void update(KomponenNilai komp);
	public void delete(UUID idKomp);
	public double totalPresentase(UUID idPemb);
	public KomponenNilai getById(UUID idKomp);
}
