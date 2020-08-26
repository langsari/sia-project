package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.SatManMK;

public interface SatManMKRepository {
	public List<SatManMK> get(String where, String order, int limit, int offset);
    public SatManMK getById(UUID idSatManMK);
    public UUID insert(SatManMK mk);
    public void update(SatManMK mk);
    public void delete(SatManMK mk);
    public long count(String where);
}
