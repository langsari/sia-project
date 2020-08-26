package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.StatusKuisioner;

public interface StatusKuisionerService {
	public List<StatusKuisioner> get();
	public List<StatusKuisioner> get(String where);
	public List<StatusKuisioner> get(String where, String order);
	public List<StatusKuisioner> get(String where, String order, int limit, int offset);
    public StatusKuisioner getById(UUID idStatusKuisioner);
    public String save(StatusKuisioner statusKuisioner);
    public String delete(UUID idStatusKuisioner);
    
}
