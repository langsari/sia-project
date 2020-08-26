package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Rombel;

public interface RombelRepository {
	public List<Rombel> get(String where, String order, int limit, int offset);
    public Rombel getById(UUID idRombel);
    public UUID insert(Rombel rombel);
    public void update(Rombel rombel);
    public void delete(UUID idRombel);
    public long count(String where);
}
