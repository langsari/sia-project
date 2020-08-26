package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.SatManMK;

public interface SatManMKService {
	public List<SatManMK> get();
	public List<SatManMK> get(String where);
	public List<SatManMK> get(String where, String order);
	public List<SatManMK> get(String where, String order, int limit, int offset);
    public SatManMK getById(UUID idSatManMK);
    public String save(SatManMK satManMk);
    public String delete(UUID idSatManMK);
}
