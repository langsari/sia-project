package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RPBentukPenilaian;

public interface RPBentukPenilaianRepository {

	public List<RPBentukPenilaian> findByRPPerTemu(UUID idRPPerTemu);

	public UUID insert(RPBentukPenilaian mp);

	public List<RPBentukPenilaian> findAll();

}
