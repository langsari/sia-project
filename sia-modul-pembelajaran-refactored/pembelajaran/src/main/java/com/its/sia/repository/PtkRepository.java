package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Ptk;

public interface PtkRepository {
	public List<Ptk> get();
	public List<Ptk> get(String where);
	public List<Ptk> get(String where, String order);
	public List<Ptk> get(String where, String order, int limit, int offset);
    public Ptk getById(UUID idPtk);
    public UUID insert(Ptk ptk);
    public void update(Ptk ptk);
    public void delete(UUID idPtk);
    public long count(String where);
}
