package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RPPerTemu;

public interface RPPerTemuService {

	public String save(RPPerTemu rpPerTemu);

	public String delete(UUID uuid);

	public List<RPPerTemu> findByRP(UUID idRP);

	public RPPerTemu findById(UUID idRPPerTemu);

}
