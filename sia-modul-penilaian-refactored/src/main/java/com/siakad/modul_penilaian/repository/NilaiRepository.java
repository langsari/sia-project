package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Nilai;

public interface NilaiRepository {
	public void insertBulk(List<Nilai> listNilai);
	public UUID insert(Nilai nilai);
	public void update(Nilai nilai);
	public Nilai getId(Nilai nilai);
	public List<Nilai> getByKrs(List<UUID> listIdKrs);
	public double getNilaiAkhir(UUID idKrs);
}
