package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Pd;

public interface KrsRepository {
	public List<Krs> get();
	public List<Krs> get(String where);
	public List<Krs> get(String where, String order);
	public List<Krs> get(String where, String order, int limit, int offset);
    public Krs getById(UUID idKrs);
    public UUID insert(Krs krs);
    public void update(Krs krs);
    public void delete(Krs krs);
    public long count(String where);
	public List<Pd> getPeserta(String where, String order, int limit, int offset);
    public long countPeserta(String where);
}
