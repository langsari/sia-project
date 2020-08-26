package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KatalogSatMan;

public interface KatalogSatManRepository {
	public List<KatalogSatMan> get(String where, String order, int limit, int offset);
    public KatalogSatMan getById(UUID idKatalogSatMan);
    public UUID insert(KatalogSatMan katalogSatMan);
    public void update(KatalogSatMan katalogSatMan);
    public void delete(KatalogSatMan katalogSatMan);
    public long count(String where);
}
