package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Kurikulum;

public interface KurikulumRepository {
	public List<Kurikulum> get(String where, String order, int limit, int offset);
    public Kurikulum getById(UUID idKurikulum);
    public UUID insert(Kurikulum kurikulum);
    public void update(Kurikulum kurikulum);
    public void delete(Kurikulum kurikulum);
    public long count(String where);
}
