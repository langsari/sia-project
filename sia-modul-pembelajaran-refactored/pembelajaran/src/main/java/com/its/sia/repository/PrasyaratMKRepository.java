package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PrasyaratMK;

public interface PrasyaratMKRepository {
	public List<PrasyaratMK> get(String where, String order, int limit, int offset);
    public PrasyaratMK getById(UUID idPrasyaratMK);
    public UUID insert(PrasyaratMK prasyaratMK);
    public void update(PrasyaratMK prasyaratMK);
    public void delete(PrasyaratMK prasyaratMK);
    public long count(String where);
}
