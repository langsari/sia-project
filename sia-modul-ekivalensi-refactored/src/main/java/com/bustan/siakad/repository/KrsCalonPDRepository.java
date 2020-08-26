package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KrsCalonPD;
import com.sia.modul.domain.MKLuar;

public interface KrsCalonPDRepository {
	public List<KrsCalonPD> get(String where, String order, int limit, int offset);
    public KrsCalonPD getById(UUID idKrsCalonPD);
    public UUID insert(KrsCalonPD krsCalonPD);
    public void update(KrsCalonPD krsCalonPD);
    public void delete(KrsCalonPD krsCalonPD);
    public long count(String where);
}
