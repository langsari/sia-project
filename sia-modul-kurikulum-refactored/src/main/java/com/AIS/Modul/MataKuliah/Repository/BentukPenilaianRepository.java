package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.BentukPenilaian;

public interface BentukPenilaianRepository {

	public long count(String string);

	public List<BentukPenilaian> get(String where, String order, int limit,
			int offset);

	public void update(BentukPenilaian bentukPenilaian);

	public UUID insert(BentukPenilaian bentukPenilaian);

	public BentukPenilaian findById(UUID idBentuk);

	public List<BentukPenilaian> findAll();

}
