package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PrasyaratMK;

public interface PrasyaratMKRepository {
	public List<PrasyaratMK> get(String where, String order, int limit, int offset);
    public PrasyaratMK getById(UUID idPrasyaratMK);
}
