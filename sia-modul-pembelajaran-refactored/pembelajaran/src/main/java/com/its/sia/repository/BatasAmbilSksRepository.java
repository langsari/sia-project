package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.BatasAmbilSks;
import com.sia.modul.domain.Ips;

public interface BatasAmbilSksRepository {
	public List<BatasAmbilSks> get(String where, String order, int limit, int offset);
    public BatasAmbilSks getById(UUID idBatasAmbilSks);
    public UUID insert(BatasAmbilSks batasAmbilSks);
    public void update(BatasAmbilSks batasAmbilSks);
    public void delete(BatasAmbilSks batasAmbilSks);
    public long count(String where);
}
