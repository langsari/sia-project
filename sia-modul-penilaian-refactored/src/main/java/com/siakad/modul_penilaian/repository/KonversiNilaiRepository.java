package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KonversiNilai;

public interface KonversiNilaiRepository {
	public List<KonversiNilai> getAll();
	public UUID insert(KonversiNilai konversi);
	public void update(KonversiNilai konversi);
	public void delete(UUID idKonversi);
}
