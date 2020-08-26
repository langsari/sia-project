package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RPMetodePemb;

public interface RPMetodePembRepository {

	public List<RPMetodePemb> findByRPPerTemu(UUID idRPPerTemu);

	public UUID insert(RPMetodePemb mp);

	public RPMetodePemb findById(UUID idRPMetodePemb);

	public List<RPMetodePemb> findAll();

}
