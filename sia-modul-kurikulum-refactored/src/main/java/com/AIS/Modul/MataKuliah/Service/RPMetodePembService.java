package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RPMetodePemb;

public interface RPMetodePembService {

	List<RPMetodePemb> findByRPPerTemu(UUID idRPPerTemu);

	public String save(RPMetodePemb mp);

	public String delete(UUID idRPMetodePemb);

	public List<RPMetodePemb> findAll();

}
