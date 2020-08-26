package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.CalonPD;;;

public interface CalonPDRepository {
	public List<CalonPD> get(String where, String order, int limit, int offset);
    public CalonPD getById(UUID idCalonPD);
    public UUID insert(CalonPD calonPD);
    public void update(CalonPD calonPD);
    public void delete(CalonPD calonPD);
    public long count(String where);
}
