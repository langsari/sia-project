package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PrasyaratMK;

public interface PrasyaratMKService {
	public List<PrasyaratMK> get();
	public List<PrasyaratMK> get(String where);
	public List<PrasyaratMK> get(String where, String order);
	public List<PrasyaratMK> get(String where, String order, int limit, int offset);
	public PrasyaratMK getById(UUID idPrasyaratMK);
}
