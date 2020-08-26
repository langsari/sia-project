package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RPPerTemu;

public interface RPPerTemuRepository {

	public void update(RPPerTemu rpPerTemu);

	public UUID insert(RPPerTemu rpPerTemu);

	public RPPerTemu findById(UUID idRPPerTemu);

	public List<RPPerTemu> findByRP(UUID idRP);

}
