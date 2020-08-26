package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.StatusKuisioner;

public interface StatusKuisionerRepository {
	public List<StatusKuisioner> get(String where, String order, int limit, int offset);
    public StatusKuisioner getById(UUID idStatusKuisioner);
    public UUID insert(StatusKuisioner statusKuisioner);
    public void update(StatusKuisioner statusKuisioner);
    public void delete(StatusKuisioner statusKuisioner);
    public long count(String where);
}
