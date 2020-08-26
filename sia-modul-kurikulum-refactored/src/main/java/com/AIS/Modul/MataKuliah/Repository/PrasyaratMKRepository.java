package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PrasyaratMK;

public interface PrasyaratMKRepository {
	public long count(String string);

	public List<PrasyaratMK> get(String where, String order, int limit, int offset);

	public void update(PrasyaratMK prasyaratMK);

	public UUID insert(PrasyaratMK prasyaratMK);

	public PrasyaratMK findById(UUID idPrasyaratMK);

	public List<PrasyaratMK> findAll();

	public List<PrasyaratMK> findParent(UUID idMK); 
}
