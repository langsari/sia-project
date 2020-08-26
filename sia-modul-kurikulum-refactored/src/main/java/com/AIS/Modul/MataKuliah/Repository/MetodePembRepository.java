package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MetodePemb;

public interface MetodePembRepository {

	public long count(String string);

	public List<MetodePemb> get(String where, String order, int limit,
			int offset);

	public void update(MetodePemb metodePemb);

	public UUID insert(MetodePemb metodePemb);

	public MetodePemb findById(UUID idMetodePemb);

	public List<MetodePemb> findAll();

}
