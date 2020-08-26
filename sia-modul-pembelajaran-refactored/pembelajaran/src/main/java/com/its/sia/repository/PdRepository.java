package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pd;

public interface PdRepository {
	public List<Pd> get(String where, String order, int limit, int offset);
    public Pd getById(UUID idPd);
    public UUID insert(Pd pd);
    public void update(Pd pd);
    public void delete(UUID idPd);
    public long count(String where);
    public List<Integer> getAngkatan(String where, String order, int limit, int offset);
}
