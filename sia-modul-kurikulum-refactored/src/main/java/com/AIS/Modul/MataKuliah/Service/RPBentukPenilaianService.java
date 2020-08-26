package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RPBentukPenilaian;

public interface RPBentukPenilaianService {

	public List<RPBentukPenilaian> findByRPPerTemu(UUID idRPPerTemu);

	public String save(RPBentukPenilaian mp);

	public List<RPBentukPenilaian> findAll();

}
